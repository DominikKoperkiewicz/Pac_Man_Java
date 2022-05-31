import java.awt.Color;
import java.lang.*;

public abstract class Ghost implements Runnable{
	int x;
	int y;
	int direction = 0;
	int speed = 1;
	Color color = Color.white;
	Game game_ref;
	
	@Override
	public void run()
	{
		this.update(game_ref);
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public Color getColor() { return color; }
	
	abstract public void AI(Game game);
	
	public void update(Game game) {
		if(x % 20 == 0 && y % 20 == 0) { AI(game); }

		if(x < 5) { x = 20*26; }
		if(x > 20*26) { x = 5; }
		
		switch(direction) {
		case 0: 	
			if(game.getMap()[(y)/20][(x+19+speed)/20] != 1)
				x += speed;
			break;
		case 1: 	
			if(game.getMap()[(y-speed)/20][(x)/20] != 1)
				y -= speed;
			break;
		case 2: 
			//System.out.println((x-150-speed)/20);			
			if(game.getMap()[(y)/20][(x-speed)/20] != 1)
				x -= speed;
			break;
		case 3: 	
			if(game.getMap()[(y+19+speed)/20][(x)/20] != 1)
				y += speed;
			break;
		}
		
	}
	
	public void setPos(int X, int Y) {
		this.x = X;
		this.y = Y;
	}
	
}