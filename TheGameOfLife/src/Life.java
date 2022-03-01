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
            List<Neighbour> cellList = new ArrayList<>();
            cellList.addAll(path);

            int value = RandomGenerator.nextNumber(cellList.size());

            nextPosition = World.grid[cellList.get(value).getNeighbourX()][cellList.get(value).getNeighbourY()];

            return nextPosition;
        }else {
            return currentCell;
        }
    }

    public abstract ArrayList<Neighbour> possiblePaths();

    public abstract void action();


}
