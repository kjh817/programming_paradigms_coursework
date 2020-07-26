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

    // abstract method to update sprite variables
    abstract void update();

    // abstract method to draw sprite
    abstract void draw(Graphics g);

    // abstract method returns if sprite can collide with other sprites
    abstract boolean isCollidable();

    // abstract method creates json object of a sprite
    abstract Json createJsonObject();

    // abstract method that creates a deep copy of a sprite
    abstract Sprite makeClone();

    // checks if sprite is a mario sprite
    abstract boolean isMario();

    // checks if sprite is a tube sprite
    abstract boolean isTube();

    // checks if sprite is a goomba sprite
    abstract boolean isGoomba();

    // checks if sprite is a goomba sprite
    abstract boolean isFireball();


    // setter method for model
    void setModel(Model m)
    {
        model = m;
    }

    // returns a boolean stating if the sprite is dead
    boolean isSpriteDead()
    {
        return dead;
    }

    // sets preX and preY based on current x and y
    void rememberState()
    {
        this.preY = this.y;
        this.preX = this.x;
    }

    // returns this.type as a string
    String getType()
    {
        return type;
    }

    /*----------- Lazy loads an image --------------
    Loads an image by passing the fileName and
    returning the fileName contents as a BufferedImage
    ------------------------------------------------*/
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


    // Checks for a collision between two sprites
    // by checking if sprites overlap in any of
    // the four directions. Returns false if no
    // overlap. Otherwise returns true;
    static boolean didTwoSpritesCollide(Sprite s1, Sprite s2)
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
