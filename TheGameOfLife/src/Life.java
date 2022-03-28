import java.awt.*;
import java.util.ArrayList;

public abstract class Life {
    /**Represents the color of this Life*/
    protected Color color;

    /**Indicates whether this Life is alive or not*/
    protected boolean alive = true;

    /**Indicates the Cell in which this Life currently resides*/
    protected Cell currentCell;

    /**Indicate whether this Life is created at this turn*/
    protected boolean isCreated = false;

    /**Number of the same type of Life in the neighbouring cells*/
    protected int numMates;

    /**Number of food in the neighbouring cells*/
    protected int numFood;

    /**Number of empty cells in the neighbouring cells*/
    protected int numEmptyCells;

    /**Number of cells occupied by the other types of Life in the neighbouring cells*/
    protected int numOccupied;

    /**List of neighbouring cells*/
    public ArrayList<Neighbour> neighbours;

    /**
     * Life Constructor
     * @param cell Cell
     * @param color Color
     */
    public Life(Cell cell, Color color){
        this.currentCell = cell;
        this.color = color;
    }

    /**
     * Returns the color of this Life
     * @return color as Color
     */
    public Color getColor(){
        return this.color;
    }

    /**
     * Sets the color of this Life
     * @param color Color
     */
    public void setColor(Color color){
        this.color = color;
    }

    /**
     * Returns the value of alive
     * @return alive as boolean
     */
    public boolean isAlive() {
        return this.alive;
    }

    /**
     * Sets the status of alive
     * @param isAlive boolean
     */
    public void setAlive(boolean isAlive){
        this.alive = isAlive;
    }

    /**
     * Resets the status of this Life into null
     */
    public void update(){
        this.currentCell.getPresence().setAlive(false);
        this.currentCell.setPresence(null);
    }

    /**
     * Chooses the next Cell randomly
     * @param path the list of Neighbours
     * @return nextPosition as Cell
     */
    public Cell chooseCell(ArrayList<Neighbour> path){
        if(path.size() != 0){
            Cell nextPosition;

            int value = RandomGenerator.nextNumber(path.size());

            nextPosition = World.grid[path.get(value).x][path.get(value).y];

            return nextPosition;
        }else {
            return null;
        }
    }

    /**
     * Checks if the birth condition is met
     * @param numMates number of the same type of Life
     * @param numEmptycells number of empty cells
     * @param numFood number of food
     * @return boolean
     */
    public boolean checkCondition(int numMates, int numEmptycells, int numFood){
        if(this.numMates >= numMates && this.numFood >= numFood
                && this.numEmptyCells > numEmptycells){
            return true;
        }

        return false;
    }

    /**
     * Kills this Life and clear the Cell
     */
    public void kill() {
        this.alive = false;
        this.currentCell.setPresence(null);
        this.color = Color.WHITE;
    }

    /**
     * Moves the Life to the nextCell chosen
     * @param nextCell Cell
     * @param life Life
     */
    public void move(Cell nextCell, Life life) {
        nextCell.setPresence(life);
    }

    /**
     * Eats the food
     * @param nextCell Cell
     * @param life Life
     */
    public void eat(Cell nextCell, Life life) {
        if (nextCell.getPresence() != null && nextCell.getPresence().isAlive()) {
            nextCell.getPresence().setAlive(false);
            nextCell.setPresence(null);
        }

        life.setCreated(true);
        nextCell.setPresence(life);
    }

    /**
     * Calculates the Neighbouring cells
     * @return neighbours as ArrayList<Neighbour>
     */
    public ArrayList<Neighbour> possiblePaths(){
        this.neighbours = currentCell.computeNeighbour();
        return this.neighbours;
    }

    /**
     * Sets isCreated value
     * @param isCreated boolean
     */
    public void setCreated(boolean isCreated){
        this.isCreated = isCreated;
    }

    public abstract void giveBirth(Cell nextCell);

    public abstract void countNeighbours();

    public abstract void action();
}
