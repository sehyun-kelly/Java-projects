import java.util.ArrayList;

public class World{
    private static final int TOTAL = 99;
    private static final int HERBIVORE_VALUE = 85;
    private static final int PLANT_VALUE = 65;
    public static int rows;
    public static int cols;
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
        }else if (value >= PLANT_VALUE && value < HERBIVORE_VALUE){
            cell.setPresence(new Plant(cell));
        }
    }

    public static ArrayList<Neighbour> computeNeighbour(Cell cell){
        ArrayList<Neighbour> neighbourCells = new ArrayList<>();
        int x = cell.returnX();
        int y = cell.returnY();

        for(int i = x - 1; i < x + 2; i++){
            for(int j = y - 1; j < y + 2; j++){

                if(i >= 0 && i <= (World.rows - 1) && j >= 0 && j <= (World.cols - 1)){
                    Neighbour neighbour = new Neighbour(i, j);
                    if(i != x || j != y){
                        neighbourCells.add(neighbour);
                    }
                }
            }
        }

        return neighbourCells;
    }

    public void turns(){
        ArrayList<Life> lives = getAliveLife();

        for(int i = 0; i < lives.size(); i++){
            Life currentLife = lives.get(i);
            if(currentLife != null && currentLife.isAlive()){
                currentLife.action();
            }else{
                currentLife.setAlive(false);
            }
        }
    }

    private ArrayList<Life> getAliveLife(){
        ArrayList<Life> lives = new ArrayList<>();
        Life currentLife = null;

        for(int i = 0; i < this.grid.length; i++){
            for(int j = 0; j < this.grid[i].length; j++){
                currentLife = grid[i][j].getPresence();
                if(currentLife != null){
                    lives.add(currentLife);
                }
            }
        }

        return lives;
    }


}
