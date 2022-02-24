public class Cell {
    private static final int TOTAL = 100;
    private static final int HERBIVORE_VALUE = 85;
    private static final int PLANT_VALUE = 65;
    public int x;
    public int y;
    public Life presence;
    public Neighbour neighbour;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.presence = generateLife();
    }

    private Life generateLife(){
        int value = RandomGenerator.nextNumber(TOTAL);

        if (value >= HERBIVORE_VALUE)
            return new Herbivore(this);
        else if (value >= PLANT_VALUE)
            return new Plant(this);
        else
            return null;
    }


    public void setPresence(Life life){
        this.presence = life;
    }

    public Life getPresence(){
        return this.presence;
    }

    public void setNeighbour(Neighbour neighbour) {
        this.neighbour = neighbour;
    }

    public Neighbour getNeighbour(){ return this.neighbour; }

}
