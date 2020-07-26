import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Goomba extends Sprite
{
    BufferedImage goombaOnFireImage;
    boolean onFire;
    boolean walkRight;
    int onFireCounter = 0;
    double verticalVelocity = 0;

    boolean isMario() {return false;}
    boolean isTube() {return false;}
    boolean isGoomba() {return true;}
    boolean isFireball() {return false;}



    Goomba(int xx, int yy, Model m)
    {
        type = "goomba";
        x = xx;
        y = yy;
        w = 99;
        h = 118;
        currentImage = loadImage("goomba.png");
        goombaOnFireImage = loadImage("goombaOnFire.png");
        walkRight = true;
        onFire = false;
        model = m;
    }

    Goomba(Goomba copy)
    {
        this.type = copy.type;
        this.x = copy.x;
        this.y = copy.y;
        this.w = copy.w;
        this.h = copy.h;
        this.currentImage = copy.currentImage;
        this.goombaOnFireImage = copy.goombaOnFireImage;
        this.walkRight = copy.walkRight;
        this.onFire = copy.onFire;
        }

    Sprite makeClone()
    {
        return new Goomba(this);
    }



    void draw(Graphics g)
    {
        g.drawImage(currentImage, x - model.cameraPosition(), y, null);
    }

    void update()
    {
        move();

        checkForCollisions();
        updateImage();

        if(onFireCounter >= 10)
        {
            dead = true;
        }
    }

    void checkForCollisions()
    {
        for(int i = 0; i < model.sprites.size(); i++)
        {
            Sprite s = model.sprites.get(i);
            if(Sprite.didTwoSpritesCollide(this, s))
            {
                if(s.isTube())
                {
                    walkRight = !walkRight;
                    getOutOfTube(s);
                }
                else if (s.isFireball())
                {
                    onFire = true;
                    s.dead = true;
                    model.deadGoombas++;
                }
            }
        }
    }

    void move()
    {
        verticalVelocity += 4.5;
        y += verticalVelocity;

        if(y >= 478)
        {
            y = 478;
            verticalVelocity = 0;
        }

        if(walkRight)
        {
            x += 5;
        }
        else
        {
            x -= 5;
        }
    }


    void updateImage()
    {
        if(onFire)
        {
            currentImage = goombaOnFireImage;
            onFireCounter++;
        }

    }


    boolean isCollidable()
    {
        return true;
    }





    void getOutOfTube(Sprite s)
    {
        if((this.x + this.w > s.x) && (this.preX <= s.x))
        {
            this.x = s.x - this.w - 1;
        }
        else if((this.x < s.x + s.w) && (this.preX >= s.x + s.w))
        {
            this.x = s.x + s.w + 1;
        }
        else
        {
            System.out.println("How did I get in here?");
        }
    }



    Json createJsonObject()
    {
        Json ob = Json.newObject();
        ob.add("type", "goomba");
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        ob.add("currentImage", "goomba.png");
        ob.add("goombaOnFireImage", "goombaOnFire.png");
        ob.add("walkRight", true);
        ob.add("onFire", false);
        ob.add("dead", false);

        return ob;
    }

    Goomba(Json ob, Model m)
    {
        type = ob.getString("type");
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
        currentImage = loadImage(ob.getString("currentImage"));
        goombaOnFireImage = loadImage(ob.getString("goombaOnFireImage"));
        walkRight = ob.getBool("walkRight");
        onFire = ob.getBool("onFire");
        dead = ob.getBool("dead");

        model = m;
    }

}
