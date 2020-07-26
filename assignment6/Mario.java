import java.awt.Graphics;
import java.awt.image.BufferedImage;


class Mario extends Sprite
{
    BufferedImage[] marioImages; // stores all mario images that may be used

    double verticalVelocity; // the current vertical velocity of mario
    int frame = 0;          // current "frame" of the mario animation

    int jumpCounter = 0;    // counts how long since mario touched the ground

    boolean isMario() {return true;}
    boolean isTube() {return false;}
    boolean isGoomba() {return false;}
    boolean isFireball() {return false;}


    // Creates a Mario object with a reference to the model
    Mario(Model m)
    {
        this.model = m;
        this.type = "mario";
        this.x = 0;
        this.y = 500;
        this.w = 57;
        this.h = 96;
        this.verticalVelocity = 0;
        this.loadMarioImages();
        this.currentImage = marioImages[frame];
    }

    Mario(Mario copy)
    {
        this.type = copy.type;
        this.x = copy.x;
        this.y = copy.y;
        this.w = copy.w;
        this.h = copy.h;
        this.verticalVelocity = copy.verticalVelocity;
        this.marioImages = copy.marioImages;
        this.currentImage = copy.currentImage;
    }

    Sprite makeClone()
    {
        return new Mario(this);
    }



    // loads all mario images that may be used and stores them in an array
    void loadMarioImages()
    {
        this.marioImages = new BufferedImage[5];

        this.marioImages[0] = loadImage("mario0.png");
        this.marioImages[1] = loadImage("mario1.png");
        this.marioImages[2] = loadImage("mario2.png");
        this.marioImages[3] = loadImage("mario3.png");
        this.marioImages[4] = loadImage("mario4.png");
    }

    // Draws mario using the current image at mario's
    // y position and 200 pixels from left of screen
    void draw(Graphics g)
    {
        g.drawImage(currentImage, 200, this.y, null);
    }

    // updates mario's position within the model and
    // updates mario's image
    void update()
    {
   		fall();                   // Causes mario to fall
        resolveCollisions();      // Resolves collisions that may occur
        imageUpdate();            // updates image
    }

    // Resolves any collisions that may occur between mario
    // and another sprite by iterating through the list of
    // sprites within the model
    void resolveCollisions()
    {
        for(int i = 0; i < model.sprites.size(); i++)
        {
            Sprite s = model.sprites.get(i);
            if(s != this)
            {
                if(Sprite.didTwoSpritesCollide(this, s) && s.isCollidable())
                {
                        if (s.isTube())
                        {
                            getOutOfTube(s);
                        }
                        else if(s.isGoomba())
                        {
                            //do something
                        }

                        // other sprite collisions
                }
            }
        }
    }

    // causes mario's y value and vertical velocity
    // to change based on the laws of gravity
    void fall()
    {
        verticalVelocity += 4.9;
        this.y += verticalVelocity;     // increments y position

        // creates "ground" for mario
        if(this.y >= 500)
        {
            this.verticalVelocity = 0;
            this.y = 500;
            jumpCounter = 0;
        }
    }


    // causes mario to jump by changing verticalVelocity
    // and checking if a jump is possible
    void jump()
    {
    		if(jumpCounter < 4)
    		{
    			verticalVelocity += -25.0;
    			jumpCounter++;
    		}
    }

    // updates currentImage based on mario.x position
    void imageUpdate()
    {
    		frame = (Math.abs(x)/10) % 5;     // calculates frame based on mario.x
            currentImage = marioImages[frame];  // sets new currentImage
    }

    // increases mario's x position by adding
    // speed to the current x position
    void moveMario(int speed)
    {
    	this.x = this.x + speed;

        // stops mario from going more than -400 px left
    	if(this.x < -400)
		{
    		this.x = -400;
    	}
    }



    // updates mario's position so that mario is not inside a tube
    void getOutOfTube(Sprite s)
    {
        if((this.x + this.w > s.x) && (this.preX <= s.x)) // if he crossed the left side of the tube
        {
        	this.x = s.x - this.w - 1;
        }
        else if((this.x < s.x + s.w) && (this.preX >= s.x + s.w)) // if he crossed the right side
        {
            this.x = s.x + s.w + 1;
        }

        else if((this.y + this.h > s.y) && (this.preY <= s.y)) // if he crossed the top of the tube
        {
            this.y = s.y - this.h;
            verticalVelocity = 0;
            jumpCounter = 0;
        }
        else if((this.y < s.y + s.h) && (this.preY >= s.y + s.h)) // if he crossed the bottom
        {
            this.y = s.y + s.h;
        }
        else
        {
            System.out.println("How did I get in here?");
        }
    }


    // saves mario to a Json object
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

    // creates a mario object using a Json object
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


    // returns if mario is collidable
    // mario is not collidable so returns false
    boolean isCollidable()
    {
        return false;
    }

    void move(int deltaX)
    {
        this.x += deltaX;
    }
}
