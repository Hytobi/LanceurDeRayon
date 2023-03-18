import java.awt.image.BufferedImage;
import env.*;
import math.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Generateur {
    private final String nomScene;
    private Scene scene;
    private Vecteur[] system;
    private double[] pixelDim;

    public Generateur(String nomScene) {
        this.nomScene = nomScene;
    }

    public void generateur() {
        CheckScene checkScene = new CheckScene(nomScene);
        this.scene = checkScene.getScene();

        // On va créé une nouvelle image en outpu diff.png
        BufferedImage img = new BufferedImage(this.scene.getSize()[0], this.scene.getSize()[1],
                BufferedImage.TYPE_INT_RGB);

        this.system = this.calculeRepereOrthonorme();
        this.pixelDim = this.calculeDimPixel();
        Point o = this.scene.getCamera().getLookFrom();

        // On recupere le nombre de coeur de la machine
        int nbCoeur = Runtime.getRuntime().availableProcessors();
        // On va créer un pool de thread de taille nbCoeur
        ExecutorService pool = Executors.newFixedThreadPool(nbCoeur);
        // crée une tâche par ligne de l'image
        for (int i = 0; i < this.scene.getSize()[1]; i++) {
            final int itmp = i;
            pool.execute(() -> rayTrace(img, itmp, o));
        }
        // indique de s'arrêter quand les tâches sont terminées
        pool.shutdown();
        // attend la complétion des tâches
        try {
            if (!pool.awaitTermination(1200, TimeUnit.SECONDS)) {
                System.err.println("Timeout reached!!!");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // On va sauvegarder l'image
        try {
            javax.imageio.ImageIO.write(img, "png", new java.io.File(this.scene.getOutput()));
        } catch (java.io.IOException e) {
            System.out.println("Erreur lors de la sauvegarde de l'image");
        }
    }

    private void rayTrace(BufferedImage img, int i, Point o) {
        for (int j = 0; j < this.scene.getSize()[0]; j++) {
            Vecteur d = this.calculeVecteurD(new Vecteur(j, i, 0));
            Intersecte intersecte = scene.calculeIntersection(d, o, true);

            if (!intersecte.intersecte) {
                img.setRGB(j, this.scene.getSize()[1] - i - 1, 0);
                continue;
            }
            Couleur couleur = this.calculeCouleur(d, o, intersecte, 0);
            java.awt.Color c = new java.awt.Color((float) (couleur.getX()), (float) (couleur.getY()),
                    (float) (couleur.getZ()));

            img.setRGB(j, this.scene.getSize()[1] - i - 1, c.getRGB());

        }
    }

    private Vecteur[] calculeRepereOrthonorme() {
        Camera camera = this.scene.getCamera();
        Vecteur[] repere = new Vecteur[3];

        // On calcule le vecteur w
        repere[2] = camera.getLookFrom().sub(camera.getLookAt()).hat();
        // On calcule le vecteur u
        repere[0] = camera.getUp().cross(repere[2]).hat();
        // On calcule le vecteur v
        repere[1] = repere[2].cross(repere[0]).hat();

        return repere;
    }

    private double[] calculeDimPixel() {
        double[] dimensions = new double[2];

        double fov = this.scene.getCamera().getFov() * Math.PI / 180;

        dimensions[1] = Math.tan(fov / 2); // height
        dimensions[0] = dimensions[1] * ((double) this.scene.getSize()[0] / (double) this.scene.getSize()[1]); // width

        return dimensions;
    }

    private Vecteur calculeVecteurD(Vecteur ij) {
        double pixelwidth = this.pixelDim[0];
        double pixelHeight = this.pixelDim[1];

        double imgwidth = this.scene.getSize()[0];
        double imgheight = this.scene.getSize()[1];

        double na = pixelwidth / (imgwidth / 2);
        double nb = pixelHeight / (imgheight / 2);

        double a = na * (ij.getX() - (imgwidth / 2) + 0.5);
        double b = nb * (ij.getY() - (imgheight / 2) + 0.5);

        Vecteur u = this.system[0];
        Vecteur v = this.system[1];
        Vecteur w = this.system[2];

        return u.mul(a).add(v.mul(b)).sub(w).hat();
    }

    private Couleur calculeCouleur(Vecteur d, Point o, Intersecte intersecte, int depth) {

        // coul = la + ( somme(max( n * ldir[i],0)*lightcolor[i] * couleurdiffuse + max(
        // n * h[i],0)^shininess *lightcolor[i] * couleurspecular) )

        if (this.scene.getMaxdepth() <= depth) {
            return new Couleur(0, 0, 0);

        }

        Forme forme = intersecte.forme;

        // coul = la
        Couleur coul = this.scene.getAmbient();

        // On recupere le difuse
        Couleur couleurDiffuse = forme.getDiffuse();
        // On recupere le specular
        Couleur couleurSpecular = forme.getSpecular();
        // On recupere le shininess
        double shininess = forme.getShininess();

        // On recupére le vecteur n
        Point p = o.add(d.mul(intersecte.t));
        Vecteur n = forme.getNormal(p);

        // Boucle la somme
        for (Lumiere lumiere : this.scene.getLumieres()) {
            // On recupere le vecteur ldir
            Vecteur ldir = lumiere.getDirection(p);

            if (this.scene.getShadow() && lumiere.estCachee(scene, p))
                continue;

            // On recupere le lightcolor
            Couleur lightcolor = lumiere.getCouleur();

            // On calcule le h
            Vecteur h = lumiere.getDirection(p).add(d.mul(-1)).hat();
            // On calcule la couleur blinnPhong
            Couleur blinnPhong = lightcolor.mul(Math.pow(Math.max(n.dot(h), 0), shininess)).times(couleurSpecular);

            // On calcule la somme
            coul = coul.add(lightcolor.mul(Math.max(n.dot(ldir), 0)).times(couleurDiffuse).add(blinnPhong)).undo();
        }

        if (!couleurSpecular.isBlack()) {
            // On calcule le vecteur r
            Vecteur r = d.sub(n.mul(2 * d.dot(n))).hat();
            p = p.add(r.mul(0.00001));
            // On calcule l'intersection
            Intersecte i = scene.calculeIntersection(r, p, true);
            if (i.intersecte) {
                // On calcule la couleur c'
                Couleur c = this.calculeCouleur(r, p, i, ++depth).undo();
                // On calcule la couleur finale coul = c' * specular + coul
                coul = coul.add(c.times(couleurSpecular)).undo();
            }
        }

        return coul.undo();
    }
}
