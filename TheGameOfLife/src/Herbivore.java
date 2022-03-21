import java.awt.*;
import java.util.ArrayList;

public class Herbivore extends Life implements CarnEddible, OmniEddible {
    /**Maximum days that Herbivore can live without eating Plant*/
    private final int MAX_DAYS = 5;

    /**Count of days passed without eating Plant*/
//    public int daysWithoutFood = 0;

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
    public void giveBirth(Cell nextCell) {
        nextCell.setPresence(new Herbivore(nextCell));
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
        //nextCell that is randomly chosen among the possible paths
        Cell nextCell = chooseCell(possiblePaths());
        countNeighbours();

        if(nextCell != null){
            if(nextCell.getPresence() == null){
                daysWithoutFood++;
                System.out.print("daysWithoutFood: " + daysWithoutFood + " ");

                if(daysWithoutFood == MAX_DAYS){
                    kill();
                    System.out.println("Herb kill");
                }else{
                    if(checkCondition(1,2,2)){
                        giveBirth(nextCell);
                        System.out.println("Herb birth");
                        nextCell.getPresence().setAlive(true);
                    }else{
                        move(nextCell);
                        System.out.println("Herb move");
                        update();
                    }
                }
            }else{
                if(nextCell.getPresence() instanceof HerbEddible){
                    eat(nextCell);
                    System.out.println("Herb eat");
                    update();
                }else{
                    daysWithoutFood++;
                    System.out.println("Herb stay");
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
        Herbivore herbivore = new Herbivore(nextCell);
        herbivore.setIndex(this.index);
        herbivore.setDaysWithoutFood(daysWithoutFood);
        nextCell.setPresence(herbivore);
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
        life.setIndex(this.index);
        nextCell.setPresence(life);
    }

}
