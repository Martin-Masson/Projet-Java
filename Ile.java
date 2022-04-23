import java.util.*;

public class Ile {
    Case[][] grid;
    Coord heliport;
    HashMap<Coord, Element> artifacts;
    HashMap<Integer, Player> players;
    ArrayList<Case> not_submerged;
    ArrayList<Case> submerged;

    public Ile(Coord heliport, HashMap<Coord, Element> artifacts, ArrayList<Player> players) {
        this.not_submerged = new ArrayList<Case>();
        this.submerged = new ArrayList<Case>();
        this.grid = new Case[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Case new_case = new Case(i, j);
                this.grid[i][j] = new_case;
                if (new_case.is_in_bound())
                    this.not_submerged.add(new_case);
            }
        }
        this.heliport = heliport;
        this.artifacts = artifacts;
        this.players = new HashMap<Integer, Player>();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            this.players.put(player.id(), player);
        }
    }

    Case get_case(int x, int y) {
        return grid[x][y];
    }

    Case get_case(Coord coord) {
        return grid[coord.x()][coord.y()];
    }

    Coord get_heliport() {
        return this.heliport;
    }

    HashMap<Coord, Element> get_artifacts() {
        return this.artifacts;
    }

    Element get_element(Coord c) {
        return this.artifacts.get(c);
    }

    Player get_player(int id) {
        return this.players.get(id);
    }

    int kill(int id) {
        this.players.remove(id);
        return id;
    }

    Case next_case(Coord c, Direction d) {
        return this.get_case(c.next_coord(d));
    }

    void move_player(int id, Direction d) {
        Player player = this.get_player(id);
        Case next_case = this.next_case(player.coord(), d);
        if (!next_case.is_submerged() && next_case.is_in_bound())
            player.move(next_case.coord());
    }

    void dry_out(int id, Coord c) {
        Player player = this.get_player(id);
        Coord position = player.coord();
        Case target = this.get_case(c);
        if (!target.is_submerged() && target.is_adjacent(position))
            target.dry_out();
    }

    void pick_up_artifact(int id) {
        Player player = this.get_player(id);
        Coord position = player.coord();
        if (this.artifacts.containsKey(position)) {
            Element e = this.get_element(position);
            if (player.has_key(e))
                player.pick_up_artifact(e);
        }
    }

    void search_key(int id) {
        Player player = this.get_player(id);
        Random rand = new Random();
        int roll = rand.nextInt(100) + 1; // roll € [1, 100]
        if (roll > 75) {
            Element random_elem = Element.values()[rand.nextInt(4)];
            player.pick_up_key(random_elem);
        } else if (roll <= 10) {
            Case position = this.get_case(player.coord());
            position.flood();
        }
    }

    Case random_not_submerged_case() {
        Random rand = new Random();
        Case random_case = this.not_submerged.get(rand.nextInt(this.not_submerged.size()));
        return random_case;
    }

    void flood() {
        for (int i = 0; i < 3; i++) {
            Case random_case = this.random_not_submerged_case();
            random_case.flood();
            this.not_submerged.remove(random_case);
            this.submerged.add(random_case);
        }
    }

    boolean is_inaccessible(Case c) {
        if (c.is_submerged())
            return true;
        else
            for (Direction d : Direction.values())
                if (!this.next_case(c.coord(), d).is_submerged())
                    return false;
        return true;
    }

    boolean is_winnable() {
        // if all players are dead
        if (players.isEmpty())
            return false;

        // if the heliport is inaccessible
        if (this.is_inaccessible(this.get_case(heliport)))
            return false;

        // an artifact zone is inaccessible and the corresponding artifact didn't get
        // fetched
        for (Coord coord : this.artifacts.keySet()) {
            Case c = this.get_case(coord);
            if (this.is_inaccessible(c)) {
                Element elem = this.get_element(c.coord());
                boolean fetched = false;
                for (Player player : this.players.values()) {
                    if (player.has_artifact(elem)) {
                        fetched = true;
                        break;
                    }
                }
                if (!fetched)
                    return false;
            }
        }

        return true;
    }
}
