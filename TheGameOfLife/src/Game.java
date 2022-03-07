import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Game extends JFrame {
    /** World object that this Game object controls*/
    private World world;

    /**
     * Game Constructor
     * @param world World
     */
    public Game(World world){
        this.world = world;
    }

    /**
     * Initializes the game frame and implements a mouse listener for each turn
     */
    public void init(){
        int rows = World.rows;
        int cols = World.cols;

        setTitle("Game Of Life");
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

    /**
     * Initiates turns after each mouse click
     */
    public void nextTurn() {
        int rows = World.rows;
        int cols = World.cols;

        ArrayList<Life> lives = world.firstTurn();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.world.getGrid(i, j).paintBackground();
            }
        }

        world.secondTurn(lives);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                this.world.getGrid(i, j).paintBackground();
            }
        }
    }
}
