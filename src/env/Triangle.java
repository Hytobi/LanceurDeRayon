package env;

import math.Couleur;
import math.Point;
import math.Vecteur;

public class Triangle extends Forme {
    private final Point point1;
    private final Point point2;
    private final Point point3;

    public Triangle(Point p1, Point p2, Point p3, Couleur d, Couleur sp, int sh) {
        this.point1 = p1;
        this.point2 = p2;
        this.point3 = p3;
        this.setDiffuse(d);
        this.setSpecular(sp);
        this.setShininess(sh);
    }

    public Point getPoint1() {
        return this.point1;
    }

    public Point getPoint2() {
        return this.point2;
    }

    public Point getPoint3() {
        return this.point3;
    }

    public double intersection(Vecteur direction, Point origine, double epsilon, double tmax) {
        Vecteur v1 = this.point2.sub(this.point1);
        Vecteur v2 = this.point3.sub(this.point1);
        Vecteur n = v1.cross(v2);

        double denom = n.dot(direction);
        if (denom != 0) {
            double t = this.point1.sub(origine).dot(n) / denom;
            Point p = origine.add(direction.mul(t));
            if (this.point2.sub(this.point1).cross(p.sub(this.point1)).dot(n) < 0)
                return -1;
            if (this.point3.sub(this.point2).cross(p.sub(this.point2)).dot(n) < 0)
                return -1;
            if (this.point1.sub(this.point3).cross(p.sub(this.point3)).dot(n) < 0)
                return -1;

            if (t > epsilon && t < tmax) {
                return t;
            }
        }
        return -1;
    }

    public Vecteur getNormal(Point p) {
        Vecteur v1 = this.point2.sub(this.point1);
        Vecteur v2 = this.point3.sub(this.point1);
        Vecteur n = v1.cross(v2);
        return n.hat();
    }
}
