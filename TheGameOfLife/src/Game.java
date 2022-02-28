import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game extends JFrame {
    private World world;

    public Game(World world){
        this.world = world;
    }

    public void init(){
        int rows = this.world.getRows();
        System.out.println("rows: " + rows);
        int cols = this.world.getCols();
        System.out.println("cols: " + cols);

        setLayout(new GridLayout(rows, cols));
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                add(this.world.getGrid(i, j));
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                world.turns();
            }
        });
    }

    public World getWorld() {
        return this.world;
    }
}
