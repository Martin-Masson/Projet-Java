enum Element {
  WATER,
  FIRE,
  EARTH,
  AIR,
}

enum Direction {
  UP,
  DOWN,
  RIGHT,
  LEFT,
}

public class Case {
  Coord coord;
  int water_level;

  public Case(int x, int y) {
    this.coord = new Coord(x, y);
    this.water_level = 0; // 0 = dry ; 1 = flooded ; 2 = submerged
  }

  Coord coord() {
    return this.coord;
  }

  int x() {
    return this.coord.x();
  }

  int y() {
    return this.coord.y();
  }

  int water_level() {
    return this.water_level;
  }

  void set_coord(int x, int y) {
    this.coord.set_coord(x, y);
  }

  void set_coord(Coord coord) {
    this.coord = coord;
  }

  void set_water_level(int water_level) {
    this.water_level = water_level;
  }

  boolean is_adjacent(Case other) {
    int dx = Math.abs(this.x() - other.x());
    int dy = Math.abs(this.y() - other.y());
    return (dx + dy == 1);
  }

  boolean is_normal() {
    return this.water_level == 0;
  }

  boolean is_flooded() {
    return this.water_level == 1;
  }

  boolean is_submerged() {
    return this.water_level == 2;
  }

  boolean is_in_bound() {
    boolean output = false;
    int i = this.x();
    int j = this.y();
    if (i == 2 || i == 3) {
      output = true;
    } else if ((i == 1 || i == 4) && 1 <= j && j <= 4) {
      output = true;
    } else if ((i == 0 || i == 5) && 2 <= j && j <= 3) {
      output = true;
    }
    return output;
  }

  void dry_out() {
    this.water_level = Math.max(0, this.water_level - 1);
  }

  void flood() {
    this.water_level = Math.min(2, this.water_level + 1);
  }
}
