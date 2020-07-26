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

	BufferedImage tubeImage;
	BufferedImage[] marios;
	Model model;


	View(Controller c, Model m)
	{
		model = m;
		tubeImage = loadImage("tube.png");

		marios = new BufferedImage[5];
		marios[0] = loadImage("mario0.png");
		marios[1] = loadImage("mario1.png");
		marios[2] = loadImage("mario2.png");
		marios[3] = loadImage("mario3.png");
		marios[4] = loadImage("mario4.png");
	}

	static BufferedImage loadImage(String fileName)
	{
		BufferedImage im = null;

		try
		{
			im = ImageIO.read(new File(fileName));
		} catch(Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}

		return im;

	}



	// creates and draws graphics, particularly the image that was loaded
	public void paintComponent(Graphics g)
	{
		// draws background
		g.setColor(new Color(128, 255, 255));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());

		// for loop that draws all tubes created
		for (int i = 0; i < model.tubes.size(); i++)
		{
			Tube t = model.tubes.get(i);
			g.drawImage(tubeImage, t.x - model.mario.x + 200, t.y, null);
		}

		// draws ground
		g.setColor(Color.gray);
		g.drawLine(0, 596, 2000, 596);

		// draw mario
		g.drawImage(marios[model.mario.frame], 200, model.mario.y, null);

	}


	//sets model reference within view
	void setModel(Model m)
	{
		model = m;
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
			if(t.x - model.mario.x < -100)
				start = mid;
			else
				end = mid;
		}
	}

}
