import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game extends JFrame {
    private World world;

    public Game(World world){
        this.world = world;
    }

    public void init(){
        int rows = this.world.getRows();
        int cols = this.world.getCols();

        setLayout(new GridLayout(rows, cols));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                add(this.world.getGrid(i, j));
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                nextTurn();
            }
        });
    }

    public void nextTurn() {
        int rows = this.world.getRows();
        int cols = this.world.getCols();

        world.turns();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.world.getGrid(i, j).paintBackground();
            }
        }
    }
}
