import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

abstract class Sprite
{
    int x;
    int y;
    int w;
    int h;
    int preX;
    int preY;
    BufferedImage currentImage;
    String type;
    boolean dead = false;
    Model model;


    abstract void update();

    abstract void draw(Graphics g);

    abstract boolean isCollidable();

    abstract Json createJsonObject();

    

    void setModel(Model m)
    {
        model = m;
    }

    boolean isSpriteDead()
    {
        return dead;
    }

    void rememberState()
    {
        this.preY = this.y;
        this.preX = this.x;
    }

    String getType()
    {
        return type;
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



    // Checks for a click on a sprite by comparing
    // mouse location and sprite dimensions
    // If mouse_x & mouse_y are outside the w & h
    // of a sprite, function returns false.
    // Othewise the function returns true.
    boolean isThereASprite(int mouse_x, int mouse_y)
    {
        if(mouse_x < this.x)
        {
            return false;
        }else if(mouse_x > this.x + w)
        {
            return false;
        }else if(mouse_y < this.y)
        {
            return false;
        }else if (mouse_y > this.y + h)
        {
            return false;
        }
        return true;
    }


    static boolean collisonDetector(Sprite s1, Sprite s2)
	{
		if (s1.x + s1.w < s2.x)
		{
			return false;
		}

		if (s1.x > s2.x + s2.w)
		{
			return false;
		}

		if (s1.y + s1.h < s2.y)
		{
			return false;
		}

		if (s1.y > s2.y + s2.h)
		{
			return false;
		}

		return true;
	}
}
