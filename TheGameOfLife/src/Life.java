import java.awt.*;
import java.util.ArrayList;

public abstract class Life {
    /**Represents the color of this Life*/
    protected Color color;

    /**Indicates whether this Life is alive or not*/
    protected boolean alive = true;

    /**Indicates the Cell in which this Life currently resides*/
    protected Cell currentCell;

    public int index;
    public int daysWithoutFood = 0;
    public int numMates;
    public int numFood;
    public int numEmptyCells;
    public int numOccupied;

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

    public boolean checkCondition(int numMates, int numEmptycells, int numFood){
        if(this.numMates >= numMates && this.numFood >= numFood
                && this.numEmptyCells > numEmptycells){
            return true;
        }

        return false;
    }

    public void setIndex(int n){
        this.index = n;
    }

    public void kill() {
        this.alive = false;
        this.currentCell.setPresence(null);
        this.color = Color.WHITE;
    }

    private void move(Cell nextCell, Life life) {
        life.setIndex(this.index);
        life.setDaysWithoutFood(daysWithoutFood);
        nextCell.setPresence(life);
    }

    public void setDaysWithoutFood(int daysWithoutFood) {
        this.daysWithoutFood = daysWithoutFood;
    }

    public abstract void giveBirth(Cell nextCell);

    public abstract ArrayList<Neighbour> possiblePaths();

    public abstract void countNeighbours();

    public abstract void action();
}
