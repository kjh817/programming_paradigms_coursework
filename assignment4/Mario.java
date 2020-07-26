import java.awt.Graphics;
import java.awt.image.BufferedImage;


class Mario extends Sprite
{
    BufferedImage[] marioImages;

    double verticalVelocity;
    int frame = 0;

    int jumpCounter = 0;


    Mario(Model m)
    {
        model = m;
        type = "mario";
        x = 0;
        y = 500;
        w = 57;
        h = 96;
        verticalVelocity = 0;
        loadMarioImages();
        currentImage = marioImages[frame];
    }

    void loadMarioImages()
    {
        marioImages = new BufferedImage[5];

        marioImages[0] = loadImage("mario0.png");
        marioImages[1] = loadImage("mario1.png");
        marioImages[2] = loadImage("mario2.png");
        marioImages[3] = loadImage("mario3.png");
        marioImages[4] = loadImage("mario4.png");
    }

    void draw(Graphics g)
    {
        g.drawImage(currentImage, 200, this.y, null);
    }


    void update()
    {
// System.out.println("X: " + x);
// System.out.println("Y: " + y);

    	// perform gravity
   		verticalVelocity += 4.9;
        this.y += verticalVelocity;

        // create ground
        if(this.y >= 500)
        {
            this.verticalVelocity = 0;
            this.y = 500;
            jumpCounter = 0;
        }

        for(int i = 0; i < model.sprites.size(); i++)
        {
            Sprite s = model.sprites.get(i);
            if(Sprite.collisonDetector(this, s) && s.isCollidable())
            {
                getOutOfTube(s);
            }
        }

        imageUpdate();
    }

    void jump()
    {
    		if(jumpCounter < 5)
    		{
    			verticalVelocity += -12.4;
    			jumpCounter++;
    		}
    }

    void imageUpdate()
    {
    		frame = (Math.abs(x)/10) % 5;
            currentImage = marioImages[frame];
    }


    void moveMario(int speed)
    {
    		this.x = this.x + speed;

    		if(this.x < -200)
    		{
    			this.x = -200;
    		}
    }




    void getOutOfTube(Sprite s)
    {
        if((this.x + this.w > s.x) && (this.preX <= s.x)) // if he crossed the left side of the tube
        {
        		if((this.y + this.h > s.y)
        				&& (this.preY <= s.y))
        		{
        			this.y = s.y - this.h;
        		}
        		else
        		{
        			this.x = s.x - this.w - 1;
        		}
        }
        else if((this.x < s.x + s.w) && (this.preX >= s.x + s.w)) // if he crossed the right side
        {
            this.x = s.x + s.w + 1;
        }

        else if((this.y + this.h > s.y) && (this.preY <= s.y)) // if he crossed the top of the tube
        {
            this.y = s.y - this.h;
        }
        else if((this.y < s.y + s.h) && (this.preY >= s.y + s.h)) // if he crossed the bottom
        {
            this.y = s.y + s.h + this.h;
        }
        else
            System.out.println("How did I get in here?");
    }


    Json createJsonObject()
    {
        Json ob = Json.newObject();
        ob.add("type", "mario");
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        ob.add("dead", false);
        ob.add("currentImage", "mario0.png");
        return ob;
    }

    Mario(Json ob, Model m)
    {
        type = ob.getString("type");
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
        dead = ob.getBool("dead");
        currentImage = loadImage(ob.getString("currentImage"));
        loadMarioImages();
        model = m;
    }


    boolean isCollidable()
    {
        return false;
    }


}
