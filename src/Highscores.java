import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


/**
 * ArrayList with highscores, can store only 10 entries
 * Methods: read, save, sort, add
 */
public class Highscores {

	public List<HighscoreEntry> highscores = new ArrayList<HighscoreEntry>();
	
	public void readHighscores()
	{
		
		try {
			File f = new File("highscores.txt");
			Scanner Reader = new Scanner(f);
			HighscoreEntry entry;
			Reader.useDelimiter(";");
			highscores.clear();
			while (Reader.hasNext())
			{
				entry = new HighscoreEntry();
				entry.username = Reader.next();
				entry.score = Integer.parseInt(Reader.next());
				highscores.add(entry);
			}
			Reader.close();
		} catch (FileNotFoundException ex) {
			System.out.println(ex.toString());
		}
	}
	
	public void saveHighscores()
	{
		try {
			FileWriter Writer = new FileWriter("highscores.txt");
			for (int i = 0; i < highscores.size(); i++)
			{
				Writer.write(highscores.get(i).username + ";" + highscores.get(i).score + ";");
			}
			Writer.close();
		} catch (IOException ex)
		{
			System.out.println(ex.toString());
		}
	}
	
	public void sortHighscores()
	{
		for (int i = 0; i < highscores.size(); i++)
		{
			for (int j = 0; j < highscores.size() - 1; j++)
			{
				if (highscores.get(j).score < highscores.get(j+1).score)
				{
					Collections.swap(highscores,j,j+1);
				}
			}
		}
		if (highscores.size() > 10)
		{
			highscores = highscores.subList(0, 10);
		}
	}
	
	public void add(String name, int score)
	{
		HighscoreEntry newEntry = new HighscoreEntry();
		newEntry.username = name;
		newEntry.score = score;
		highscores.add(newEntry);
	}
	
}