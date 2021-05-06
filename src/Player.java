import java.awt.*;

public class Player extends GameObject
{
	private int health;

	public Player(ID id, int x, int y, int width, int height)
	{
		super(id, x, y, width, height);
		health = 100;
	}

	public void dealDamage(int d)
	{
		health = Math.max(0, health - d);
	}


	@Override
	public int getHealth()
	{
		return health;
	}

	@Override
	public void tick()
	{
		if(health < 1)
		{
			System.err.println("YIKES YOU DIED");
		}
	}

	@Override
	public void render(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(x, y, 20, 60);
	}

	@Override
	public int checkWasKilled(char c)
	{
		return 0;
	}

	@Override
	public void setTargeted(boolean t){}

	@Override
	public void setPlayer(Player p){}
}
