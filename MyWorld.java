import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.ArrayList;
import java.util.List;
/**
 * World object.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MyWorld extends World
{
    public static ArrayList<TileWorld> chunkWorld = new ArrayList<TileWorld>(); //all of the world tiles in an array
    public static Player player = new Player(); // static object of the player
    public static Manbearpig enemy = new Manbearpig();
    public static int loadedChunk = 0; // the chunk currently loaded in the world
    public static Score scoreBoard = new Score("Rocks: ", 0);
    public static String defaultConveyorDirection = "East";
    public static Label mousePos = new Label("", 16);
    public static boolean showMousePos = true;
    public static int lives = 3;
    public static Score livesCounter = new Score("Lives: ", lives);
    public static int desiredType = Greenfoot.getRandomNumber(3);
    public static Label rockType = new Label("", 16);
    public static int ticks = 0;
    public Label manbearpigLocation = new Label("Manbearpig chunk: \nManbearpig Location: ", 16);
    public Label playerLocation = new Label("Player: ", 16);
    public QuestionMark question = new QuestionMark();
    public static PlayerHelp help = new PlayerHelp();

    public MyWorld(String playerImage)
    {    
        super(500, 550, 1);
        setPaintOrder(PlayerHelp.class, Label.class, Score.class, Manbearpig.class, FirstAidKit.class, Player.class, Tile.class);
        int i = 0; //index of first chunk insert
        TileWorld chunk1 = new TileWorld(0, 0); // the first chunk is at 0, 0
        chunk1.genWorld(); // generates the chunk's tiles
        chunk1.id = 0;
        chunkWorld.add(chunk1); // adds the chunk to the world
        genChunk(0, 0); // generates the chunk that is at the location of 0, 0
        player.realX = 25;
        player.realY = 25;
        player.x = 0;
        player.y = 0;
        player.tileWorldX = 0;
        player.tileWorldY = 0;
        rockType.setFillColor(Color.BLACK);
        rockType.setLineColor(null);
        manbearpigLocation.setFillColor(Color.BLACK);
        manbearpigLocation.setLineColor(null);
        playerLocation.setFillColor(Color.BLACK);
        playerLocation.setLineColor(null);
        addObject(rockType, 250, 44);
        player.setImage(new GreenfootImage(playerImage));
        addObject(player, 25, 25); // add player to world
        addObject(enemy, 475, 475);
        addObject(scoreBoard, 250, 14);
        addObject(mousePos, 250, 480);
        addObject(livesCounter, 250 , 30);
        addObject(question, 475, 525);
        addObject(help, 200, 200);
        manbearpigLocation.setValue("Manbearpig chunk: " + "(" + enemy.tileWorldX  + ", " + enemy.tileWorldY + ")" + "\nManbearpig Location: " + "(" + enemy.x  + ", " + (9 - enemy.y) + ")");
        playerLocation.setValue("Player chunk: " + "(" + player.tileWorldX  + ", " + player.tileWorldY + ")" + "\nPlayer Location: " + "(" + player.x  + ", " + (9 - enemy.y) + ")");
        addObject(manbearpigLocation, 80, 525);
        addObject(playerLocation, 300, 525);
        loadedChunk = 0;
        reset();
        prepare();
    }

    public void act() {
        livesCounter.update();
        scoreBoard.update();
        ticks++;
        if(Greenfoot.getMouseInfo() != null && showMousePos) {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            mousePos.setValue("(" + mouse.getX() + ", " + mouse.getY() + ")");
        }
        if(Greenfoot.getRandomNumber(25000) <= 1 && !player.stopped) {
            int x = Greenfoot.getRandomNumber(10);
            int y = Greenfoot.getRandomNumber(10);
            addObject(new FirstAidKit(x, y, player.tileWorldX, player.tileWorldY), ((x + 1) * 50) - 25, ((y + 1) * 50) - 25);
        }
        playerLocation.setValue("Player chunk: " + "(" + player.tileWorldX  + ", " + player.tileWorldY + ")" + "\nPlayer Location: " + "(" + player.x  + ", " + (9 - player.y) + ")");
        manbearpigLocation.setValue("Manbearpig chunk: " + "(" + enemy.tileWorldX  + ", " + enemy.tileWorldY + ")" + "\nManbearpig Location: " + "(" + enemy.x  + ", " + (9 - enemy.y) + ")");
    }
    // gets tile from arraylist
    public static int getTile(int x, int y) {
        return y + x * 10;
    }
    // draws an existing chunk to the screen if it exists
    // if the chunk doesn't exist, it creates a new chunk.
    public static void reset() {
        lives = 3;
        livesCounter.score = 3;
        scoreBoard.score = 0;
        desiredType = Greenfoot.getRandomNumber(3);
        if(desiredType == 0) {
            rockType.setValue("Collect metamorphic rocks.");
        } else if(desiredType == 1) {
            rockType.setValue("Collect sedimentary rocks.");
        } else {
            rockType.setValue("Collect igneous rocks.");
        }
    }

    public void genChunk(int x, int y) {
        this.unloadChunk();
        TileWorld currChunk;
        for(int i = 0; i < chunkWorld.size(); i++) {
            currChunk = chunkWorld.get(i);
            if(currChunk.xCoord == x && currChunk.yCoord == y) {
                for(Tile tile : chunkWorld.get(i).chunk) {
                    addObject(tile, tile.realX, tile.realY);
                }
                loadedChunk = i;
                return;
            }
        }
        currChunk = new TileWorld(x, y);
        currChunk.genWorld();
        chunkWorld.add(currChunk);
        currChunk.id = chunkWorld.size() - 1;
        for(Tile tile : currChunk.chunk) {
            addObject(tile, tile.realX, tile.realY);
        }
        loadedChunk = chunkWorld.size() - 1;
    }
    // generates chunk but does not load it in
    public void genItemChunk(int x, int y) {
        TileWorld currChunk = new TileWorld(x, y);
        currChunk.genWorld();
        chunkWorld.add(currChunk);
        currChunk.id = chunkWorld.size() - 1;
    }
    //unloads all of the current tiles.
    public void unloadChunk() {
        List<Tile> chunk = getObjects(Tile.class);
        removeObjects(chunk);
    }

    public void load(Actor obj, int xCoord, int yCoord) {
        addObject(obj, xCoord, yCoord);
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
    }
}
