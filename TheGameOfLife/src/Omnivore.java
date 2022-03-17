import java.awt.*;
import java.util.ArrayList;

public class Omnivore extends Life implements CarnEddible{
    /**Maximum days that Omnivore can live without eating Plant*/
    private final int MAX_DAYS = 5;

    /**Count of days passed without eating*/
    public int daysWithoutFood = 0;

    public Omnivore(Cell cell){
        super(cell, Color.BLUE);
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
