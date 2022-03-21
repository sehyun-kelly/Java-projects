import java.awt.*;
import java.util.ArrayList;

public class Omnivore extends Life implements CarnEddible{
    /**Maximum days that Omnivore can live without eating Plant*/
    private final int MAX_DAYS = 5;

    /**Count of days passed without eating*/
    public int daysWithoutFood = 0;

    public Color color = Color.BLUE;

    public ArrayList<Neighbour> neighbours;

    public Omnivore(Cell cell){
        super(cell, Color.BLUE);
    }

    @Override
    public void giveBirth(Cell nextCell) {
        nextCell.setPresence(new Omnivore(nextCell));
    }

    @Override
    public ArrayList<Neighbour> possiblePaths() {
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

    @Override
    public void action() {
        //nextCell that is randomly chosen among the possible paths
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
                        System.out.println("Omni birth");
                        nextCell.getPresence().setAlive(true);
                    }else{
                        move(nextCell);
                        System.out.println("Omni move");
                        update();
                    }
                }
            }else{
                if(nextCell.getPresence() instanceof OmniEddible){
                    eat(nextCell);
                    System.out.println("Omni eat");
                    update();
                }else{
                    daysWithoutFood++;
                    if(daysWithoutFood == MAX_DAYS){
                        kill();
                    }
                }
            }
        }
    }

    /**
     * moves to the nextCell
     * @param nextCell Cell
     */
    private void move(Cell nextCell) {
        Omnivore omnivore = new Omnivore(nextCell);
        omnivore.setIndex(this.index);
        omnivore.setDaysWithoutFood(daysWithoutFood);
        nextCell.setPresence(omnivore);
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

        Omnivore life = new Omnivore(nextCell);
        life.setIndex(this.index);
        nextCell.setPresence(life);
    }
}
