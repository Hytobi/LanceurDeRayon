package env;

import math.Couleur;
import math.Point;
import math.Vecteur;

public class Sphere extends Forme {
    private final Point centre;
    private final double rayon;

    private Vecteur normal;

    public Sphere(Point c, double r, Couleur d, Couleur sp, int sh) {
        this.centre = c;
        this.rayon = r;
        this.setDiffuse(d);
        this.setSpecular(sp);
        this.setShininess(sh);
    }

    public Point getCentre() {
        return this.centre;
    }

    public double getRayon() {
        return this.rayon;
    }

    public double intersection(Vecteur direction, Point origine, double epsilon, double tmax) {
        Vecteur oc = origine.sub(this.centre);

        double a = direction.dot(direction);
        double b = 2 * oc.dot(direction);
        double c = oc.dot(oc) - this.rayon * this.rayon;

        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return -1;
        }

        double t2 = (-b - Math.sqrt(discriminant)) / (2 * a);
        double t1 = (-b + Math.sqrt(discriminant)) / (2 * a);

        if (t2 > epsilon && t2 < tmax) {
            Vecteur inv_nor = origine.add(direction.mul(t2)).sub(this.centre).mul(1 / this.rayon);
            this.normal = (inv_nor.dot(direction) < 0) ? inv_nor : inv_nor.mul(-1);
            return t2;
        } else if (t1 > epsilon && t1 < tmax) {
            Vecteur inv_nor = origine.add(direction.mul(t1)).sub(this.centre).mul(1 / this.rayon);
            this.normal = (inv_nor.dot(direction) < 0) ? inv_nor : inv_nor.mul(-1);
            return t1;
        } else {
            return -1;
        }

        // return (t2 > epsilon && t2 < tmax) ? t2 : (t1 > epsilon && t1 < tmax) ? t1
        // :-1;

    }

    @Override
    public Vecteur getNormal(Point p) {
        return this.normal.hat();
    }
}
