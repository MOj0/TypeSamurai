import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Handler
{
	private Player player;
	private CopyOnWriteArrayList<GameObject> list;

	public Handler()
	{
		list = new CopyOnWriteArrayList();
	}

	public void add(GameObject object)
	{
		list.add(object);
	}

	public void remove(GameObject object)
	{
		list.remove(object);
	}

	public void setPlayer(Player p)
	{
		player = p;
	}

	public Player getPlayer()
	{
		return player;
	}

	public CopyOnWriteArrayList<GameObject> getList()
	{
		return list;
	}

	public void tick()
	{
		player.tick();
		for(GameObject object : list)
		{
			if(object.getId() != ID.Player) // Update player object on enemies
			{
				object.setPlayer(player);
			}

			object.tick();
		}
	}

	public void render(Graphics g)
	{
		player.render(g);
		for(GameObject object : list)
		{
			object.render(g);
		}

		g.setColor(Color.white);
	}
}
