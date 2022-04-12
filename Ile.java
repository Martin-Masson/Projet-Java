import java.util.*;

public class Ile {
    Case[][] grid;
    Coord heliport;
    HashMap<Element, Coord> artifacts;
    HashMap<Integer, Joueur> players;
    ArrayList<Case> not_submerged;
    ArrayList<Case> submerged;

    public Ile(Coord heliport, HashMap<Element, Coord> artifacts, ArrayList<Joueur> players) {
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
        this.players = new HashMap<Integer, Joueur>();
        for (int i = 0; i < players.size(); i++) {
            Joueur player = players.get(i);
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

    Coord get_artifact(Element elem) {
        return this.artifacts.get(elem);
    }

    Joueur get_player(int id) {
        return this.players.get(id);
    }

    int kill(int id) {
        this.players.remove(id);
        return id;
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

    Coord next_coord(Coord c, Direction d) {
        switch (d) {
            case UP:
                return this.get_case(c.x(), Math.max(0, c.y() - 1)).coord();
            case DOWN:
                return this.get_case(c.x(), Math.min(6, c.y() + 1)).coord();
            case RIGHT:
                return this.get_case(Math.min(6, c.x() + 1), c.y()).coord();
            case LEFT:
                return this.get_case(Math.max(0, c.x() - 1), c.y()).coord();
            case default:
                return c;
        }
    }

    void move_player(int id, Direction d) {
        Joueur player = this.get_player(id);
        Coord next_coord = this.next_coord(player.coord(), d);
        if (!this.get_case(next_coord).is_submerged())
            player.move(next_coord);
    }
}
