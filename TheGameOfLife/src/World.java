import java.awt.*;
import java.util.ArrayList;

public class World {
    private static final int TOTAL = 100;
    private static final int HERBIVORE_VALUE = 85;
    private static final int PLANT_VALUE = 65;
    public static int rows;
    public static int cols;
    public static Cell[][] grid;

    public World(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Cell[this.rows][this.cols];
    }

    public int getRows() {
        return this.rows;
    }

    public int getCols() {
        return this.cols;
    }

    public Cell getGrid(int rows, int cols) {
        return this.grid[rows][cols];
    }

    public void init() {
        RandomGenerator.reset();
        int herbIndex = 0;

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.grid[i][j] = new Cell(i, j);
                generateLife(this.grid[i][j]);
                if(this.grid[i][j].getPresence()!= null && this.grid[i][j].getPresence().getColor() == Color.YELLOW){
                    ((Herbivore)this.grid[i][j].getPresence()).setHerbIndex(herbIndex);
                    herbIndex++;
                }
            }
        }
    }

    private void generateLife(Cell cell) {
        int value = RandomGenerator.nextNumber(TOTAL);

        if (value >= HERBIVORE_VALUE) {
            cell.setPresence(new Herbivore(cell));
        } else if (value >= PLANT_VALUE && value < HERBIVORE_VALUE) {
            cell.setPresence(new Plant(cell));
        }
    }

    public void turns() {
        resetSeed();
        ArrayList<Life> lives = getAliveLife();

        for(int i = 0; i < lives.size(); i++){
            Life currentLife = lives.get(i);
//            if(currentLife != null && currentLife.getColor() == Color.YELLOW){
//                System.out.println("X: " + currentLife.currentCell.x + "Y: " + currentLife.currentCell.y);
//            }
            if (currentLife != null && currentLife.isAlive()) {
                currentLife.action();
            } else {
                currentLife.setAlive(false);
            }
        }
    }

    public ArrayList<Life> firstTurn() {
        resetSeed();
        ArrayList<Life> lives = getAliveLife();
//        Cell[][] newGrid = new Cell[0][];
//        copyWorld(newGrid);

        for (int i = 0; i < lives.size(); i++) {
            Life currentLife = lives.get(i);
            if(currentLife.getColor() == Color.GREEN){
                takeAction(currentLife);
            }
        }

//        for (int i = 0; i < lives.size(); i++) {
//            Life currentLife = lives.get(i);
//            if(currentLife.getColor() == Color.YELLOW){
//                takeAction(currentLife);
//            }
//        }

        return lives;
    }

    public void secondTurn(ArrayList<Life> lives) {
        for (int i = 0; i < lives.size(); i++) {
            Life currentLife = lives.get(i);
            if(currentLife.getColor() == Color.YELLOW){
                takeAction(currentLife);
            }
        }

    }

    private void takeAction(Life life) {
        if (life != null && life.isAlive()) {
            life.action();
        } else {
            life.setAlive(false);
        }

    }

    private void copyWorld(Cell[][] newGrid){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                newGrid[i][j] = new Cell(i, j);
                newGrid[i][j].setPresence(this.grid[i][j].getPresence());
            }
        }
    }

    private ArrayList<Life> getAliveLife() {
        ArrayList<Life> lives = new ArrayList<>();
        Life currentLife = null;

        for (int i = 0; i < this.grid.length; i++) {
            for (int j = 0; j < this.grid[i].length; j++) {
                currentLife = grid[i][j].getPresence();
                if (currentLife != null) {
                    lives.add(currentLife);
                }
            }
        }

        return lives;
    }

    private void resetSeed(){
        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                if(this.grid[i][j].getPresence()!= null && this.grid[i][j].getPresence().getColor() == Color.GREEN){
                    ((Plant)this.grid[i][j].getPresence()).setSeed(false);
                }
            }
        }
    }
}
