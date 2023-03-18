package env;

import math.Couleur;
import math.Point;
import math.Vecteur;

public class Plan extends Forme {
    private final Point passeParCePoint;
    private final Vecteur saNormale;

    public Plan(Point p, Vecteur n, Couleur d, Couleur sp, int sh) {
        this.passeParCePoint = p;
        this.saNormale = n;
        this.setDiffuse(d);
        this.setSpecular(sp);
        this.setShininess(sh);
    }

    public Point getPasseParCePoint() {
        return this.passeParCePoint;
    }

    public Vecteur getSaNormale() {
        return this.saNormale;
    }

    public double intersection(Vecteur direction, Point origine, double epsilon, double tmax) {
        double denom = this.saNormale.dot(direction);
        if (denom != 0) {
            double t = this.passeParCePoint.sub(origine).dot(this.saNormale) / denom;
            if (t > epsilon && t < tmax) {
                return t;
            }
        }
        return -1;
    }

    public Vecteur getNormal(Point p) {
        return this.saNormale.hat();
    }
}
