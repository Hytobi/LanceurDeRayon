package math;

public class Point extends Triplet {

    public Point(double x, double y, double z) {
        super(x, y, z);
    }

    public Point() {
        super();
    }

    /**
     * Ajoute un vecteur à un point
     * 
     * @param v le vecteur à ajouter
     * @return le point résultant
     */
    public Point add(Vecteur v) {
        return new Point(this.getX() + v.getX(), this.getY() + v.getY(), this.getZ() + v.getZ());
    }

    /**
     * Soustrait un point à un autre
     * 
     * @param p le point à soustraire
     * @return le vecteur résultant
     */
    public Vecteur sub(Point p) {
        return new Vecteur(this.getX() - p.getX(), this.getY() - p.getY(), this.getZ() - p.getZ());
    }

    /**
     * Soustrait un vecteur à un point
     * 
     * @param v le vecteur à soustraire
     * @return le point résultant
     */
    public Vecteur sub(Vecteur v) {
        return new Vecteur(this.getX() - v.getX(), this.getY() - v.getY(), this.getZ() - v.getZ());
    }

    /**
     * Multiplie un point par un scalaire
     * 
     * @param s le scalaire
     * @return le point résultant
     */
    public Point mul(double s) {
        return new Point(this.getX() * s, this.getY() * s, this.getZ() * s);
    }

    /**
     * Calcule la distance entre deux points
     * 
     * @param p le point à comparer
     * @return la distance entre les deux points
     */
    public double length(Point p) {
        return Math.sqrt((p.getX() - this.getX()) * (p.getX() - this.getX())
                + (p.getY() - this.getY()) * (p.getY() - this.getY())
                + p.getZ() - this.getZ() * p.getZ() - this.getZ());
    }

    @Override
    public String toString() {
        return "P " + this.getX() + " " + this.getY() + " " + this.getZ();
    }

}
