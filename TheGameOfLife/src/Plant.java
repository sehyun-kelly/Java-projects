import java.awt.Color;
import java.util.ArrayList;

public class Plant extends Life {

    public boolean isSeed = false;

    public Plant(Cell cell){
        super(cell, Color.GREEN);
    }

    @Override
    public ArrayList<Neighbour> possiblePaths() {
        ArrayList<Neighbour> newPath = new ArrayList<>();
        int nullCount = 0;
        int plantCount = 0;

        ArrayList<Neighbour> neighbouringCells = super.currentCell.computeNeighbour();

        for(int i = 0; i < neighbouringCells.size(); i++){
            int x = neighbouringCells.get(i).getNeighbourX();
            int y = neighbouringCells.get(i).getNeighbourY();

            Cell nextCell = World.grid[x][y];

            if(nextCell.getPresence() == null){
                nullCount++;
            }else if(nextCell.getPresence() != null && nextCell.getPresence().getColor() == Color.GREEN){
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
        if(nextCell != null){
            seed(nextCell);
        }
    }

    private void seed(Cell nextCell){
        Life life = new Plant(nextCell);
        nextCell.setPresence(life);
        nextCell.getPresence().setColor(Color.GREEN);
        nextCell.getPresence().setAlive(true);
        ((Plant)nextCell.getPresence()).setSeed(true);
    }

    public void setSeed(boolean isSeed){
        this.isSeed = isSeed;
    }
}
