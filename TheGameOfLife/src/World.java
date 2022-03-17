import java.awt.*;
import java.util.ArrayList;

public class World {
    /** number for random generator to indicate total chance*/
    private static final int TOTAL = 100;

    /** number for random generator to indicate the chance of Herbivore creation*/
    private static final int HERBIVORE_VALUE = 80;

    /** number for random generator to indicate the chance of Plant creation*/
    private static final int PLANT_VALUE = 60;

    /** number for random generator to indicate the chance of Carnivore creation*/
    private static final int CARNIVORE_VALUE = 50;

    /** number for random generator to indicate the chance of Omnivore creation*/
    private static final int OMNIVORE_VALUE = 45;

    /** total number of rows for this World */
    public static int rows;

    /** total number of columns for this World */
    public static int cols;

    /** 2d array to store Cells for this World */
    public static Cell[][] grid;

    /**
     * World Constructor
     * @param rows total number of rows for this World
     * @param cols total number of rows for this World
     */
    public World(int rows, int cols) {
        World.rows = rows;
        World.cols = cols;
        World.grid = new Cell[World.rows][World.cols];
    }

    /**
     * Returns the Cell at X = rows and Y = cols position
     * @param rows int
     * @param cols int
     * @return World.grid[rows][cols] as Cell
     */
    public Cell getGrid(int rows, int cols) {
        return World.grid[rows][cols];
    }

    /**
     * Adds cells to this World and adds Life into each Cell
     */
    public void init() {
        RandomGenerator.reset();

        for (int i = 0; i < World.rows; i++) {
            for (int j = 0; j < World.cols; j++) {
                World.grid[i][j] = new Cell(i, j);
                generateLife(World.grid[i][j]);
            }
        }
    }

    /**
     * Generates Life based on the possibility to put into cell
     * @param cell Cell
     */
    private void generateLife(Cell cell) {
        int value = RandomGenerator.nextNumber(TOTAL);

        if (value >= HERBIVORE_VALUE) {
            cell.setPresence(new Herbivore(cell));
        } else if (value >= PLANT_VALUE) {
            cell.setPresence(new Plant(cell));
        } else if (value >= CARNIVORE_VALUE) {
            cell.setPresence(new Carnivore(cell));
        } else if (value >= OMNIVORE_VALUE) {
            cell.setPresence(new Omnivore(cell));
        }
    }

    /**
     * Controls the turn for Plant to seed first
     * @return lives as ArrayList<Life>
     */
    public void turn() {
        ArrayList<Life> lives = getAliveLife();

        for (int i = 0; i < lives.size(); i++) {
            Life currentLife = lives.get(i);
            if(currentLife != null && currentLife.isAlive()){
                currentLife.action();
            }else{
                currentLife.setAlive(false);
            }
        }

    }

    /**
     * Gets a list of Cells alive in this World
     * @return lives as ArrayList<Life>
     */
    private ArrayList<Life> getAliveLife() {
        ArrayList<Life> lives = new ArrayList<>();
        Life currentLife;

        for (int i = 0; i < World.grid.length; i++) {
            for (int j = 0; j < World.grid[i].length; j++) {
                currentLife = grid[i][j].getPresence();
                if (currentLife != null) {
                    lives.add(currentLife);
                }
            }
        }

        return lives;
    }

}
