import java.awt.*;
import java.util.ArrayList;

public class Carnivore extends Life implements OmniEddible{
    /**Maximum days that Carnivore can live without eating Plant*/
    private final int MAX_DAYS = 5;

    /**Count of days passed without eating Plant*/
    public int daysWithoutFood = 0;

    public Color color = Color.RED;

    public ArrayList<Neighbour> neighbours;

    public Carnivore(Cell cell){
        super(cell, Color.RED);
    }

    @Override
    public void giveBirth(Cell nextCell) {
        nextCell.setPresence(new Carnivore(nextCell));
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
                    if(checkCondition(1,3,2)){
                        giveBirth(nextCell);
                        System.out.println("Carn birth");
                        nextCell.getPresence().setAlive(true);
                    }else{
                        move(nextCell);
                        System.out.println("Carn move");
                        update();
                    }
                }
            }else{
                if(nextCell.getPresence() instanceof CarnEddible){
                    eat(nextCell);
                    System.out.println("Carn eat");
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
        Carnivore carnivore = new Carnivore(nextCell);
        carnivore.setIndex(this.index);
        carnivore.setDaysWithoutFood(daysWithoutFood);
        nextCell.setPresence(carnivore);
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

        Carnivore life = new Carnivore(nextCell);
        life.setIndex(this.index);
        nextCell.setPresence(life);
    }
}
