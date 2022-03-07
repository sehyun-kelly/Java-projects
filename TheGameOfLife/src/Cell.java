import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Cell extends JPanel {
    /** X coordinate of this Cell */
    public int x;

    /** Y coordinate of this Cell */
    public int y;

    /** Life that resides in this Cell */
    public Life presence;

    /**
     * Cell Constructor
     * @param x x coordinate of current position
     * @param y y coordinate of current position
     */
    public Cell(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Draws rectangles and add background colors
     * @param g Graphics
     */
    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        super.paint(g);
        g.drawRect(0, 0, width, height);
        paintBackground();
    }

    /**
     * Paints background after each turn
     */
    public void paintBackground(){
        if(this.presence != null && !this.presence.alive){
            this.setPresence(null);
            this.setBackground(Color.WHITE);
        }else if (this.presence == null){
            this.setBackground(Color.WHITE);
        }else {
            this.setBackground(this.presence.color);
        }
    }

    /**
     * Gets the coordinates of all 8 neighbouring cells
     * @return neighbourCells as ArrayList<Neighbour>
     */
    public ArrayList<Neighbour> computeNeighbour(){
        ArrayList<Neighbour> neighbourCells = new ArrayList<>();

        for(int i = this.x - 1; i < this.x + 2; i++){
            for(int j = this.y - 1; j < this.y + 2; j++){
                if(i >= 0 && i <= (World.rows - 1) && j >= 0 && j <= (World.cols - 1)){
                    Neighbour neighbour = new Neighbour(i, j);
                    if(i != this.x || j != this.y){
                        neighbourCells.add(neighbour);
                    }
                }
            }
        }

        return neighbourCells;
    }

    /**
     * Sets Life that resides in the current cell
     * @param life Life
     */
    public void setPresence(Life life){
        this.presence = life;
    }

    /**
     * Gets Life that resides in the current cell
     * @return Life
     */
    public Life getPresence(){
        return this.presence;
    }

}
