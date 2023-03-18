
public class RayTracer {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Usage: java RayTracer <scene file>");
        }
        Generateur generateur = new Generateur(args[0]);
        generateur.generateur();
    }
}
