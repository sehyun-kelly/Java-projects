import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

public class Plant extends Life {

    public Plant(Cell cell){
        super(cell, Color.GREEN);
    }

    @Override
    public Set<Cell> possiblePaths() {
        Set<Cell> newPath = new HashSet<>();

        Set<Cell> neighbouringCells = World.computeNeighbour(super.getCurrentCell());

//        for(Cell nextCell : neighbouringCells){
//            if(nextCell.getPresence().getColor() != Color.GREEN){
//                newPath.add(nextCell);
//            }
//        }

        return newPath;
    }

    @Override
    public void action(Neighbour neighbour) {

    }
}
