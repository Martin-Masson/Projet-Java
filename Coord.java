public class Coord {
    private int x;
    private int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    boolean equal(int x, int y) {
        return this.x == x && this.y == y;
    }

    boolean equal(Coord other) {
        return this.x == other.x && this.y == other.y;
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

    Coord next_coord(Direction d) {
        return switch (d) {
            case UP -> new Coord(this.x(), Math.max(0, this.y() - 1));
            case DOWN -> new Coord(this.x(), Math.min(6, this.y() + 1));
            case RIGHT -> new Coord(Math.min(6, this.x() + 1), this.y());
            case LEFT -> new Coord(Math.max(0, this.x() - 1), this.y());
        };
    }
}
