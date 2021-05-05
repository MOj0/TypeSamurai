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

		for(GameObject object : handler.getList())
		{
			String enemyText = object.getText();
			object.setTargeted(false);
			if(enemyText != null && enemyText.charAt(nextIndex) == key)
			{
				enemyWasHit = true;
				object.setTypedText(currentText.toString() + key);
				object.setTargeted(true);

				if((currentText.toString() + key).equals(enemyText))
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
		handler.setCurrentText(currentText.toString());
	}
}
