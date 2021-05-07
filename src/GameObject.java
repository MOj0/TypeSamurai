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

	public ID getId()
	{
		return id;
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	public abstract void setTargeted(boolean t);

	public abstract void setPlayer(Player p);

	public abstract int checkWasKilled(char c);

	public abstract Rectangle getTextBounds();

	public abstract Rectangle getTextMovedBounds(int dir);

	public abstract Rectangle getGroundRect();

	public abstract int getMaxTextY();

	public abstract void moveText(int dir);

	public abstract void resetTextY();

	public abstract void setTextFall(boolean fall);
}
