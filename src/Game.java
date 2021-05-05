import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable
{
	public static final int WIDTH = 1024, HEIGHT = 768;

	private Thread thread;
	private Handler handler;
	private KeyInput keyboard;
	private Random random;
	private Color sky;
	private int floorY;
	private int[][] stars;
	private Spawner spawner;
	private HUD hud;

	public static void main(String[] args)
	{
		new Game();
	}

	public Game()
	{
		random = new Random();
		initTerrain();
		handler = new Handler();

		handler.setPlayer(new Player(ID.Player, WIDTH / 2 - 10, floorY - 60, 20, 60));
		spawner = new Spawner(handler.getPlayer(), floorY, handler);
		hud = new HUD(handler.getPlayer().getHealth());

		new Window("Type Samurai", WIDTH, HEIGHT, this);
		keyboard = new KeyInput(handler);
		this.addKeyListener(keyboard);

		this.setFocusable(true);
		this.requestFocus();

		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run()
	{
		// Notch's game loop :D
		long lastTime = System.nanoTime(); // nanoTime - most precise unit of time
		double targetFPS = 60;
		double nsPerTick = 1000000000 / targetFPS; // 1 second / 60 => how many ticks in one second
		double delta = 0;

		while(true)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / nsPerTick;
			lastTime = now;
			while(delta >= 1)
			{
				tick();
				delta--;
			}
			render();

			try
			{
				Thread.sleep(8);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void tick()
	{
		handler.tick();
		hud.setPlayerHealth(handler.getPlayer().getHealth());
	}

	private void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(sky);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(Color.white);
		for(int[] star : stars)
		{
			g.fillOval(star[0], star[1], star[2], star[2]);
		}
		g.setColor(Color.black);
		g.fillRect(0, floorY, WIDTH, HEIGHT - floorY);
		g.setFont(new Font("Yu Gothic Regular", Font.BOLD, 26));

		handler.render(g);
		hud.render(g);

		g.dispose();
		bs.show();
	}


	private void initTerrain()
	{
		sky = new Color(10, 10, 45, 255);
		floorY = 600;
		stars = new int[20][3];
		int starCounter = 0;
		for(int i = 0; i < 20; i++)
		{
			stars[starCounter][0] = random.nextInt(WIDTH); // x
			stars[starCounter][1] = random.nextInt(300); // y
			stars[starCounter++][2] = random.nextInt(3) + 3; // r
		}
	}
}
