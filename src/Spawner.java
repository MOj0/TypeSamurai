import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Spawner
{
	private Player player;
	private Handler handler;
	private int level;
	private ArrayList<String> words;
	private Random random;
	private int nWords, floorY;

	public Spawner(Player player, int floorY, Handler handler)
	{
		this.player = player;
		this.floorY = floorY;
		this.handler = handler;
		level = 1;

		random = new Random();
		words = new ArrayList<>();
		getWordsFromFile("res/words.txt");
		nWords = words.size();

		spawnEnemies(15);
	}

	private void getWordsFromFile(String fileName)
	{
		try
		{
			Scanner sc = new Scanner(new File(fileName));
			while(sc.hasNextLine())
			{
				words.add(sc.nextLine());
			}
			sc.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	private void spawnEnemy()
	{
		handler.add(new Enemy(ID.Enemy, 800, floorY - 60, 20, 60, 1, 1,
				words.get(random.nextInt(nWords)), player));
	}


	private void spawnEnemies(int n)
	{
		// Contains linked words (banana -> ananas -> slice ...) so player can chain enemies
		String[] linkedWords = getLinkedWords(n);

		for(int i = 0; i < n; i++)
		{
			handler.add(new Enemy(ID.Enemy, random.nextInt(Game.WIDTH), floorY - 60, 20, 60, 1, 1,
					linkedWords[i], player));
		}
	}

	private String[] getLinkedWords(int n)
	{
		String[] linkedWords = new String[n];
		linkedWords[0] = words.get(random.nextInt(nWords));

		for(int i = 1; i < n; i++)
		{
			char nextChar = linkedWords[i - 1].charAt(linkedWords[i - 1].length() - 1); // Last char in prev word
			int startIndex = random.nextInt(nWords); // Used for heuristics
			for(int j = startIndex; j < nWords && j >= 0; j += (startIndex < nWords / 2) ? 1 : -1)
			{
				String word = words.get(j);
				if(word.charAt(0) == nextChar && !checkWordDuplicate(word, linkedWords)) // Found link
				{
					linkedWords[i] = word;
					break;
				}
			}
		}
		return linkedWords;
	}

	private boolean checkWordDuplicate(String word, String[] linkedWords)
	{
		for(String linkedWord : linkedWords)
		{
			if(word.equals(linkedWord)) // We don't want two same words
			{
				return true;
			}
		}
		return false;
	}
}
