package env;

import java.util.ArrayList;
import java.util.List;
import math.Couleur;
import math.Point;
import math.Vecteur;

public class Scene {

    private String output;
    private final int[] size;

    // Camera
    private Camera camera;

    // Les ombres
    private boolean shadow;

    // Informations de couleur de la scene
    private Couleur ambient; // Couleur ambiante
    private Couleur diffuse; // Couleur de l'objet
    private Couleur specular; // Couleur de la lumière reflétée
    private int shininess; // La brillance de l'objet
    private int maxdepth; // La profondeur maximale de réflexion

    // Informations de lumière
    private List<Lumiere> lumieres; // global directionnelle et lumière locale

    // Informations de forme géometrique
    private int maxverts; // nombre de sommets maximum
    private List<Point> vertex; // liste des sommets
    private List<Forme> formes; // Sphere, Plane ou Triangle

    public Scene() {
        this.size = new int[2];
        this.camera = new Camera();
        this.shadow = false;
        this.ambient = new Couleur();
        this.diffuse = new Couleur();
        this.specular = new Couleur();
        this.shininess = 1;
        this.maxdepth = 1;
        this.lumieres = new ArrayList<>();
        this.vertex = new ArrayList<>();
        this.formes = new ArrayList<>();
    }

    // Getters
    public String getOutput() {
        return this.output;
    }

    public int[] getSize() {
        return this.size;
    }

    public Camera getCamera() {
        return this.camera;
    }

    public boolean getShadow() {
        return this.shadow;
    }

    public Couleur getAmbient() {
        return this.ambient;
    }

    public Couleur getDiffuse() {
        return this.diffuse;
    }

    public Couleur getSpecular() {
        return this.specular;
    }

    public int getShininess() {
        return this.shininess;
    }

    public int getMaxdepth() {
        return this.maxdepth;
    }

    public List<Lumiere> getLumieres() {
        return this.lumieres;
    }

    public int getMaxverts() {
        return this.maxverts;
    }

    public List<Point> getVertex() {
        return this.vertex;
    }

    public List<Forme> getFormes() {
        return this.formes;
    }

    // Setters
    private double[] getDoubleArray(String vals) {
        String[] tokens = vals.split("\\s+");
        double[] res = new double[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            res[i] = Double.parseDouble(tokens[i]);
        }
        return res;
    }

    public void output(String output) {
        this.output = output;
    }

    public void size(String str) {
        String[] tokens = str.split("\\s+");
        for (int i = 0; i < tokens.length; i++) {
            this.size[i] = Integer.parseInt(tokens[i]);
        }
    }

    public void camera(String str) {
        double[] vals = this.getDoubleArray(str);

        this.camera.setLookFrom(new Point(vals[0], vals[1], vals[2]));
        this.camera.setLookAt(new Vecteur(vals[3], vals[4], vals[5]));
        this.camera.setUp(new Vecteur(vals[6], vals[7], vals[8]));
        this.camera.setFov(vals[9]);
    }

    public void shadow(String str) {
        this.shadow = Boolean.parseBoolean(str);
    }

    public void ambient(String str) throws Exception {
        double[] vals = this.getDoubleArray(str);
        if (this.diffuse != null && (this.diffuse.getX() * vals[0] > 1 || this.diffuse.getY() * vals[1] > 1
                || this.diffuse.getZ() * vals[2] > 1))
            throw new Exception("Entrée incorrecte : difuse * ambient > 1");
        this.ambient = new Couleur(vals[0], vals[1], vals[2]);
    }

    public void diffuse(String str) throws Exception {
        double[] vals = this.getDoubleArray(str);
        if (this.ambient != null && (this.ambient.getX() * vals[0] > 1 || this.ambient.getY() * vals[1] > 1
                || this.ambient.getZ() * vals[2] > 1))
            throw new Exception("Entrée incorrecte : difuse * ambient > 1");
        this.diffuse = new Couleur(vals[0], vals[1], vals[2]);
    }

    public void specular(String str) {
        double[] vals = this.getDoubleArray(str);
        this.specular = new Couleur(vals[0], vals[1], vals[2]);
    }

    public void shininess(String str) {
        this.shininess = Integer.parseInt(str);
    }

    public void maxdepth(String str) {
        this.maxdepth = Integer.parseInt(str);
    }

    public void directional(String str) {
        double[] vals = this.getDoubleArray(str);
        this.lumieres.add(
                new LumiereDirection(new Vecteur(vals[0], vals[1], vals[2]), new Couleur(vals[3], vals[4], vals[5])));
    }

    public void point(String str) {
        double[] vals = this.getDoubleArray(str);
        this.lumieres
                .add(new LumierePoint(new Point(vals[0], vals[1], vals[2]), new Couleur(vals[3], vals[4], vals[5])));
    }

    public void maxverts(String str) {
        this.maxverts = Integer.parseInt(str.trim());
    }

    public void vertex(String str) {
        double[] vals = this.getDoubleArray(str);
        this.vertex.add(new Point(vals[0], vals[1], vals[2]));
    }

    public void tri(String str) {
        String[] vals = str.split(" ");
        int a = Integer.parseInt(vals[0]);
        int b = Integer.parseInt(vals[1]);
        int c = Integer.parseInt(vals[2]);
        if (a < this.maxverts && b < this.maxverts && c < this.maxverts) {
            this.formes.add(new Triangle(this.vertex.get(a), this.vertex.get(b), this.vertex.get(c), this.diffuse,
                    this.specular, this.shininess));
        }
    }

    public void sphere(String str) {
        double[] vals = this.getDoubleArray(str);
        this.formes.add(new Sphere(new Point(vals[0], vals[1], vals[2]), vals[3], this.diffuse, this.specular,
                this.shininess));
    }

    public void plane(String str) {
        double[] vals = this.getDoubleArray(str);
        this.formes.add(new Plan(new Point(vals[0], vals[1], vals[2]), new Vecteur(vals[3], vals[4], vals[5]),
                this.diffuse, this.specular, this.shininess));
    }

    public Intersecte calculeIntersection(Vecteur d, Point o, boolean premier) {
        Intersecte i = new Intersecte();

        for (Forme f : this.formes) {
            double tForme = f.intersection(d, o, 1E-6, 1E6);
            if (tForme > 0 && (!i.intersecte || tForme < i.t)) {
                i.intersecte = true;
                i.t = tForme;
                i.forme = f;
                if (!premier)
                    return i;
            }
        }
        return i;

    }
}
