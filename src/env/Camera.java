package env;

import math.Point;
import math.Vecteur;

public class Camera {
    private Point lookFrom; // Position de l'oeil
    private Vecteur lookAt; // Point visé par l'oeil
    private Vecteur up; // la direction vers le haut de l'oeil
    private double fov; // L'angle de vue de l'oeil

    // Pas de constructeur, on va la set dans la scene

    public Point getLookFrom() {
        return this.lookFrom;
    }

    public void setLookFrom(Point lookFrom) {
        this.lookFrom = lookFrom;
    }

    public Vecteur getLookAt() {
        return this.lookAt;
    }

    public void setLookAt(Vecteur lookAt) {
        this.lookAt = lookAt;
    }

    public Vecteur getUp() {
        return this.up;
    }

    public void setUp(Vecteur up) {
        this.up = up;
    }

    public double getFov() {
        return this.fov;
    }

    public void setFov(double fov) {
        this.fov = fov;
    }
}
