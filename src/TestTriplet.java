import math.Couleur;
import math.Point;
import math.Vecteur;

public class TestTriplet {

    public static Object buildObject(String str) throws Exception {
        // On cut par les espaces pour récupérer le type et les coordonnées
        String[] triplet = str.split(" ");

        // Si on a pas de type, c'est un scalaire
        if (triplet.length == 1)
            return Double.parseDouble(triplet[0]);

        // Sinon on récupère les coordonnées
        double x = Double.parseDouble(triplet[1]);
        double y = Double.parseDouble(triplet[2]);
        double z = Double.parseDouble(triplet[3]);

        // On construit l'objet en fonction du type
        switch (triplet[0]) {
            case "P":
                return new Point(x, y, z);
            case "V":
                return new Vecteur(x, y, z);
            case "C":
                return new Couleur(x, y, z);
            default:
                throw new Exception(triplet[0] + ": Non reconnu");
        }
    }

    public static void main(String[] args) throws Exception {
        String[] data = args[0].split(",");
        String operation = data[1];
        Object o1 = buildObject(data[0]);
        Object o2 = buildObject(data[2]);
        try {
            Class<?> clazz2 = o2.getClass() == Double.class ? double.class : o2.getClass();
            Object o3 = o1.getClass().getMethod(operation, clazz2).invoke(o1, o2);
            System.out.println(o3);
        } catch (Exception e) {
            System.out.println("Interdit");
        }
    }

    /*
     * $ checktriplet.sh "P 1 1 1,add,V 3 3 3" -> P 4.0 4.0 4.0
     * $ checktriplet.sh "P 1 1 1,mul,3" -> P 3.0 3.0 3.0
     * $ checktriplet.sh "P 1 1 1,add,P 3 3 3" -> Interdit
     */
}
