public class Coord {
    private int x;
    private int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    int x() {
        return this.x;
    }

    int y() {
        return this.y;
    }

    void set_coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    boolean equal(int x, int y) {
        return this.x == x && this.y == y;
    }

    boolean equal(Coord other) {
        return this.x == other.x && this.y == other.y;
    }

}
