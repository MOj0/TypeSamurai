import java.awt.*;

public abstract class GameObject
{
	protected ID id;
	protected int x, y;
	protected int width, height;

	public GameObject(ID id, int x, int y, int width, int height)
	{
		this.id = id;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getX()
	{
		return x;
	}

	public ID getId()
	{
		return id;
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract String getText();

	public abstract void setTargeted(boolean t);

	public abstract void setPlayer(Player p);

	public abstract void setTypedText(String typedText);
}
