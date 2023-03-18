package env;

import math.Couleur;
import math.Vecteur;
import math.Point;

public class LumiereDirection extends Lumiere {

    private final Vecteur direction;

    public LumiereDirection(Vecteur direction, Couleur couleur) {
        super(couleur);
        this.direction = direction;
    }

    public Vecteur getDirection(Point p) {
        return this.direction.hat();
    }

    public boolean estCachee(Scene scene, Point p) {
        return scene.calculeIntersection(this.getDirection(p), p.add(this.getDirection(p).mul(1E-6)), false).intersecte;

    }
}
