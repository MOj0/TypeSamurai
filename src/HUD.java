import java.awt.*;

public class HUD
{
	private int playerHealth;
	private final int healthBarWidth;
	private double scale;
	private static int level, levelDisplayDuration;

	public HUD(double playerHealth)
	{
		this.playerHealth = (int) playerHealth;
		healthBarWidth = 175;
		scale = healthBarWidth / playerHealth;
		level = levelDisplayDuration = 0;
	}

	public void setPlayerHealth(int health)
	{
		playerHealth = health;
	}

	public void render(Graphics g)
	{
		g.setColor(Color.gray);
		g.fillRect(10, 40, healthBarWidth, 30);
		g.setColor(Color.white);
		g.drawRect(10, 40, healthBarWidth, 30);

		g.setColor(new Color(255 - (int) (playerHealth * 2.55), (int) (playerHealth * 2.55), 0));
		g.fillRect(10, 40, (int) (scale * playerHealth), 30);

		if(levelDisplayDuration != 0)
		{
			g.setColor(Color.white);
			g.drawString("LEVEL " + level, 500, 200);
			levelDisplayDuration = Math.max(0, levelDisplayDuration - 1);
		}
	}

	public static void displayNextLevel(int l)
	{
		level = l;
		levelDisplayDuration = 200;
	}
}
