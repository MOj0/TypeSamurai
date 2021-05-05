import java.awt.*;

public class HUD
{
	private int playerHealth, healthBarWidth;
	private double scale;

	public HUD(double playerHealth)
	{
		this.playerHealth = (int) playerHealth;
		healthBarWidth = 175;
		scale = healthBarWidth / playerHealth;
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
	}
}
