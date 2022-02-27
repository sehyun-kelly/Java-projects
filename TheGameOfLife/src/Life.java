import java.awt.*;
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

    public abstract Set<Cell> possiblePaths();

    public abstract void action(Neighbour neighbour);

    public void paintBackground(Graphics g){
        if(currentCell != null){
            this.currentCell.setBackground(this.color);
        }
    }

    public void paintReset(Graphics g){
        if(currentCell != null){
            this.currentCell.setBackground(Color.WHITE);
        }
    }

}
