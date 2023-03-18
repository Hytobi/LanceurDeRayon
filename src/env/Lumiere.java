package env;

import math.Couleur;
import math.Point;
import math.Vecteur;

public abstract class Lumiere {
    private final Couleur couleur;

    public Lumiere(Couleur couleur) {
        this.couleur = couleur;
    }

    public Couleur getCouleur() {
        return this.couleur;
    }

    public abstract Vecteur getDirection(Point p);

    public abstract boolean estCachee(Scene scene, Point p);
}
