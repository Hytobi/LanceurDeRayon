package math;

public class Triplet {

    private final double x;
    private final double y;
    private final double z;

    public Triplet(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Triplet() {
        this(0, 0, 0);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }

    public boolean equals(Object o) {
        if (o instanceof Triplet) {
            Triplet t = (Triplet) o;
            return x == t.x && y == t.y && z == t.z;
        }
        return false;
    }
}