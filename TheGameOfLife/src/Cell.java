import javax.swing.*;
import java.awt.*;
import java.awt.Graphics2D;


public class Cell extends JPanel {
    public int x;
    public int y;
    public Life presence;
    public Neighbour neighbour;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        setBackground(Color.WHITE);
    }

    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        super.paint(g);
        g.drawRect(0, 0, width, height);
        if(this.presence != null){
            this.presence.paintBackground(g);
        }
    }

    public void setPresence(Life life){
        this.presence = life;
    }

    public Life getPresence(){
        return this.presence;
    }

    public void setNeighbour(Neighbour neighbour) {
        this.neighbour = neighbour;
    }

    public Neighbour getNeighbour(){ return this.neighbour; }



}
