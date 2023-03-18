
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import env.Scene;

public class CheckScene {
    private final String nomFichier;
    private Scene scene;

    public CheckScene(String nomFichier) {
        this.nomFichier = nomFichier;
        this.scene = new Scene();
    }

    public Scene getScene() {
        File file = null;

        // On ouvre le fichier en .scene
        try {
            file = new File(nomFichier);
        } catch (Exception e) {
            System.out.println("Erreur lors de l'ouverture du fichier");
            System.exit(1);
        }
        String line;
        String[] tokens;

        try {
            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                line = sc.nextLine();
                tokens = line.split("\\s+", 2);
                try {
                    if (line.startsWith("#") || line.trim().length() == 0) {
                        continue;
                    }
                    // On appelle la méthode du meme nom que le token et on lui passe le reste de la
                    // ligne
                    this.scene.getClass().getDeclaredMethod(tokens[0], String.class).invoke(scene, tokens[1]);
                } catch (Exception e) {
                    System.out.println("Erreur lecture : " + line + " token :" + tokens[0]);
                    System.exit(1);
                }
            }

            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return scene;
    }

    public static void main(String[] args) {
        CheckScene cs = new CheckScene(args[0]);
        Scene s = cs.getScene();

        /*
         * Le nom de fichier de sortie indiqué dans la scène
         * le nombre de pixels de la scène (hauteur * largeur)
         * le nombre d'objets graphiques déclarés
         * le nombre de sources de lumières
         */
        System.out.println(s.getOutput());
        System.out.println(s.getSize()[0] * s.getSize()[1]);
        System.out.println(s.getFormes().size());
        System.out.println(s.getLumieres().size());
    }

}