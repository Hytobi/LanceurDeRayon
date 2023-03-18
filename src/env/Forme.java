package env;

import math.Couleur;
import math.Point;
import math.Vecteur;

public abstract class Forme {

    private Couleur diffuse;
    private Couleur specular;
    private int shininess;

    public Couleur getDiffuse() {
        return this.diffuse;
    }

    public Couleur getSpecular() {
        return this.specular;
    }

    public int getShininess() {
        return this.shininess;
    }

    public void setDiffuse(Couleur difuse) {
        this.diffuse = difuse;
    }

    public void setSpecular(Couleur specular) {
        this.specular = specular;
    }

    public void setShininess(int shininess) {
        this.shininess = shininess;
    }

    public abstract double intersection(Vecteur direction, Point origine, double epsilon, double tmax);

    public abstract Vecteur getNormal(Point p);
}
