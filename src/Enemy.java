import java.awt.*;

public class Enemy extends GameObject
{
	private int speed, damage;
	private String text;
	private int currentTextIndex;
	private double initialTextTimer, textTimer;
	private Color textBackground;
	private Player player;
	private boolean targeted;

	public Enemy(ID id, int x, int y, int width, int height, int speed, int damage, String fullText, Player player)
	{
		super(id, x, y, width, height);
		this.text = fullText;
		currentTextIndex = 0;
		initialTextTimer = 100;
		textTimer = 0;
		this.speed = speed;
		this.damage = damage;
		this.player = player;
		textBackground = new Color(60, 60, 80, 200);
		targeted = false;
	}

	public void setTargeted(boolean targeted)
	{
		this.targeted = targeted;
		currentTextIndex = targeted ? currentTextIndex : 0; // Reset the text index if needed
		textTimer = targeted ? textTimer : 0; // Reset the timer if needed
	}

	@Override
	public void setPlayer(Player p)
	{
		player = p;
	}

	/**
	 * @param c: character to check
	 * @return 1 - was killed, 0 - was hit, -1 - was missed
	 */
	@Override
	public int checkWasKilled(char c)
	{
		if(text.charAt(currentTextIndex) == c)
		{
			if(currentTextIndex++ == text.length() - 1)
			{
				return 1;
			}
			targeted = true;
			textTimer = initialTextTimer;
			return 0;
		}
		return -1;
	}


	@Override
	public void tick()
	{
		int px = player.getX();

		textTimer = Math.max(0, textTimer - 1);
		if(textTimer == 0)
		{
			currentTextIndex = 0;
			targeted = false;
		}

		if(Math.abs(x - px) <= width)
		{
			player.dealDamage(damage);
			return;
		}
		x += (x < px) ? speed : -speed;
	}

	@Override
	public void render(Graphics g)
	{
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);

		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int h = metrics.getHeight();
		int w = metrics.stringWidth(text);

		g.setColor(textBackground);
		g.fillRoundRect(x - w / 2 + width / 2 - 5, y - 20 - 2 * h / 3, w + 10, h, 30, 30);

		g.setColor(Color.gray);
		g.fillRoundRect(x - w / 2 + width / 2 - 5, (int) (y - 20 - 2 * h / 3 + h * (1 - textTimer / initialTextTimer)),
				w + 10, (int) (h - h * (1 - textTimer / initialTextTimer)), 30, 30);

		g.setColor(Color.white);
		g.drawString(text, x - w / 2 + width / 2, y - 20);

		g.setColor(Color.red);
		g.drawString(text.substring(0, currentTextIndex), x - w / 2 + width / 2, y - 20);

		if(targeted)
		{
			g.setColor(Color.white);
			Graphics2D g2d = (Graphics2D) g.create(); // g.create() so we have 2 different objects
			g2d.setStroke(new BasicStroke(4));
			g2d.drawRoundRect(x - w / 2 + width / 2 - 8, y - 20 - 2 * h / 3, w + 16, h, 30, 30);
		}
	}

	@Override
	public int getHealth()
	{
		return -1;
	}
}
