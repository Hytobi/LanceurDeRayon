package env;

import math.Couleur;
import math.Point;
import math.Vecteur;

public class LumierePoint extends Lumiere {

    private final Point position;

    public LumierePoint(Point position, Couleur couleur) {
        super(couleur);
        this.position = position;
    }

    public Point getPosition() {
        return this.position;
    }

    public Vecteur getDirection(Point p) {
        return this.position.sub(p).hat();
    }

    public boolean estCachee(Scene scene, Point p) {
        Intersecte i = scene.calculeIntersection(this.getDirection(p), p.add(this.getDirection(p).mul(1E-6)), false);
        return i.intersecte && this.getDirection(p).mul(i.t).length() < this.position
                .sub(p.add(this.getDirection(p).mul(1E-6))).length();
    }
}
