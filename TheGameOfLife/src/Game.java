import javax.swing.*;
import java.awt.GridLayout;

public class Game extends JFrame {
    private World world;

    public Game(World world){
        this.world = world;
    }

    public void init(){
        int rows = this.world.getRows();
        int cols = this.world.getCols();

        setLayout(new GridLayout(rows, cols));
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++)
                add(this.world.getGrid(row, col));
        }

        //addMouseListener(new TurnListener(this));
    }

    public World getWorld() {
        return this.world;
    }
}
