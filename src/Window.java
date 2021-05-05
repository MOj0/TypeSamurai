import javax.swing.*;

public class Window extends JFrame
{
	public Window(String title, int width, int height, Game game)
	{
		new JFrame();
		this.setTitle(title);

		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);


		this.add(game);
	}
}
