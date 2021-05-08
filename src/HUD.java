import java.awt.*;

public class HUD
{
	private int playerHealth;
	private final int healthBarWidth;
	private double scale;
	private static int level, levelDisplayDuration, levelDisplayTreshold, nEnemies;
	private static float levelDisplayX;

	public HUD(double playerHealth)
	{
		this.playerHealth = (int) playerHealth;
		healthBarWidth = 175;
		scale = healthBarWidth / playerHealth;
		level = levelDisplayDuration = 0;
		levelDisplayTreshold = 250;
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

		g.setColor(Color.red);
		g.drawString(nEnemies + "", 500, 100);

		if(levelDisplayDuration != 0 && levelDisplayDuration <= levelDisplayTreshold)
		{
			g.setColor(Color.white);
			levelDisplayX += levelDisplayDuration >= levelDisplayTreshold * 0.92
					|| levelDisplayDuration <= levelDisplayTreshold * 0.1 ? 20 : 0.3f;
			g.drawString("LEVEL " + level, (int) levelDisplayX, 200);
		}
		levelDisplayDuration = Math.max(0, levelDisplayDuration - 1);
	}

	public static void displayNextLevel(int l)
	{
		level = l;
		levelDisplayDuration = levelDisplayTreshold + 100;
		levelDisplayX = 0;
	}

	public static void setRemainingEnemies(int enemies)
	{
		nEnemies = enemies;
	}
}
