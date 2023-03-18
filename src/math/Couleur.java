package math;

public class Couleur extends Triplet {

    public Couleur(double r, double g, double b) {
        super(r, g, b);
    }

    public Couleur() {
        super();
    }

    /**
     * Multiplie le triplet couleur par un scalaire
     * 
     * @param s le scalaire
     * @return une nouvelle couleur
     */
    public Couleur mul(double s) {
        return new Couleur(this.getX() * s, this.getY() * s, this.getZ() * s);
    }

    /**
     * Ajoute le triplet couleur à un autre triplet couleur
     * 
     * @param c la couleur
     * @return une nouvelle couleur
     */
    public Couleur add(Couleur c) {
        return new Couleur(this.getX() + c.getX(), this.getY() + c.getY(), this.getZ() + c.getZ());
    }

    /**
     * Produit de Schur
     * 
     * @param c la couleur
     * @return une nouvelle couleur
     */
    public Couleur times(Couleur c) {
        return new Couleur(this.getX() * c.getX(), this.getY() * c.getY(), this.getZ() * c.getZ());
    }

    @Override
    public String toString() {
        return "C " + this.getX() + " " + this.getY() + " " + this.getZ();
    }

    public boolean isBlack() {
        return this.getX() == 0 && this.getY() == 0 && this.getZ() == 0;
    }

    public Couleur undo() {
        return new Couleur(
                Math.min(1, Math.max(0, this.getX())),
                Math.min(1, Math.max(0, this.getY())),
                Math.min(1, Math.max(0, this.getZ())));
    }
}
