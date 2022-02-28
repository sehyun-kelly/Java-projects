import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

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

    public Cell getCurrentCell(){
        return this.currentCell;
    }

    public void setAlive(boolean isAlive){
        this.alive = isAlive;
    }

    public void kill(){
        this.alive = false;
        this.currentCell.setBackground(Color.WHITE);
        this.currentCell.setPresence((Life)null);
    }

    public void update(Cell nextCell, Color color){
        nextCell.setBackground(color);
        nextCell.setPresence(this);

        this.currentCell.setBackground(Color.WHITE);
        this.currentCell.setPresence((Life)null);
    }

    public void paintBackground(Graphics g){
        if(currentCell != null){
            this.currentCell.setBackground(this.color);
        }
    }

    public abstract ArrayList<Neighbour> possiblePaths();

    public abstract void action();

}
