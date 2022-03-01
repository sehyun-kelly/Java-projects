import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Plant extends Life {

    public Plant(Cell cell){
        super(cell, Color.GREEN);
    }

    @Override
    public ArrayList<Neighbour> possiblePaths() {
        ArrayList<Neighbour> newPath = new ArrayList<>();
        int nullCount = 0;
        int plantCount = 0;

        ArrayList<Neighbour> neighbouringCells = World.computeNeighbour(super.currentCell);

        for(int i = 0; i < neighbouringCells.size(); i++){
            int x = neighbouringCells.get(i).getNeighbourX();
            int y = neighbouringCells.get(i).getNeighbourY();

            Cell nextCell = World.grid[x][y];

            if(nextCell.getPresence() == null){
                nullCount++;
            }else if(nextCell.getPresence() != null && nextCell.getPresence().getColor()!= Color.GREEN){
                plantCount++;
            }
        }

        if(nullCount >= 3 && plantCount == 4){
            for(int i = 0; i < neighbouringCells.size(); i++){
                int x = neighbouringCells.get(i).getNeighbourX();
                int y = neighbouringCells.get(i).getNeighbourY();
                Cell nextCell = World.grid[x][y];

                if(nextCell.getPresence() == null){
                    newPath.add(neighbouringCells.get(i));
                }
            }
        }

        return newPath;
    }

    @Override
    public void action() {
        Cell nextCell = chooseCell(possiblePaths());

        if(nextCell.getPresence() == null){
            update();
        }
    }
}
