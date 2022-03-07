import java.awt.Color;
import java.util.ArrayList;

public class Plant extends Life {

    /**Indicates if this Plant is seeded from another Plant*/
    public boolean isSeed = false;

    /**
     * Plant constructor
     * @param cell Cell
     */
    public Plant(Cell cell){
        super(cell, Color.GREEN);
    }

    /**
     * Checks the condition if this Plant can seed and add the qualified cell to the list
     * @return newPath as ArrayList<Neighbour>
     */
    @Override
    public ArrayList<Neighbour> possiblePaths() {
        ArrayList<Neighbour> newPath = new ArrayList<>();
        int nullCount = 0;
        int plantCount = 0;

        ArrayList<Neighbour> neighbouringCells = super.currentCell.computeNeighbour();

        for(int i = 0; i < neighbouringCells.size(); i++){
            int x = neighbouringCells.get(i).x;
            int y = neighbouringCells.get(i).y;

            Cell nextCell = World.grid[x][y];

            if(nextCell.getPresence() == null){
                nullCount++;
            }else if(nextCell.getPresence() != null && nextCell.getPresence().getColor() == Color.GREEN){
                plantCount++;
            }
        }

        if(nullCount >= 3 && plantCount == 4){
            for(int i = 0; i < neighbouringCells.size(); i++){
                int x = neighbouringCells.get(i).x;
                int y = neighbouringCells.get(i).y;
                Cell nextCell = World.grid[x][y];

                if(nextCell.getPresence() == null){
                    newPath.add(neighbouringCells.get(i));
                }
            }
        }

        return newPath;
    }

    /**
     * Chooses the next Cell among the possible paths and calls seed method
     */
    @Override
    public void action() {
        Cell nextCell = chooseCell(possiblePaths());
        if(nextCell != null){
            seed(nextCell);
        }
    }

    /**
     * Seeds(creates another Plant) in the nextCell
     * @param nextCell Cell
     */
    private void seed(Cell nextCell){
        Life life = new Plant(nextCell);
        nextCell.setPresence(life);
        nextCell.getPresence().setColor(Color.GREEN);
        nextCell.getPresence().setAlive(true);
        ((Plant)nextCell.getPresence()).setSeed(true);
    }

    /**
     * Sets the status of isSeed
     * @param isSeed boolean
     */
    public void setSeed(boolean isSeed){
        this.isSeed = isSeed;
    }
}
