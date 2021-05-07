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
		GameObject object1 = list.get(index);
		Rectangle r1 = object1.getTextBounds();
		if(r1.x == -1)
		{
			return;
		}
		for(int i = index + 1; i < list.size(); i++)
		{
			GameObject object2 = list.get(i);
			if(r1.intersects(object2.getTextBounds()))
			{
				object2.moveText(-1);
			}
		}

		Rectangle groundRect = object1.getGroundRect();
		boolean shouldFall = true;
		for(int i = 0; i < list.size(); i++)
		{
			if(i == index)
			{
				continue;
			}
			if(groundRect.intersects(list.get(i).getTextBounds()) || groundRect.y > object1.getMaxTextY())
			{
				shouldFall = false;
				break;
			}
		}
		object1.setTextFall(shouldFall);
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
	}
}
