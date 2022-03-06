import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Life {
    protected Color color;
    protected boolean alive = true;
    protected Cell currentCell;

    public Life(Cell cell, Color color){
        this.currentCell = cell;
        this.color = color;
    }

    public Color getColor(){
        return this.color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public void setAlive(boolean isAlive){
        this.alive = isAlive;
    }

    public void update(){
        this.currentCell.getPresence().setAlive(false);
        this.currentCell.setPresence(null);
    }

    public Cell chooseCell(ArrayList<Neighbour> path){
        if(path.size() != 0){
            Cell nextPosition = null;

            int value = RandomGenerator.nextNumber(path.size());

            nextPosition = World.grid[path.get(value).getNeighbourX()][path.get(value).getNeighbourY()];

            return nextPosition;
        }else {
            return null;
        }
    }

    public abstract ArrayList<Neighbour> possiblePaths();

    public abstract void action();


}
