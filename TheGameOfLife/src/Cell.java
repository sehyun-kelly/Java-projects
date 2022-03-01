import javax.swing.*;
import java.awt.*;

public class Cell extends JPanel {
    public int x;
    public int y;
    public Life presence;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        super.paint(g);
        g.drawRect(0, 0, width, height);
        paintBackground();
    }

    public void paintBackground(){
        if(this.presence != null && this.presence.alive == false){
            this.setPresence(null);
            this.setBackground(Color.WHITE);
        }else if (this.presence == null){
            this.setBackground(Color.WHITE);
        }else if(this.presence != null && this.presence.alive == true){
            this.setBackground(this.presence.color);
        }
    }

    public void setPresence(Life life){
        this.presence = life;
    }

    public Life getPresence(){
        return this.presence;
    }

    public int returnX() {
        return this.x;
    }

    public int returnY() {
        return this.y;
    }

}
