
public class Player {
	int x;
	int y; 
	int direction; // 0 - right; 1 - up; 2 - left; 3 - down
	int speed = 1;
	
	public Player() {
		x = 20 * 12;
		y = 20 * 20;
		direction = 2;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	
	public void update(Game game) {
		switch(Display.wanted_dir) {
		case 0: 	
			if(game.getMap()[(y)/20][(x+19+speed)/20] != 1 && y % 20 == 0)
				direction = 0;
			break;
		case 1: 	
			if(game.getMap()[(y-speed)/20][(x)/20] != 1 && x % 20 == 0)
				direction = 1;
			break;
		case 2: 
			//System.out.println((x-150-speed)/20);			
			if(game.getMap()[(y)/20][(x-speed)/20] != 1 && y % 20 == 0)
				direction = 2;
			break;
		case 3: 	
			if(game.getMap()[(y+19+speed)/20][(x)/20] != 1 && x % 20 == 0)
				direction = 3;
			break;
		}
		//direction = Display.wanted_dir;
		
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
	/*
	public void render(Graphics graphics) {
		graphics.setColor(Color.yellow);
		graphics.fillRect(x-10, y-10, x+10, y+10);
	}*/
	
}