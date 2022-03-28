import java.awt.Color;

public class Plant extends Life implements HerbEddible, OmniEddible {

    /**Color of Plant*/
    public Color color = Color.GREEN;

    /**
     * Plant constructor
     * @param cell Cell
     */
    public Plant(Cell cell) {
        super(cell, Color.GREEN);
    }

    /**
     * Gives birth
     * @param nextCell Cell
     */
    @Override
    public void giveBirth(Cell nextCell) {
        nextCell.setPresence(new Plant(nextCell));
        nextCell.getPresence().setCreated(true);
    }

    /**
     * Counts types of neighbouring cells
     */
    @Override
    public void countNeighbours() {
        numFood = 0;
        numEmptyCells = 0;
        numMates = 0;
        numOccupied = 0;

        for (Neighbour a : neighbours) {
            Life neighbour = World.grid[a.x][a.y].getPresence();
            if (neighbour == null) {
                numEmptyCells++;
            } else {
                if (neighbour instanceof Plant) {
                    numMates++;
                } else {
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

        if (nextCell != null) {
            if (nextCell.getPresence() == null) {
                if (checkCondition(2, 3, 0)) {
                    giveBirth(nextCell);
                    nextCell.getPresence().setAlive(true);
                }
            }
        }
    }
}
