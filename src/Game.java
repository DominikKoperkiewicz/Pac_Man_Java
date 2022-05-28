
import java.awt.Rectangle;

public class Game {
	
	private Display display;
	private Rectangle rectangle;
	private Player player;
	
	
	public Game(int width, int heigth)
	{
		display = new Display(width, heigth);
		rectangle = new Rectangle(0, 0, 50, 50);
		player = new Player();
	}
	
	public void update()
	{
		//rectangle.setLocation((int) rectangle.getX()+1, (int) rectangle.getY()+1);
		player.update(this);
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

	public short[][] getMap() { return display.getMap(); }
}
