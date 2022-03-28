import java.awt.*;

public class Omnivore extends Life implements CarnEddible{
    /**Maximum days that Omnivore can live without eating Plant*/
    private final int MAX_DAYS = 5;

    /**Count of days passed without eating*/
    public int daysWithoutFood = 0;

    /**Color of Omnivore*/
    public Color color = Color.BLUE;

    /**
     * Omnivore constructor
     * @param cell Cell
     */
    public Omnivore(Cell cell){
        super(cell, Color.BLUE);
    }

    /**
     * Gives birth
     * @param nextCell Cell
     */
    @Override
    public void giveBirth(Cell nextCell) {
        nextCell.setPresence(new Omnivore(nextCell));
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
                if(neighbour instanceof Omnivore){
                    numMates++;
                }else if(neighbour instanceof OmniEddible){
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
                    if(checkCondition(1,3,1)){
                        giveBirth(nextCell);
                        nextCell.getPresence().setAlive(true);
                    }else{
                        Omnivore omnivore = new Omnivore(nextCell);
                        omnivore.setDaysWithoutFood(this.daysWithoutFood);
                        move(nextCell, omnivore);
                        update();
                    }
                }
            }else if(nextCell.getPresence() != null && !nextCell.getPresence().isCreated){
                if(nextCell.getPresence() instanceof OmniEddible){
                    Omnivore omnivore = new Omnivore(nextCell);
                    eat(nextCell, omnivore);
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
