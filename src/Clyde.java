import java.awt.Color;

public class Clyde extends Ghost {
	
	public Clyde(int X, int Y, Game ref) {
		this.x = X;
		this.y = Y;
		this.color = Color.orange;
		this.game_ref = ref;
	}
	
	@Override
	public void AI(Game game) {
		int targetX = game.getPlayer().getX();
		int targetY = game.getPlayer().getY();
		
		if((x-game.getPlayer().getX())*(x-game.getPlayer().getX()) + (y-game.getPlayer().getY())*(y-game.getPlayer().getY()) < 8*20*8*20) {
			targetX = 20;
			targetY = 20*28;
		}
		
		float small = 9999999;
		float tmp;
		int dir = 0;
		
		if ((this.x > 10*20 && this.x < 17*20) && (this.y > 10*20 && this.y < 14*20))
		{
			dir = 1;
		}else
		{
			for(int i = 0; i < 4; i++) {
				if(i != (direction+2) % 4) 
				{
					switch(i) {
						case 0:
							if(game.getMap()[(y)/20][(x)/20 + 1] == 1) { break; }
							tmp = (x-targetX+20)*(x-targetX+20) + (y-targetY)*(y-targetY);
							if( tmp < small ) { small = tmp; dir = i; }
							break;
						case 1:
							if(game.getMap()[(y)/20 - 1][(x)/20] == 1) { break; }
							tmp = (x-targetX)*(x-targetX) + (y-targetY-20)*(y-targetY-20);
							if( tmp < small ) { small = tmp; dir = i; }
							break;
						case 2:
							if(game.getMap()[(y)/20][(x)/20 - 1] == 1) { break; }
							tmp = (x-targetX-20)*(x-targetX-20) + (y-targetY)*(y-targetY);
							if( tmp < small ) { small = tmp; dir = i; }
							break;
						case 3:
							if(game.getMap()[(y)/20 + 1][(x)/20] == 1) { break; }
							tmp = (x-targetX)*(x-targetX) + (y-targetY+20)*(y-targetY+20);
							if( tmp < small ) { small = tmp; dir = i; }
							break;
					}
				}
			}
		}
		
		direction = dir;
	}
}