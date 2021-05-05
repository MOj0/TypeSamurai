import java.awt.*;

public class Enemy extends GameObject
{
	private String text, typedText;
	private int speed, damage;
	private Color textBackground;
	private Player player;
	private boolean targeted;

	public Enemy(ID id, int x, int y, int width, int height, int speed, int damage, String text, Player player)
	{
		super(id, x, y, width, height);
		this.text = text;
		typedText = "";
		this.speed = speed;
		this.damage = damage;
		this.player = player;
		textBackground = new Color(60, 60, 80, 200);
		targeted = false;
	}

	public String getText()
	{
		return text;
	}

	public void setTargeted(boolean t)
	{
		targeted = t;
		typedText = targeted ? typedText : ""; // Reset the typed text if needed
	}

	public void setTypedText(String typedText)
	{
		this.typedText = typedText;
	}

	@Override
	public void setPlayer(Player p)
	{
		player = p;
	}

	@Override
	public void tick()
	{
		int px = player.getX();
		if(Math.abs(x - px) <= width)
		{
			player.dealDamage(damage);
			return;
		}
		x += (x < px) ? 0 : -0; // TODO: 05/05/2021 change to speed
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
		g.fillRect(x - w / 2 + width / 2 - 5, y - 20 - 2 * h / 3, w + 10, h);

		g.setColor(Color.white);
		g.drawString(text, x - w / 2 + width / 2, y - 20);

		g.setColor(Color.red);
		g.drawString(typedText, x - w / 2 + width / 2, y - 20);

		if(targeted)
		{
			g.setColor(Color.white);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(4));
			g.drawRect(x - w / 2 + width / 2 - 8, y - 20 - 2 * h / 3, w + 16, h);
		}
	}
}
