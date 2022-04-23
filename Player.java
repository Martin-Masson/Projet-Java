import java.util.*;

public class Player {
    int id;
    Coord coord;
    HashMap<Element, Boolean> keys;
    HashMap<Element, Boolean> artifacts;

    public Player(int id, Coord coord) {
        this.id = id;
        this.coord = coord;
        this.keys = new HashMap<Element, Boolean>(4);
        this.keys = new HashMap<Element, Boolean>(4);
        for (Element e : Element.values()) {
            this.keys.put(e, false);
            this.artifacts.put(e, false);
        }
    }

    boolean has_key(Element e) {
        return this.keys.get(e);
    }

    boolean has_artifact(Element e) {
        return this.artifacts.get(e);
    }

    int id() {
        return this.id;
    }

    Coord coord() {
        return this.coord;
    }

    HashMap<Element, Boolean> keys() {
        return this.keys;
    }

    HashMap<Element, Boolean> artifacts() {
        return this.artifacts;
    }

    void set_id(int id) {
        this.id = id;
    }

    void move(int x, int y) {
        this.coord.set_coord(x, y);
    }

    void move(Coord coord) {
        this.coord = coord;
    }

    void pick_up_key(Element e) {
        this.keys.replace(e, true);
    }

    void pick_up_artifact(Element e) {
        this.artifacts.replace(e, true);
    }
}
