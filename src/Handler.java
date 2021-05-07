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
		player.addScore(object);
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


	private boolean checkGroundCollision(GameObject object)
	{
		Rectangle groundRect = object.getGroundRect();
		if(groundRect.y > object.getMaxTextY()) // Max y value reached
		{
			return false;
		}
		for(GameObject object1 : list)
		{
			if(groundRect.intersects(object1.getTextBounds())) // There is something below, can't fall further
			{
				return false;
			}
		}
		return true;
	}

	private void moveTextObjects(int index)
	{
		GameObject object1 = list.get(index);
		Rectangle r1 = object1.getTextBounds();
		if(r1.x == -1) // If text isn't rendered yet
		{
			return;
		}
		for(int i = index + 1; i < list.size(); i++) // Loop through all remaining objects
		{
			GameObject object2 = list.get(i);
			if(r1.intersects(object2.getTextBounds())) // If it intersects with another
			{
				object2.moveText(-1); // Move that other one up
			}
		}
		object1.setTextFall(checkGroundCollision(object1));
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
				moveTextObjects(i); // Moves text objects up, if they intersect -> player always sees all objects
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
