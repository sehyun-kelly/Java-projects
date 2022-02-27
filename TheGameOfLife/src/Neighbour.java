import java.util.Set;

public class Neighbour {
    public Set<Cell> cells;

    public Neighbour(Set<Cell> cells){
        this.cells = cells;
    }

    public Set<Cell> getCells() {
        return this.cells;
    }
}
