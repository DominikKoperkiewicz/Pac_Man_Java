
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;

public class Game {
	
	private Display display;
	private Rectangle rectangle;
	private Player player;
	private int points = 0;
	private int lives = 0;
	private Ghost blinky;
	private Ghost pinky;
	private Ghost inky;
	private Ghost clyde;
	
	public Game(int width, int heigth)
	{
		display = new Display(width, heigth);
		rectangle = new Rectangle(0, 0, 50, 50);
		player = new Player();
		blinky = new Blinky(20*13, 20*12);
		pinky = new Pinky(20*14, 20*12);
		inky = new Inky(20*13, 20*13);
		clyde = new Clyde(20*14, 20*13);
	}
	
	public void update()
	{
		if(display.getReset()) {
			display.setReset(false);
			points = 0;
			this.resetPositions();
			lives = 2;
			display.resetMap();
		}
		
		player.update(this);
		blinky.update(this);
		pinky.update(this);
		inky.update(this);
		clyde.update(this);
		

		Ghost[] ghosts = {blinky, pinky, inky, clyde};
		for(Ghost ghost : ghosts) {
			if((player.getX()-ghost.getX())*(player.getX()-ghost.getX())+(player.getY()-ghost.getY())*(player.getY()-ghost.getY()) < 20) {
				resetPositions();
				lives--;
			}
		}
	}
	
	private void resetPositions() {
		blinky.setPos(20*14, 20*12);
		pinky.setPos(20*13, 20*13);
		inky.setPos(20*13, 20*12);
		clyde.setPos(20*14, 20*13);
		player.setPos(20*12, 20*20);
	}
	
	public void render()
	{
		display.render(this);
	}
	
	public Rectangle getRectangle()
	{
		return rectangle;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public void eat(int x, int y) {
		if(display.getMap()[x][y] == 2)
		{
			points += 10;
			display.setMap(x, y, (short)0);
		}
		if(points % 2400 == 0) {
			resetPositions();
			display.resetMap();
		}
	}
	
	public int getPoints() { return points; }

	public Ghost getBlinky() { return blinky; }
	public Ghost getPinky() { return pinky; }
	public Ghost getInky() { return inky; }
	public Ghost getClyde() { return clyde; }
	public int getLives() { return lives; }
	
	public short[][] getMap() { return display.getMap(); }
}
