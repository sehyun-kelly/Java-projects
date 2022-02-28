import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Herbivore extends Life{
    private final int maxDays = 5;

    public int daysWithoutFood = 0;

    public Herbivore(Cell cell){
        super(cell, Color.yellow);
    }

    @Override
    public ArrayList<Neighbour> possiblePaths() {
        System.out.println("possiblepaths");
        ArrayList<Neighbour> newPath = new ArrayList<>();

        ArrayList<Neighbour> neighbouringCells = World.computeNeighbour(super.getCurrentCell());
        System.out.println("current cell: " + super.getCurrentCell().returnX() + " " + super.getCurrentCell().returnY());

        for(int i = 0; i < neighbouringCells.size(); i++){
            int x = neighbouringCells.get(i).getNeighbourX();
            int y = neighbouringCells.get(i).getNeighbourY();

            Cell nextCell = World.grid[x][y];
            System.out.println(i + ": " + x + ", " + y);
            if(nextCell.getPresence() == null){
                newPath.add(neighbouringCells.get(i));
            }else if(nextCell.getPresence() != null && nextCell.getPresence().getColor()!= Color.yellow){
                newPath.add(neighbouringCells.get(i));
                System.out.println("added to newpath: x: " + x + ", y: " + y);
            }
        }

        System.out.println("size: " + newPath.size());

        return newPath;
    }

    private Cell chooseCell(ArrayList<Neighbour> path){
        System.out.println("choosecell");
        Cell nextPosition = null;
        List<Neighbour> cellList = new ArrayList<>();
        cellList.addAll(path);

        int value = RandomGenerator.nextNumber(cellList.size());

        nextPosition = World.grid[cellList.get(value).getNeighbourX()][cellList.get(value).getNeighbourY()];

        return nextPosition;

    }

    @Override
    public void action() {
        Cell nextCell = chooseCell(possiblePaths());

        if(nextCell.getPresence() == null){
            daysWithoutFood++;
            if(daysWithoutFood == maxDays){
                kill();
            } else{
                update(nextCell, Color.yellow);
            }
        }else if(nextCell.getPresence() != null && nextCell.getPresence().getColor() == Color.GREEN){
            update(nextCell, Color.yellow);
            daysWithoutFood = 0;
        }
    }



}
