import java.awt.*;
import java.util.ArrayList;

public class World {
    /** number for random generator to indicate total chance*/
    private static final int TOTAL = 100;

    /** number for random generator to indicate the chance of Herbivore creation*/
    private static final int HERBIVORE_VALUE = 85;

    /** number for random generator to indicate the chance of Plant creation*/
    private static final int PLANT_VALUE = 65;

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
        int herbIndex = 0;

        for (int i = 0; i < World.rows; i++) {
            for (int j = 0; j < World.cols; j++) {
                World.grid[i][j] = new Cell(i, j);
                generateLife(World.grid[i][j]);
                if(World.grid[i][j].getPresence()!= null && World.grid[i][j].getPresence().getColor() == Color.YELLOW){
                    ((Herbivore)World.grid[i][j].getPresence()).setHerbIndex(herbIndex);
                    herbIndex++;
                }
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
        }
    }

    /**
     * Controls the turn for Plant to seed first
     * @return lives as ArrayList<Life>
     */
    public ArrayList<Life> firstTurn() {
        resetSeed();
        ArrayList<Life> lives = getAliveLife();

        for (int i = 0; i < lives.size(); i++) {
            Life currentLife = lives.get(i);
            if(currentLife.getColor() == Color.GREEN){
                takeAction(currentLife);
            }
        }

        return lives;
    }

    /**
     * Controls the turn for Herbivores after Plant seeds
     * @param lives ArrayList that stores the cells alive
     */
    public void secondTurn(ArrayList<Life> lives) {
        for (int i = 0; i < lives.size(); i++) {
            Life currentLife = lives.get(i);
            if(currentLife.getColor() == Color.YELLOW){
                takeAction(currentLife);
            }
        }
    }

    /**
     * Runs actions for each Life
     * @param life Life
     */
    private void takeAction(Life life) {
        if (life != null && life.isAlive()) {
            life.action();
        } else {
            life.setAlive(false);
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

    /**
     * Resets the status of isSeed variable of Plant before each firstTurn
     */
    private void resetSeed(){
        for(int i = 0; i < World.rows; i++){
            for(int j = 0; j < World.cols; j++){
                if(World.grid[i][j].getPresence()!= null && World.grid[i][j].getPresence().getColor() == Color.GREEN){
                    ((Plant)World.grid[i][j].getPresence()).setSeed(false);
                }
            }
        }
    }
}
