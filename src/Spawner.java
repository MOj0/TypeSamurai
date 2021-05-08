import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Spawner
{
	private Player player;
	private Handler handler;
	private int level, nEnemies;
	private ArrayList<String> words;
	private LinkedList<String> linkedWords; // Contains linked words (banana -> ananas -> slice ...) so player can
	// chain enemies
	private Iterator<String> linkedIterator;
	private Random random;
	private int nWords, floorY, spawnPeriod;

	public Spawner(Player player, int floorY, Handler handler)
	{
		this.player = player;
		this.floorY = floorY;
		this.handler = handler;

		random = new Random();
		words = new ArrayList<>();
		linkedWords = new LinkedList<>();
		getWordsFromFile("res/words.txt");
		nWords = words.size();
		level = 0;

		nextLevel();
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

	private void nextLevel()
	{
		nEnemies = ++level * 4 + random.nextInt(2 + level);

		linkedWords.clear();
		linkedWords.addAll(Arrays.asList(getLinkedWords(nEnemies)));
		linkedIterator = linkedWords.iterator();

		spawnPeriod = 200;
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
				if(j == nWords - 1 || j == 0) // Link wasn't found, random bullshit, go
				{
					String randomWord;
					do
					{
						randomWord = words.get(random.nextInt(nWords));
						linkedWords[i] = words.get(random.nextInt(nWords));
					}while(checkWordDuplicate(randomWord, linkedWords));
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


	private void spawnEnemy(String word)
	{
		handler.add(new Enemy(ID.Enemy, random.nextInt(2) == 0 ? 0 : Game.WIDTH, floorY - 60, 20, 60, 1, 1,
				word, player));
	}

	private void spawnEnemies()
	{
		if(nEnemies == 0 && handler.getList().isEmpty())
		{
			nextLevel();
			HUD.displayNextLevel(level);
			return;
		}
		if(spawnPeriod == 0)
		{
			for(int i = 0; i < Math.min(nEnemies, random.nextInt(Math.min(4, level)) + 1); i++)
			{
				if(linkedIterator.hasNext())
				{
					spawnEnemy(linkedIterator.next());
					HUD.setRemainingEnemies(--nEnemies);
				}
			}
			spawnPeriod = 100 + level * 20;
		}
	}

	public void tick()
	{
		spawnEnemies();
		spawnPeriod = Math.max(0, spawnPeriod - 1);
	}
}
