import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.CopyOnWriteArrayList;

public class KeyInput extends KeyAdapter
{
	private Handler handler;

	public KeyInput(Handler handler)
	{
		this.handler = handler;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		char key = e.getKeyChar();
		CopyOnWriteArrayList<GameObject> list = handler.getList();
		boolean enemyWasHit = false;

		for(int i = list.size() - 1; i >= 0; i--)
		{
			GameObject object = list.get(i);
			if(object.getId() == ID.Enemy)
			{
				int status = object.checkWasKilled(key);
				if(status == 1)
				{
					handler.remove(object);
				}
				enemyWasHit = status != -1 || enemyWasHit;
			}
		}

		if(!enemyWasHit)
		{
			for(GameObject object : list)
			{
				object.setTargeted(false);
			}
		}
	}
}
