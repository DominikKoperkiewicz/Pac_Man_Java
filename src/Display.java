import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Display extends JFrame {

	private static final long serialVersionUID = 1L;
	private Canvas canvas;
	private int X_default = 120, Y_default=40;

	private final short[][] map = 
			// 0 - puste pola,
			// 1 - lewy dolny rog
			// 2 - sciana dol
			// 3 - prawy dolny rog
			// 4 - sciana lewo
			// 6 - sciana prawo
			// 7 - lewy gorny rog
			// 8 - sciana gora
			// 9 - prawy gorny rog
		
			// 19 - prawy gorny rog jest pusty
			// 37 - lewy gorny rog jest pusty
			// 73 - prawy dolny rog jest pusty
			// 91 - lewy dolny rog jest pusty
			
			// 5 - kropki
				{{73,2,2,2,2,2,2,2,2,2,2,2,2,91,73,2,2,2,2,2,2,2,2,2,2,2,2,91},	
				{6,5,5,5,5,5,5,5,5,5,5,5,5,4,6,5,5,5,5,5,5,5,5,5,5,5,5,4},
				{6,5,7,8,8,9,5,7,8,8,8,9,5,4,6,5,7,8,8,8,9,5,7,8,8,9,5,4},
				{6,5,4,0,0,6,5,4,0,0,0,6,5,4,6,5,4,0,0,0,6,5,4,0,0,6,5,4},
				{6,5,1,2,2,3,5,1,2,2,2,3,5,1,3,5,1,2,2,2,3,5,1,2,2,3,5,4},
				{6,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4},
				{6,5,7,8,8,9,5,7,9,5,7,8,8,8,8,8,8,9,5,7,9,5,7,8,8,9,5,4},
				{6,5,1,2,2,3,5,4,6,5,1,2,2,91,73,2,2,3,5,4,6,5,1,2,2,3,5,4},
				{6,5,5,5,5,5,5,4,6,5,5,5,5,4,6,5,5,5,5,4,6,5,5,5,5,5,5,4},
				{19,8,8,8,8,9,5,4,6,8,8,9,0,4,6,0,7,8,8,4,6,5,7,8,8,8,8,37},
				{6,0,0,0,0,6,5,4,6,2,2,3,0,1,3,0,1,2,2,4,6,5,4,0,0,0,0,4},
				{6,0,0,0,0,6,5,4,6,0,0,0,0,0,0,0,0,0,0,4,6,5,4,0,0,0,0,4},
				{6,0,0,0,0,6,5,4,6,0,7,8,8,0,0,8,8,9,0,4,6,5,4,0,0,0,0,4},
				{2,2,2,2,2,3,5,1,3,0,4,0,0,0,0,0,0,6,0,1,3,5,1,2,2,2,2,2},
				{0,0,0,0,0,0,5,0,0,0,4,0,0,0,0,0,0,6,0,0,0,5,0,0,0,0,0,0},
				{8,8,8,8,8,9,5,7,9,0,4,0,0,0,0,0,0,6,0,7,9,5,7,8,8,8,8,8},
				{6,0,0,0,0,6,5,4,6,0,1,2,2,2,2,2,2,3,0,4,6,5,4,0,0,0,0,4},
				{6,0,0,0,0,6,5,4,6,0,0,0,0,0,0,0,0,0,0,4,6,5,4,0,0,0,0,4},
				{6,0,0,0,0,6,5,4,6,0,7,8,8,8,8,8,8,9,0,4,6,5,4,0,0,0,0,4},
				{73,2,2,2,2,3,5,1,3,0,1,2,2,91,73,2,2,3,0,1,3,5,1,2,2,2,2,91},
				{6,5,5,5,5,5,5,5,5,5,5,5,5,4,6,5,5,5,5,5,5,5,5,5,5,5,5,4},
				{6,5,7,8,8,9,5,7,8,8,8,9,5,4,6,5,7,8,8,8,9,5,7,8,8,9,5,4},
				{6,5,1,2,91,6,5,1,2,2,2,3,5,1,3,5,1,2,2,2,3,5,4,73,2,3,5,4},
				{6,5,5,5,4,6,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4,6,5,5,5,4},
				{19,8,9,5,4,6,5,4,6,5,7,8,8,8,8,8,8,9,5,7,9,5,4,6,5,7,8,37},
				{73,2,3,5,1,3,5,4,6,5,1,2,2,91,73,2,2,3,5,4,6,5,1,3,5,1,2,91},
				{6,5,5,5,5,5,5,4,6,5,5,5,5,4,6,5,5,5,5,4,6,5,5,5,5,5,5,4},
				{6,5,7,8,8,8,8,8,8,8,8,9,5,4,6,5,7,8,8,8,8,8,8,8,8,9,5,4},
				{6,5,1,2,2,2,2,2,2,2,2,3,5,4,6,5,1,2,2,2,2,2,2,2,2,3,5,4},
				{6,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,4},
				{19,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,8,37}};

	
	public Display(int width, int heigth)
	{
		setTitle("Pac-Man");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		canvas = new Canvas();
		canvas.setPreferredSize(new Dimension(width, heigth));
		canvas.setFocusable(false);
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
		
		Rectangle rectangle = game.getRectangle();
		graphics.setColor(Color.red);
		graphics.fillRect(
				(int) rectangle.getX(), 
				(int) rectangle.getY(), 
				(int) rectangle.getWidth(), 
				(int) rectangle.getHeight()
				);
		
		
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
				// 0 - puste pola,
				
				graphics.setColor(Color.blue);
				switch (c) {
				case 1: 		// 1 - sciana,
					graphics.fillRect(x+10, y, 10, 10);
					break;
					
				case 2:			// 2 - sciana dol
					graphics.fillRect(x, y, 20, 10);
					break;
					
				case 3: 		// 1 - sciana,
					graphics.fillRect(x, y, 10, 10);
					break;
					
				case 4: 		// 4 - sciana lewo
					graphics.fillRect(x+10, y, 10, 20);
					break;
					
					
				case 6:			// 6 - sciana prawo
					graphics.fillRect(x, y, 10, 20);
					break;
					
				case 7:			// 7 - lewy gorny rog
					graphics.fillRect(x+10, y+10, 10, 10);
					break;
					
				case 8:	 		// 8 - sciana gora
					graphics.fillRect(x, y+10, 20, 10);
					break;
					
				case 9:			// 9 - prawy gorny rog
					graphics.fillRect(x, y+10, 10, 10);
					break;
					
				case 19:			// 19 - prawy gorny rog jest pusty
					graphics.fillRect(x, y, 10, 20);
					graphics.fillRect(x+10, y+10, 10, 10);
					break;
					
				case 37:			// 37 - lewy gorny rog jest pusty
					graphics.fillRect(x, y+10, 10, 10);
					graphics.fillRect(x+10, y, 10, 20);
					break;
					
				case 73:			// 73 - prawy dolny rog jest pusty
					graphics.fillRect(x+10, y, 10, 10);
					graphics.fillRect(x, y, 10, 20);
					break;
					
				case 91:			// 91 - lewy dolny rog jest pusty
					graphics.fillRect(x, y, 10, 10);
					graphics.fillRect(x+10, y, 10, 20);
					break;
					
				case 5:			// 5 - kropki
					graphics.setColor(Color.yellow);
					graphics.fillRect(x+8, y+8, 4, 4);
				default:
					break;
				}
				x += object_size;
			}
			x = X_default;
			y += object_size;
		}
		graphics.dispose();
		bufferStrategy.show();
		
	}
}