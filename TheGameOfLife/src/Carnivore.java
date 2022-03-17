import java.awt.*;
import java.util.ArrayList;

public class Carnivore extends Life implements OmniEddible{
    /**Maximum days that Carnivore can live without eating Plant*/
    private final int MAX_DAYS = 5;

    /**Count of days passed without eating Plant*/
    public int daysWithoutFood = 0;

    public Carnivore(Cell cell){
        super(cell, Color.RED);
    }

    @Override
    public Life giveBirth(Cell nextCell) {
        return null;
    }

    @Override
    public ArrayList<Neighbour> possiblePaths() {
        return null;
    }

    @Override
    public void countNeighbours() {

    }

    @Override
    public void action() {

    }
}
