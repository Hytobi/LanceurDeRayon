package math;

public class Vecteur extends Triplet {

    public Vecteur(double x, double y, double z) {
        super(x, y, z);
    }

    public Vecteur() {
        super();
    }

    /**
     * Ajoute un vecteur à un autre
     * 
     * @param v le vecteur à ajouter
     * @return le vecteur résultant
     */
    public Vecteur add(Vecteur v) {
        return new Vecteur(this.getX() + v.getX(), this.getY() + v.getY(), this.getZ() + v.getZ());
    }

    /**
     * Soustrait un point à un vecteur
     * 
     * @param p le point à soustraire
     * @return le point résultant
     */
    public Point add(Point p) {
        return new Point(this.getX() + p.getX(), this.getY() + p.getY(), this.getZ() + p.getZ());
    }

    /**
     * Soustrait un vecteur à un autre
     * 
     * @param v le vecteur à soustraire
     * @return le vecteur résultant
     */
    public Vecteur sub(Vecteur v) {
        return new Vecteur(this.getX() - v.getX(), this.getY() - v.getY(), this.getZ() - v.getZ());
    }

    /**
     * Multiplie un vecteur par un scalaire
     * 
     * @param s le scalaire
     * @return le vecteur résultant
     */
    public Vecteur mul(double s) {
        return new Vecteur(this.getX() * s, this.getY() * s, this.getZ() * s);
    }

    /**
     * Calcule le produit scalaire entre deux vecteurs
     * 
     * @param v le vecteur à comparer
     * @return le produit scalaire entre les deux vecteurs
     */
    public double dot(Vecteur v) {
        return this.getX() * v.getX() + this.getY() * v.getY() + this.getZ() * v.getZ();
    }

    /**
     * Calcule le produit vectoriel entre deux vecteurs
     * 
     * @param v le vecteur à comparer
     * @return le produit vectoriel entre les deux vecteurs
     */
    public Vecteur cross(Vecteur v) {
        return new Vecteur(this.getY() * v.getZ() - this.getZ() * v.getY(),
                this.getZ() * v.getX() - this.getX() * v.getZ(), this.getX() * v.getY() - this.getY() * v.getX());
    }

    /**
     * Calcule la longueur (norme) d'un vecteur
     * 
     * @return la longueur du vecteur
     */
    public double length() {
        return Math.sqrt(this.getX() * this.getX() + this.getY() * this.getY() + this.getZ() * this.getZ());
    }

    /**
     * Normalisation d'un vecteur
     * 
     * @return le vecteur normalisé
     */
    public Vecteur hat() {
        double length = this.length();
        return new Vecteur(this.getX() / length, this.getY() / length, this.getZ() / length);
    }

    @Override
    public String toString() {
        return "V " + this.getX() + " " + this.getY() + " " + this.getZ();
    }

}
