import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Display extends JFrame implements KeyListener  {

	private static final long serialVersionUID = 1L;
	private Canvas canvas;
	private int X_default = 120, Y_default=15;
	public static int wanted_dir; 

	private final short[][] map = 
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
			{0,0,0,0,0,1,2,1,1,1,1,1,0,1,1,0,1,1,1,1,1,2,1,0,0,0,0,0},
			{0,0,0,0,0,1,2,1,1,0,0,0,0,0,0,0,0,0,0,1,1,2,1,0,0,0,0,0},
			{1,1,1,1,1,1,2,1,1,0,1,1,1,0,0,1,1,1,0,1,1,2,1,1,1,1,1,1},
			{0,0,0,0,0,0,2,0,0,0,1,0,0,0,0,0,0,1,0,0,0,2,1,0,0,0,0,0},
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

	
	public void render(Game game)
	{
		BufferStrategy bufferStrategy = canvas.getBufferStrategy();
		Graphics graphics = bufferStrategy.getDrawGraphics();
		
		renderMap(game);
		renderPlayer(game);
		/*
		Rectangle rectangle = game.getRectangle();
		graphics.setColor(Color.red);
		graphics.fillRect(
				(int) rectangle.getX(), 
				(int) rectangle.getY(), 
				(int) rectangle.getWidth(), 
				(int) rectangle.getHeight()
				);
		*/
		
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
	
	public short[][] getMap() { return map; }


	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case 37:
				System.out.println("LEFT");
				Display.wanted_dir = 2;
				break;
			case 38:
				System.out.println("UP");
				Display.wanted_dir = 1;
				break;
			case 39:
				System.out.println("RIGHT");
				Display.wanted_dir = 0;
				break;
			case 40:
				System.out.println("DOWN");
				Display.wanted_dir = 3;
				break;
		};
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}