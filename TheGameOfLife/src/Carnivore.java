import java.awt.*;

public class Carnivore extends Life implements OmniEddible{
    /**Maximum days that Carnivore can live without eating Plant*/
    private final int MAX_DAYS = 5;

    /**Count of days passed without eating Plant*/
    public int daysWithoutFood = 0;

    /**Color of Carnivor*/
    public Color color = Color.RED;

    /**
     * Carnivor constructor
     * @param cell Cell
     */
    public Carnivore(Cell cell){
        super(cell, Color.RED);
    }

    /**
     * Gives birth
     * @param nextCell Cell
     */
    @Override
    public void giveBirth(Cell nextCell) {
        nextCell.setPresence(new Carnivore(nextCell));
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
                if(neighbour instanceof Carnivore){
                    numMates++;
                }else if(neighbour instanceof CarnEddible){
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
    public void action() {
        Cell nextCell = chooseCell(possiblePaths());
        countNeighbours();

        if(nextCell != null){
            if(nextCell.getPresence() == null){
                daysWithoutFood++;
                if(daysWithoutFood == MAX_DAYS){
                    kill();
                }else{
                    if(checkCondition(1,3,2)){
                        giveBirth(nextCell);
                        nextCell.getPresence().setAlive(true);
                    }else{
                        Carnivore carnivore = new Carnivore(nextCell);
                        carnivore.setDaysWithoutFood(this.daysWithoutFood);
                        move(nextCell, carnivore);
                        update();
                    }
                }
            }else if(nextCell.getPresence() != null && !nextCell.getPresence().isCreated){
                if(nextCell.getPresence() instanceof CarnEddible){
                    Carnivore carnivore = new Carnivore(nextCell);
                    eat(nextCell, carnivore);
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
