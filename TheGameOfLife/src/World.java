import java.util.Set;
import java.util.HashSet;

public class World{
    private static final int TOTAL = 100;
    private static final int HERBIVORE_VALUE = 85;
    private static final int PLANT_VALUE = 65;
    private int rows;
    private int cols;
    public static Cell[][] grid;

    public World(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.grid = new Cell[this.rows][this.cols];
    }

    public int getRows(){
        return this.rows;
    }

    public int getCols(){
        return this.cols;
    }

    public Cell getGrid(int rows, int cols){
        return this.grid[rows][cols];
    }

    public void init(){
        RandomGenerator.reset();

        for(int i = 0; i < this.rows; i++){
            for(int j = 0; j < this.cols; j++){
                this.grid[i][j] = new Cell(i, j);
                generateLife(this.grid[i][j]);
            }
        }
    }

    private void generateLife(Cell cell){
        int value = RandomGenerator.nextNumber(TOTAL);

        if(value >= HERBIVORE_VALUE){
            cell.setPresence(new Herbivore(cell));
        }else if (value >= PLANT_VALUE){
            cell.setPresence(new Plant(cell));
        }else {
            cell.setPresence(null);
        }
    }

    public static Set<Cell> computeNeighbour(Cell cell){
        Set<Cell> neighbourCells = new HashSet<>();
        int x = cell.getX();
        int y = cell.getY();

        for(int i = x - 1; i < x + 2; i++){
            for(int j = y - 1; j < y + 2; j++){
                if(i >= 0 && i <= 24 && j >= 0 && j <=24){
                    neighbourCells.add(grid[i][j]);
                }
            }
        }

        return neighbourCells;
    }


}
