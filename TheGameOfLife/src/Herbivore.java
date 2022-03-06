import java.awt.*;
import java.util.ArrayList;

public class Herbivore extends Life{
    private final int maxDays = 5;

    public int daysWithoutFood = 0;

    public int herbIndex;

    public Herbivore(Cell cell){
        super(cell, Color.yellow);
    }

    public void setHerbIndex(int x){
        this.herbIndex = x;
    }

    @Override
    public ArrayList<Neighbour> possiblePaths() {
        ArrayList<Neighbour> newPath = super.currentCell.computeNeighbour();

//        ArrayList<Neighbour> neighbouringCells = super.currentCell.computeNeighbour();

//        for(int i = 0; i < neighbouringCells.size(); i++){
//            int x = neighbouringCells.get(i).getNeighbourX();
//            int y = neighbouringCells.get(i).getNeighbourY();
//
//            Cell nextCell = World.grid[x][y];
//            if(nextCell.getPresence() == null){
//                newPath.add(neighbouringCells.get(i));
//            }else if(nextCell.getPresence() != null && nextCell.getPresence().getColor()!= Color.yellow){
//                newPath.add(neighbouringCells.get(i));
//            }
//        }

        return newPath;
    }

    @Override
    public void action() {
        Cell nextCell = chooseCell(possiblePaths());
        System.out.println("X: " + nextCell.x + "Y: " + nextCell.y);

        if(nextCell != null){
            if(nextCell.getPresence() == null){
                daysWithoutFood++;
                System.out.println("daysWithoutFood in action:" + daysWithoutFood + ", herbIndex:" + herbIndex);
                if(daysWithoutFood == maxDays){
                    kill();
                    System.out.println("NULL & kill called");
                } else{
                    move(nextCell);
                    update();
                }
            }else if(nextCell.getPresence() != null && nextCell.getPresence().getColor() == Color.GREEN
            && !((Plant)nextCell.getPresence()).isSeed){
                daysWithoutFood = 0;
                eat(nextCell);
                System.out.println("EAT, index:" + herbIndex);
                update();
            }else if(nextCell.getPresence() != null && nextCell.getPresence().getColor() == Color.GREEN
            && ((Plant)nextCell.getPresence()).isSeed){
                daysWithoutFood++;
                if(daysWithoutFood == maxDays){
                    kill();
                    System.out.println("isSeed, kill called, index:" + herbIndex);
                }
            }else if(nextCell.getPresence() != null && nextCell.getPresence().getColor() == Color.YELLOW){
                daysWithoutFood++;
                if(daysWithoutFood == maxDays){
                    kill();
                    System.out.println("Stay, kill called, index: " + herbIndex);
                }
            }
        }
    }

    private void kill(){
        this.alive = false;
        this.currentCell.setPresence(null);
        this.color = Color.WHITE;
    }

    private void move(Cell nextCell){
//        if(nextCell.x <= World.rows - 1 && nextCell.y <= World.cols - 1){
            Herbivore herbivore = new Herbivore(nextCell);
            herbivore.setDaysWithoutFood(daysWithoutFood);
            herbivore.setHerbIndex(herbIndex);
            nextCell.setPresence(herbivore);
            nextCell.getPresence().setColor(Color.YELLOW);
            nextCell.getPresence().setAlive(true);
            if(daysWithoutFood >= 5){
                System.out.println("DAYS WITHOUT FOOD OVER 5");
                System.out.println("daysWithoutFood: " + ((Herbivore)nextCell.getPresence()).daysWithoutFood);
            }
//        }
    }

    private void setDaysWithoutFood(int daysWithoutFood){
        this.daysWithoutFood = daysWithoutFood;
    }


    private void eat(Cell nextCell){
        if(nextCell.getPresence() != null && nextCell.getPresence().isAlive()){
            nextCell.getPresence().setAlive(false);
            nextCell.setPresence(null);
        }

        Herbivore life = new Herbivore(nextCell);
        life.setHerbIndex(herbIndex);
        life.setDaysWithoutFood(daysWithoutFood);
        nextCell.setPresence(life);
        nextCell.getPresence().setColor(Color.YELLOW);
        nextCell.getPresence().setAlive(true);

    }

}
