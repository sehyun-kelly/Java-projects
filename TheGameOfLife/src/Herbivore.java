import java.awt.*;

public class Herbivore extends Life implements CarnEddible, OmniEddible {
    /**Maximum days that Herbivore can live without eating Plant*/
    private final int MAX_DAYS = 5;

    /**Count of days passed without eating Plant*/
    public int daysWithoutFood = 0;

    /**Color of Herbivore*/
    public Color color = Color.YELLOW;

    /**
     * Herbivore constructor
     * @param cell Cell
     */
    public Herbivore(Cell cell) {
        super(cell, Color.yellow);
    }

    /**
     * Gives birth
     * @param nextCell Cell
     */
    @Override
    public void giveBirth(Cell nextCell) {
        nextCell.setPresence(new Herbivore(nextCell));
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
        Cell nextCell = chooseCell(possiblePaths());
        countNeighbours();

        if(nextCell != null){
            if(nextCell.getPresence() == null){
                daysWithoutFood++;

                if(daysWithoutFood == MAX_DAYS){
                    kill();
                }else{
                    if(checkCondition(1,2,2)){
                        giveBirth(nextCell);
                        nextCell.getPresence().setAlive(true);
                    }else{
                        Herbivore herbivore = new Herbivore(nextCell);
                        herbivore.setDaysWithoutFood(this.daysWithoutFood);
                        move(nextCell, herbivore);
                        update();
                    }
                }
            }else if(nextCell.getPresence() != null && !nextCell.getPresence().isCreated){
                if(nextCell.getPresence() instanceof HerbEddible){
                    Herbivore herbivore = new Herbivore(nextCell);
                    eat(nextCell, herbivore);
                    update();
                }else{
                    daysWithoutFood++;
                    if(daysWithoutFood == MAX_DAYS){
                        kill();
                    }
                }
            }else if(nextCell.getPresence() != null && nextCell.getPresence().isCreated){
                daysWithoutFood++;

                if(daysWithoutFood == MAX_DAYS) {
                    kill();
                }
            }
        }
    }

    private void setDaysWithoutFood(int daysWithoutFood){
        this.daysWithoutFood = daysWithoutFood;
    }


}
