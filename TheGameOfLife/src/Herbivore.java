import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Herbivore extends Life{
    private final int maxDays = 5;

    public int daysWithoutFood = 0;

    public Herbivore(Cell cell){
        super(cell, Color.yellow);
    }

    @Override
    public ArrayList<Neighbour> possiblePaths() {
        ArrayList<Neighbour> newPath = new ArrayList<>();

        ArrayList<Neighbour> neighbouringCells = World.computeNeighbour(super.currentCell);

        for(int i = 0; i < neighbouringCells.size(); i++){
            int x = neighbouringCells.get(i).getNeighbourX();
            int y = neighbouringCells.get(i).getNeighbourY();

            Cell nextCell = World.grid[x][y];
            if(nextCell.getPresence() == null){
                newPath.add(neighbouringCells.get(i));
            }else if(nextCell.getPresence() != null && nextCell.getPresence().getColor()!= Color.yellow){
                newPath.add(neighbouringCells.get(i));
            }
        }

        return newPath;
    }

    @Override
    public void action() {
        Cell nextCell = chooseCell(possiblePaths());

        if(nextCell.getPresence() == null){
            daysWithoutFood++;
            if(daysWithoutFood == maxDays){
                kill();
            } else{
                move(nextCell);
                update();
            }
        }else if(nextCell.getPresence() != null && nextCell.getPresence().getColor() == Color.GREEN){
            daysWithoutFood = 0;
            eat(nextCell);
            update();
        }
    }

    private void kill(){
        this.alive = false;
        this.currentCell.setPresence((Life)null);
        this.color = Color.WHITE;
    }

    private void move(Cell nextCell){
        Herbivore herbivore = new Herbivore(nextCell);
        herbivore.setDaysWithoutFood(daysWithoutFood);
        System.out.println("Days Without Food info sent: " + daysWithoutFood);
        nextCell.setPresence(herbivore);
        nextCell.getPresence().setColor(Color.YELLOW);
        nextCell.getPresence().setAlive(true);
    }

    private void setDaysWithoutFood(int daysWithoutFood){
        this.daysWithoutFood = daysWithoutFood;
    }

    private void eat(Cell nextCell){
        if(nextCell.getPresence() != null && nextCell.getPresence().alive == true){
            nextCell.getPresence().setAlive(false);
            nextCell.setPresence((Life)null);
        }

        Life life = new Herbivore(nextCell);
        nextCell.setPresence(life);
        nextCell.getPresence().setColor(Color.YELLOW);
        nextCell.getPresence().setAlive(true);
    }

}
