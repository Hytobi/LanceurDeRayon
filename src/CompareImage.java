import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class CompareImage {
    public static void main(String[] args) {
        // On recupère le nom des image passé en arg
        String im1 = args[0];
        String im2 = args[1];
        // On recupere les images
        BufferedImage img1, img2;
        try {
            img1 = ImageIO.read(new File(im1));
            img2 = ImageIO.read(new File(im2));
        } catch (Exception e) {
            throw new IllegalArgumentException("Pb ouverture");
        }

        // On va créé une nouvelle image en outpu diff.png
        BufferedImage img3 = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_INT_RGB);

        // Le compteur de pixel different
        int count = 0;
        int color1;
        int color2;
        int r, g, b;

        // On compare les images
        if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
            for (int x = 0; x < img1.getWidth(); x++) {
                for (int y = 0; y < img1.getHeight(); y++) {
                    color1 = img1.getRGB(x, y);
                    color2 = img2.getRGB(x, y);

                    if (color1 != color2) {
                        count++;
                        r = Math.abs(((color1 >> 16) & 0xff) - ((color2 >> 16) & 0xff));
                        g = Math.abs(((color1 >> 8) & 0xff) - ((color2 >> 8) & 0xff));
                        b = Math.abs(((color1) & 0xff) - ((color2) & 0xff));
                        img3.setRGB(x, y, (255 << 24) | (r << 16) | (g << 8) | b);
                    } else {
                        img3.setRGB(x, y, 0x000000);
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Les images n'ont pas la meme taille");
        }

        // On affiche le resultat
        if (count <= 1000) {
            System.out.println("OK");
        } else {
            System.out.println("KO");
        }
        System.out.println(count);

        // On sauvegarde l'image
        if (count < 1)
            return;
        try {
            ImageIO.write(img3, "png", new File("diff.png"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Pb sauvegarde");
        }

    }
}
