import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Handler
{
	private Player player;
	private CopyOnWriteArrayList<GameObject> list;
	private String currentText = "";

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

	public void setCurrentText(String text)
	{
		currentText = text;
	}

	public void tick()
	{
		player.tick();
		for(GameObject object : list)
		{
			if(object.getId() != ID.Player)
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
		g.drawString(currentText, 20, 100); // TODO: 05/05/2021 DELETE? used for debugging
	}
}
