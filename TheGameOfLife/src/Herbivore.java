import java.awt.*;
import java.util.ArrayList;

public class Herbivore extends Life implements CarnEddible, OmniEddible {
    /**Maximum days that Herbivore can live without eating Plant*/
    private final int MAX_DAYS = 5;

    /**Count of days passed without eating Plant*/
    public int daysWithoutFood = 0;

    /**The index of this Herbivore*/
    public int herbIndex;

    public Color color = Color.YELLOW;

    public ArrayList<Neighbour> neighbours;

    /**
     * Herbivore constructor
     * @param cell Cell
     */
    public Herbivore(Cell cell) {
        super(cell, Color.yellow);
    }


    @Override
    public Life giveBirth(Cell nextCell) {
        return new Herbivore(nextCell);
    }

    /**
     * Calculates possible paths that this Herbivore can move into
     * @return the list of Neighbours
     */
    @Override
    public ArrayList<Neighbour> possiblePaths() {
        this.neighbours = currentCell.computeNeighbour();
        return this.neighbours;
    }

    @Override
    public void countNeighbours() {
        for(Neighbour a : neighbours){
            Life neighbour = World.grid[a.x][a.y].getPresence();
            if(neighbour == null){
                numEmptyCells++;
            }else{
                if(neighbour instanceof Herbivore){
                    numMates++;
                }else if(neighbour instanceof HerbEddible){
                    numFood++;
                }else{
                    numOccupied++;
                }
            }
        }
    }

    /**
     * Chooses the next Cell among the possible paths and takes actions based on the status of next Cell
     */
    @Override
    public void action() {
        //nextCell that is randomly chosen among the possible paths
        Cell nextCell = chooseCell(possiblePaths());

        if (nextCell != null) {
            //if nextCell doesn't have any Life in it
            if (nextCell.getPresence() == null) {
                daysWithoutFood++;
                //if it reaches maxDays without eating Plant, it dies
                if (daysWithoutFood == MAX_DAYS) {
                    kill();
                //if it doesn't reach maxDays yet, it moves to nextCell
                } else {
                    move(nextCell);
                    update();
                }
            //if nextCell has Plant and it's not seeded at the current turn, then it eats the Plant
            } else if (nextCell.getPresence() != null && nextCell.getPresence().getColor() == Color.GREEN
                    && !((Plant) nextCell.getPresence()).isSeed) {
                daysWithoutFood = 0;
                eat(nextCell);
                update();
            /* if nextCell has Plant and it's seeded at the current turn, then it stays and increases daysWithoutFood.
               If it reaches maxDays after daysWithoutFood incremented, it dies.
             */
            } else if (nextCell.getPresence() != null && nextCell.getPresence().getColor() == Color.GREEN
                    && ((Plant) nextCell.getPresence()).isSeed) {
                daysWithoutFood++;
                if (daysWithoutFood == MAX_DAYS) {
                    kill();
                }
            /* if nextCell has another Herbivore, then it stays and increases daysWithoutFood.
               If it reaches maxDays after daysWithoutFood incremented, it dies.
             */
            } else if (nextCell.getPresence() != null && nextCell.getPresence().getColor() == Color.YELLOW) {
                daysWithoutFood++;
                if (daysWithoutFood == MAX_DAYS) {
                    kill();
                }
            }
        }
    }

    /**
     * Kills this Herbivore by setting alive to false and its presence to null
     */
    private void kill() {
        this.alive = false;
        this.currentCell.setPresence(null);
        this.color = Color.WHITE;
    }

    /**
     * moves to the nextCell
     * @param nextCell Cell
     */
    private void move(Cell nextCell) {
        Herbivore herbivore = new Herbivore(nextCell);
        herbivore.setDaysWithoutFood(daysWithoutFood);
        nextCell.setPresence(herbivore);
        nextCell.getPresence().setColor(Color.YELLOW);
        nextCell.getPresence().setAlive(true);
    }

    /**
     * Sets daysWithoutFood value
     * @param daysWithoutFood int
     */
    private void setDaysWithoutFood(int daysWithoutFood) {
        this.daysWithoutFood = daysWithoutFood;
    }

    /**
     * Eats the Plant in the nextCell
     * @param nextCell Cell
     */
    private void eat(Cell nextCell) {
        if (nextCell.getPresence() != null && nextCell.getPresence().isAlive()) {
            nextCell.getPresence().setAlive(false);
            nextCell.setPresence(null);
        }

        Herbivore life = new Herbivore(nextCell);
        life.setDaysWithoutFood(daysWithoutFood);
        nextCell.setPresence(life);
        nextCell.getPresence().setColor(Color.YELLOW);
        nextCell.getPresence().setAlive(true);
    }

}
