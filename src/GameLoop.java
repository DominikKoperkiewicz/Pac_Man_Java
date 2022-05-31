
public class GameLoop implements Runnable 
	{
	private Game game;
	
	private boolean running;
	private final double updateRate = 1.0d/60.0d;
	private boolean lost = true;
	
	private long nextStatTime;
	private int fps, ups;
	
	public GameLoop(Game game)
	{
		this.game = game;
	}
	
	@Override
	public void run() {
		running = true;
		while(true)
		{
			double accumulator = 0;
			long currentTime, lastUpdate = System.currentTimeMillis();
			nextStatTime = System.currentTimeMillis() + 1000;
			while(running)
			{
				currentTime = System.currentTimeMillis();
				double lastRenderTimeInSeconds = (currentTime - lastUpdate) / 1000d;
				accumulator += lastRenderTimeInSeconds;
				lastUpdate = currentTime;
					
				if(accumulator >= updateRate)
				{
					while (accumulator > updateRate)
					{
						running = update();
						accumulator -= updateRate;
					}
						
				render();
				}
				printStats();
			}
			if (lost)
			{
				renderGameOver();
				lost = false;
			}
				
			if (getReset()) 
			{
				running = true;
				lost = true;
			}
		}
	}

	private void printStats() 
	{	
		if (System.currentTimeMillis() > nextStatTime) 
		{
			System.out.println(String.format("FPS: %d, UPS: %d", fps, ups));
			fps = 0;
			ups = 0;
			nextStatTime = System.currentTimeMillis() + 1000;
		}
		
	}
	
	
	private Boolean update() 
	{
		Boolean result = game.update();
		ups++;
		return result;
	}
	
	private void renderGameOver()
	{
		game.renderGameOver();
	}

	private void render() 
	{
		game.render();
		fps++;
	}

	private Boolean getReset()
	{
		return game.getReset();
	}

}