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

	private void checkTextCollisions(int index)
	{
		Rectangle r1 = list.get(index).getTextBounds();

		if(r1.x == -1)
		{
			return;
		}
		for(int i = index + 1; i < list.size(); i++)
		{
			GameObject object = list.get(i);
			Rectangle r2 = object.getTextBounds();
			if(r1.intersects(r2))
			{
				list.get(i).moveText(-1);
			}
			//TODO!
//			else
//			{
//				Rectangle r3 = new Rectangle(r2.x, r2.y + 42, r2.width, r2.height);
//				if(!r1.intersects(r3))
//				{
//					object.moveText(1);
//				}
//			}
		}
	}

	public void tick()
	{
		player.tick();
		for(int i = 0; i < list.size(); i++)
		{
			GameObject object = list.get(i);
			if(object.getId() != ID.Player)
			{
				object.setPlayer(player); // Update player object on enemies

				checkTextCollisions(i);
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
