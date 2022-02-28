import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Plant extends Life {

    public Plant(Cell cell){
        super(cell, Color.GREEN);
    }

    @Override
    public ArrayList<Neighbour> possiblePaths() {
        return null;
    }

    @Override
    public void action() {
        System.out.println("I'm a plant");
    }
}
