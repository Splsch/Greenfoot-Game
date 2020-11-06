import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;

/**
 * A 10x10 grid of tiles.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TileWorld
{
    public ArrayList<Tile> chunk = new ArrayList<Tile>();
    public ArrayList<Conveyor> conveyorArray = new ArrayList<Conveyor>();
    public int xCoord;
    public int yCoord;
    public int id;
    public static String[] weightString = {"grass", "metamorphic", "sedimentary", "igneous"};
    public static int[] weights = {1700, 40, 20, 90};
    public TileWorld(int x, int y) {
        xCoord = x;
        yCoord = y;
    }

    public void act() 
    {
        
    }
    public void setWeights(int[] arr) {
        weights = arr;
    }
    public void genWorld() {
        int index = 0;
        int roll = 0;
        for(int i = 25; i <= 500; i += 50) {
            for(int j = 25; j <= 500; j += 50) {
                roll = getRoll();
                if(roll == 0) {
                    chunk.add(new Tile("grass" + Greenfoot.getRandomNumber(5) + ".png", "grass"));
                } else {
                    chunk.add(new Tile(weightString[roll] + ".png", weightString[roll]));
                }
                chunk.get(index).realX = i;
                chunk.get(index).realY = j;
                chunk.get(index).x = (i-25)/50;
                chunk.get(index).y = (j-25)/50;
                chunk.get(index).index = i;
                index++;
            }
        }
    }
    public void changeTile(int x, int y, Tile newTile) {
        chunk.set(y + x * 10, newTile);
    }
    public int getRoll() {
        int total = 0;
        for(int i : weights) {
            total += i;
        }
        int roll = Greenfoot.getRandomNumber(total);
        if(roll < weights[0]) {
            return 0;
        } else if(roll < weights[0] + weights[1]) {
            return 1;
        } else if(roll < weights[0] + weights[1] + weights[2]){
            return 2;
        } else {
            return 3;
        }
    }
}
