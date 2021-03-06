import java.awt.*;

public class Enemy extends GameObject
{
	private int speed, damage;
	private String text;
	private int currentTextIndex, textX, initialTextY, textY, textWidth, textHeight;
	private boolean textFall;
	private double initialTextTimer, textTimer;
	private boolean textTimerReset;
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
		textX = textY = textWidth = textHeight = -1;
		textTimerReset = true;
		textFall = true;
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

	@Override
	public Rectangle getTextBounds()
	{
		return new Rectangle(textX, textY, textWidth, textHeight);
	}

	@Override
	public Rectangle getGroundRect()
	{
		return new Rectangle(textX, textY + textHeight + 1, textWidth, 1);
	}

	@Override
	public int getMaxTextY()
	{
		return initialTextY + textHeight;
	}

	@Override
	public void moveText(int dir)
	{
		textY += dir * textHeight;
	}

	@Override
	public void setTextFall(boolean fall)
	{
		this.textFall = fall;
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
			if(!textTimerReset)
			{
				player.resetScoreMultiplier();
			}
			currentTextIndex = 0;
			targeted = false;
			textTimerReset = true;
		}
		else
		{
			textTimerReset = false;
		}

		textY = textFall ? Math.min(initialTextY, textY + 1) : textY;

		if(Math.abs(x - px) <= width) // Touching the player
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
		if(textY == -1 || textWidth == -1 || textHeight == -1) // If variables weren't set yet
		{
			textWidth = metrics.stringWidth(text);
			textHeight = metrics.getHeight();
			initialTextY = y - 20;
			textY = initialTextY;
		}
		textX = x - textWidth / 2 + width / 2;

		g.setColor(textBackground);
		g.fillRoundRect(textX - 5, textY - 2 * textHeight / 3, textWidth + 10, textHeight, 30, 30);

		// Timer rectangle -> indicates how much time you have left to strike an enemy
		g.setColor(Color.gray);
		g.fillRoundRect(textX - 5,
				(int) (textY - 2 * textHeight / 3 + textHeight * (1 - textTimer / initialTextTimer)),
				textWidth + 10, (int) (textHeight - textHeight * (1 - textTimer / initialTextTimer)), 30, 30);

		// Full text
		g.setColor(Color.white);
		g.drawString(text, textX, textY);

		// Currently typed text
		g.setColor(Color.red);
		g.drawString(text.substring(0, currentTextIndex), textX, textY);

		if(targeted)
		{
			g.setColor(Color.white);
			Graphics2D g2d = (Graphics2D) g.create(); // g.create() so we have 2 different objects
			g2d.setStroke(new BasicStroke(4));
			g2d.drawRoundRect(textX - 8, textY - 2 * textHeight / 3, textWidth + 16, textHeight, 30, 30);
		}
	}
}
