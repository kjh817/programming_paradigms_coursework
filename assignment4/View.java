import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;

class View extends JPanel
{

	Model model;

	View(Controller c, Model m)
	{
		model = m;
	}

	// creates and draws graphics, particularly the image that was loaded
	public void paintComponent(Graphics g)
	{
		// draws background
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// for loop that draws all tubes created
		for (int i = 0; i < model.sprites.size(); i++)
		{
			Sprite s = model.sprites.get(i);
			s.draw(g);
		}

		// draws ground
		g.setColor(Color.gray);
		g.drawLine(0, 596, 2000, 596);
	}


	//sets model reference within view
	void setModel(Model m)
	{
		model = m;
	}


	// int findFirstSpriteOnScreen()
	// {
	// 	int start = 0;
	// 	int end = model.sprites.size();
	// 	while(true)
	// 	{
	// 		int mid = (start + end) / 2;
	// 		if(mid == start)
	// 			return start;
	// 		Sprite s = model.sprites.get(mid);
	// 		if(s.x - model.mario.x < -100)
	// 			start = mid;
	// 		else
	// 			end = mid;
	// 	}
	// }

}
