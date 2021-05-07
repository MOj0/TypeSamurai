import java.awt.*;

public class Player // Doesn't extend GameObject, not similar enough :(
{
	private ID id;
	private int x, y, width, height, health, score, scoreMultiplier;

	public Player(ID id, int x, int y, int width, int height)
	{
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		health = 100;
		score = 0;
		scoreMultiplier = 1;
	}

	public int getX()
	{
		return x;
	}

	public int getHealth()
	{
		return health;
	}

	public void dealDamage(int d)
	{
		health = Math.max(0, health - d);
	}

	public void addScore(GameObject object)
	{
		int scoreAmount = 0;
		if(object.getId() == ID.Enemy)
		{
			scoreAmount = 1;
		}
		score += scoreAmount * scoreMultiplier;
		scoreMultiplier *= 2;
	}

	public void resetScoreMultiplier()
	{
		scoreMultiplier = 1;
	}

	public void tick()
	{
		if(health < 1)
		{
//			System.err.println("YIKES YOU DIED");
		}
	}

	public void render(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(x, y, 20, 60);

		g.drawString("Score: " + score, 10, 120);
		g.drawString("Score multiplier: " + scoreMultiplier, 10, 180);
	}
}
