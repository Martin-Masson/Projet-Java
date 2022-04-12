import java.util.*;

public class Joueur {
    int id;
    Coord coord;
    HashMap<Element, Boolean> keys;

    public Joueur(int id, Coord coord) {
        this.id = id;
        this.coord = coord;
        this.keys = new HashMap<Element, Boolean>(4);
        for (Element e : Element.values())
            this.keys.put(e, false);
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

    boolean has_key(Element e) {
        return this.keys.get(e);
    }
}
