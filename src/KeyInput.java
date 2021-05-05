import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;

public class KeyInput extends KeyAdapter
{
	private Handler handler;
	private StringBuffer currentText;
	private int nextIndex;

	public KeyInput(Handler handler)
	{
		this.handler = handler;
		currentText = new StringBuffer();
		nextIndex = 0;
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		char key = e.getKeyChar();
		boolean enemyWasHit = false;
		boolean enemyWasKilled = false;
		LinkedList<GameObject> enemiesKilled = new LinkedList<>();

		String checkText = currentText.toString() + key;

		for(GameObject object : handler.getList())
		{
			String enemyText = object.getText();
			object.setTargeted(false);

			if(enemyText != null && enemyText.startsWith(checkText))
			{
				enemyWasHit = true;
				object.setTypedText(checkText);
				object.setTargeted(true);

				if(checkText.equals(enemyText))
				{
					enemiesKilled.add(object);
					enemyWasKilled = true;
				}
			}
		}

		Iterator<GameObject> reverseIterator = enemiesKilled.descendingIterator();
		while(reverseIterator.hasNext())
		{
			handler.remove(reverseIterator.next());
		}

		if(enemyWasKilled || !enemyWasHit)
		{
			currentText.delete(0, nextIndex);
		}
		else if(!enemyWasKilled)
		{
			currentText.append(key);
		}
		nextIndex = currentText.length();
	}
}
