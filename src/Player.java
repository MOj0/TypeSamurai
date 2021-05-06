import java.awt.*;

public class Player // doesnt extend GameObject, not similar enough :(
{
	private ID id;
	private int x, y, width, height, health;

	public Player(ID id, int x, int y, int width, int height)
	{
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		health = 100;
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
	}
}
