import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import javax.swing.JOptionPane;

public class Display extends JFrame implements KeyListener  {

	private static final long serialVersionUID = 1L;
	private Canvas canvas;
	private int X_default = 120, Y_default=15;
	private boolean reset = true;
	public static int wanted_dir = 2; 
	private Highscores highscores = new Highscores();
	
	public boolean tmp = true;

	private short[][] map = 
			// 0 - puste pola,
			// 1 - sciana,
			// 2 - kropki
			{{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1},
			{1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1},
			{1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1},
			{1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1},
			{1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
			{1,2,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,2,1},
			{1,2,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,2,1},
			{1,2,2,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,2,2,1},
			{1,1,1,1,1,1,2,1,1,1,1,1,0,1,1,0,1,1,1,1,1,2,1,1,1,1,1,1},
			{0,0,0,0,0,1,2,1,1,0,0,0,0,0,0,0,0,0,0,1,1,2,1,0,0,0,0,0},
			{0,0,0,0,0,1,2,1,1,0,1,1,0,0,0,0,1,1,0,1,1,2,1,0,0,0,0,0},
			{1,1,1,1,1,1,2,1,1,0,1,0,0,0,0,0,0,1,0,1,1,2,1,1,1,1,1,1},
			{0,0,0,0,0,0,2,0,0,0,1,0,0,0,0,0,0,1,0,0,0,2,0,0,0,0,0,0},
			{1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1},
			{0,0,0,0,0,1,2,1,1,0,0,0,0,0,0,0,0,0,0,1,1,2,1,0,0,0,0,0},
			{1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1},
			{1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1},
			{1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1},
			{1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1},
			{1,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,1},
			{1,1,1,2,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,2,1,1,1},
			{1,1,1,2,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,2,1,1,1},
			{1,2,2,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,2,2,1},
			{1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1},
			{1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1},
			{1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
	
	public Display(int width, int heigth)
	{
		setTitle("Pac-Man");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, heigth));
		canvas.setFocusable(false);
		this.addKeyListener(this);
		add(canvas);
		pack();
		
		canvas.createBufferStrategy(3);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void renderGameOver(Game game)
	{
		
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
			
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, 60));
		graphics.setColor(Color.red);
		graphics.drawString("GAME OVER", 210, 80);
		
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, 40));
		graphics.setColor(Color.white);
		graphics.drawString("HIGH SCORES ", 260, 140);
			
			
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		graphics.drawString("Press R to reset ", 320, 520);
		
		String username = JOptionPane.showInputDialog("Enter your name");
		
		
		highscores.readHighscores();
		highscores.add(username, game.getPoints());
		highscores.sortHighscores();
		highscores.saveHighscores();
		
		graphics.setFont(new Font("TimesRoman", Font.PLAIN, 30));
		int h = 190;
			
		for (int i = 0; i < highscores.highscores.size(); i++)
		{
			graphics.drawString(highscores.highscores.get(i).username + "   " + highscores.highscores.get(i).score, 320, h);
			h += 30;
		}
			
		graphics.dispose();
		bufferStrategy.show();
			
		return;
		
	}
	
	public void render(Game game)
	{		
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		
		renderMap(game);
		renderPlayer(game);
		renderGhosts(game);
		graphics.setColor(Color.yellow);
		for(int i = 0; i < game.getLives(); i++) {
			graphics.fillRect(85, 550-i*30, 20, 20);
		}
		graphics.setColor(Color.white);
		graphics.drawString("SCORE: " + game.getPoints(), 10, 20);
		
		graphics.dispose();
		bufferStrategy.show();
	}
	
	public void renderMap(Game game)
	{
		int object_size=20;
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		
		graphics.setColor(Color.black);
		graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
		
		int x = X_default, y = Y_default;
		
		for (short[] line: map)
		{
			for (short c: line)
			{
				switch (c) {
				case 1:
					graphics.setColor(Color.blue);
					graphics.fillRect(x, y, 20, 20);
					break;
					
				case 2:
					graphics.setColor(Color.white);
					graphics.fillRect(x+8, y+8, 4, 4);
				default:
					break;
				}
				x += object_size;
			}
			x = X_default;
			y += object_size;
		}		
	}
	
	public void renderPlayer(Game game) {
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		
		int size = 20;
		graphics.setColor(Color.yellow);
		graphics.fillRect(game.getPlayer().getX()+120, game.getPlayer().getY()+15, size, size);

	}
	
	public void renderGhosts(Game game) {
		Ghost[] ghosts = {game.getBlinky(), game.getPinky(), game.getInky(), game.getClyde()};
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		
		int size = 20;
		for(Ghost ghost : ghosts) {
			graphics.setColor(ghost.getColor());
			graphics.fillRect(ghost.getX()+120, ghost.getY()+15, size, size);
		}
	}
	
	public short[][] getMap() { return map; }

	public void setMap(int x, int y, short val) { map[x][y] = val; }
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case 37:
				Display.wanted_dir = 2;
				break;
			case 38:
				Display.wanted_dir = 1;
				break;
			case 39:
				Display.wanted_dir = 0;
				break;
			case 40:
				Display.wanted_dir = 3;
				break;
			case 82:
					this.setReset(true);
				break;
		};
		//System.out.println(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
	public void setReset(boolean bool) {
		reset = bool;
	}
	
	public boolean getReset() { return reset; }
	
	public void resetMap() {
		short[][] tmp = 	
				// 0 - puste pola,
				// 1 - sciana,
				// 2 - kropki
				{{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1},
				{1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1},
				{1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1},
				{1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1},
				{1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
				{1,2,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,2,1},
				{1,2,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,2,1},
				{1,2,2,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,2,2,1},
				{1,1,1,1,1,1,2,1,1,1,1,1,0,1,1,0,1,1,1,1,1,2,1,1,1,1,1,1},
				{0,0,0,0,0,1,2,1,1,0,0,0,0,0,0,0,0,0,0,1,1,2,1,0,0,0,0,0},
				{0,0,0,0,0,1,2,1,1,0,1,1,0,0,0,0,1,1,0,1,1,2,1,0,0,0,0,0},
				{1,1,1,1,1,1,2,1,1,0,1,0,0,0,0,0,0,1,0,1,1,2,1,1,1,1,1,1},
				{0,0,0,0,0,0,2,0,0,0,1,0,0,0,0,0,0,1,0,0,0,2,0,0,0,0,0,0},
				{1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1},
				{0,0,0,0,0,1,2,1,1,0,0,0,0,0,0,0,0,0,0,1,1,2,1,0,0,0,0,0},
				{1,1,1,1,1,1,2,1,1,0,1,1,1,1,1,1,1,1,0,1,1,2,1,1,1,1,1,1},
				{1,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,1},
				{1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1},
				{1,2,1,1,1,1,2,1,1,1,1,1,2,1,1,2,1,1,1,1,1,2,1,1,1,1,2,1},
				{1,2,2,2,1,1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1,1,2,2,2,1},
				{1,1,1,2,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,2,1,1,1},
				{1,1,1,2,1,1,2,1,1,2,1,1,1,1,1,1,1,1,2,1,1,2,1,1,2,1,1,1},
				{1,2,2,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,1,1,2,2,2,2,2,2,1},
				{1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1},
				{1,2,1,1,1,1,1,1,1,1,1,1,2,1,1,2,1,1,1,1,1,1,1,1,1,1,2,1},
				{1,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}};
		map = tmp;
	}
}