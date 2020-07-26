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
	// final int Width = 500;
	// final int Height = 500;

	BufferedImage tube_image;
	Model model;

	int scrollPosition;



	View(Controller c, Model m)
	{
		//loads the image but doesn't draw it
		try
		{
			this.tube_image = ImageIO.read(new File("tube.png"));
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}

		model = m;

		scrollPosition = 0;
	}

	//sets model reference within view
	void setModel(Model m)
	{
		model = m;
	}

	// sets the scroll position by incrementing the current scrollPosition by x
	public void setScrollPosition(int x)
	{
		scrollPosition = scrollPosition + x;
	}


	// creates and draws graphics, particularly the image that was loaded
	public void paintComponent(Graphics g)
	{
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// for loop that draws all tubes created
		for (int i = 0; i < model.tubes.size(); i++)
		{
			Tube t = model.tubes.get(i);
			g.drawImage(tube_image, t.x - scrollPosition, t.y, null);
		}
	}


	int findFirstTubeOnScreen()
	{
		int start = 0;
		int end = model.tubes.size();
		while(true)
		{
			int mid = (start + end) / 2;
			if(mid == start)
				return start;
			Tube t = model.tubes.get(mid);
			if(t.x - scrollPosition < -100)
				start = mid;
			else
				end = mid;
		}
	}

}
