import java.awt.Color;
import java.util.ArrayList;

public class Plant extends Life implements HerbEddible, OmniEddible {

    public ArrayList<Neighbour> neighbours;

    public Color color = Color.GREEN;

    /**
     * Plant constructor
     * @param cell Cell
     */
    public Plant(Cell cell){
        super(cell, Color.GREEN);
    }

    @Override
    public void giveBirth(Cell nextCell) {
        nextCell.setPresence(new Plant(nextCell));
    }

    /**
     * Checks the condition if this Plant can seed and add the qualified cell to the list
     * @return newPath as ArrayList<Neighbour>
     */
    @Override
    public ArrayList<Neighbour> possiblePaths() {
//        ArrayList<Neighbour> newPath = new ArrayList<>();
//        int nullCount = 0;
//        int plantCount = 0;
//
//        ArrayList<Neighbour> neighbouringCells = super.currentCell.computeNeighbour();
//
//        for(int i = 0; i < neighbouringCells.size(); i++){
//            int x = neighbouringCells.get(i).x;
//            int y = neighbouringCells.get(i).y;
//
//            Cell nextCell = World.grid[x][y];
//
//            if(nextCell.getPresence() == null){
//                nullCount++;
//            }else if(nextCell.getPresence() != null && nextCell.getPresence().getColor() == Color.GREEN){
//                plantCount++;
//            }
//        }
//
//        if(nullCount >= 3 && plantCount == 4){
//            for(int i = 0; i < neighbouringCells.size(); i++){
//                int x = neighbouringCells.get(i).x;
//                int y = neighbouringCells.get(i).y;
//                Cell nextCell = World.grid[x][y];
//
//                if(nextCell.getPresence() == null){
//                    newPath.add(neighbouringCells.get(i));
//                }
//            }
//        }
//
//        return newPath;

        this.neighbours = currentCell.computeNeighbour();
        return this.neighbours;
    }

    @Override
    public void countNeighbours() {
        numFood = 0;
        numEmptyCells = 0;
        numMates = 0;
        numOccupied = 0;

        for(Neighbour a : neighbours){
            Life neighbour = World.grid[a.x][a.y].getPresence();
            if(neighbour == null){
                numEmptyCells++;
            }else{
                if(neighbour instanceof Plant){
                    numMates++;
                }else{
                    numOccupied++;
                }
            }
        }
    }

    /**
     * Chooses the next Cell among the possible paths and calls seed method
     */
    @Override
    public void action() {
        Cell nextCell = chooseCell(possiblePaths());
        countNeighbours();

        if(nextCell != null){
            if(nextCell.getPresence() == null){
                if(checkCondition(2,3,0)){
                    giveBirth(nextCell);
//                    System.out.println("Plant birth");
                    nextCell.getPresence().setAlive(true);
                }
            }
        }
    }

//    /**
//     * Seeds(creates another Plant) in the nextCell
//     * @param nextCell Cell
//     */
//    private void seed(Cell nextCell){
//        Life life = new Plant(nextCell);
//        nextCell.setPresence(life);
//        nextCell.getPresence().setColor(Color.GREEN);
//        nextCell.getPresence().setAlive(true);
//    }
}
