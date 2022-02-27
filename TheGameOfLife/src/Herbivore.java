import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Herbivore extends Life{

    public int daysWithoutFood = 0;

    public Herbivore(Cell cell){
        super(cell, Color.yellow);
    }

    @Override
    public Set<Cell> possiblePaths() {
        Set<Cell> newPath = new HashSet<>();

        Set<Cell> neighbouringCells = World.computeNeighbour(super.getCurrentCell());

        for(Cell nextCell : neighbouringCells){
            if(nextCell.getPresence().getColor() != Color.YELLOW){
                newPath.add(nextCell);
            }
        }

        return newPath;
    }

    @Override
    public void action(Neighbour neighbour) {

    }

    private Cell chooseCell(Set<Cell> path){
        Cell nextPosition = null;
        List<Cell> cellList = new ArrayList<>();
        cellList.addAll(path);

        int value = RandomGenerator.nextNumber(cellList.size());

        nextPosition = cellList.get(value);

        return nextPosition;

    }

    private
}
