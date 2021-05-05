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

//		spawnEnemies(3);

//		handler.add(new Enemy(ID.Enemy, random.nextInt(Game.WIDTH), floorY - 60, 20, 60, 1, 1,
//				"asdr", player));
//		handler.add(new Enemy(ID.Enemy, random.nextInt(Game.WIDTH), floorY - 60, 20, 60, 1, 1,
//				"rewq", player));

		handler.add(new Enemy(ID.Enemy, random.nextInt(Game.WIDTH), floorY - 60, 20, 60, 1, 1,
				"asdq", player));
		handler.add(new Enemy(ID.Enemy, random.nextInt(Game.WIDTH), floorY - 60, 20, 60, 1, 1,
				"asdw", player));
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
		for(int i = 0; i < n; i++)
		{
			handler.add(new Enemy(ID.Enemy, random.nextInt(Game.WIDTH), floorY - 60, 20, 60, 1, 1,
					words.get(random.nextInt(nWords)), player));
		}
	}
}
