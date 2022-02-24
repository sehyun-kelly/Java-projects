public class World {
    public int rows;
    public int cols;
    public Cell[][] grid;

    public World(int rows, int cols){

    }

    public Cell[][] getGrid(){
        return this.grid;
    }

    public Neighbour computeNeighbour(Cell cell){
        Neighbour neighbour = new Neighbour();

        return neighbour;
    }

}
