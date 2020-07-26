import java.awt.Graphics;
import java.awt.image.BufferedImage;

class Fireball extends Sprite
{
    double verticalVelocity = 4.9;
    double horizontalVelocity = 20;

    boolean isMario() {return false;}
    boolean isTube() {return false;}
    boolean isGoomba() {return false;}
    boolean isFireball() {return true;}




    Fireball(int xx, int yy, Model m)
    {
        type = "fireBall";
        x = xx;
        y = yy;
        w = 47;
        h = 47;
        model = m;
        verticalVelocity = 0;
        currentImage = loadImage("fireball.png");
    }

    Fireball(Fireball copy)
    {
        this.type = copy.type;
        this.x = copy.x;
        this.y = copy.y;
        this.w = copy.w;
        this.h = copy.h;
        this.currentImage = copy.currentImage;
    }

    Sprite makeClone()
    {
        return new Fireball(this);
    }



    void draw(Graphics g)
    {
        g.drawImage(currentImage, x - model.cameraPosition(), y, null);
    }

    void update()
    {
        bounce();
        x += horizontalVelocity;
        isFireballOffScreen();

    }

    void isFireballOffScreen()
    {
        if(this.x >= model.mario.x + 700)
        {
            dead = true;
        }
    }

    void bounce()
    {
        verticalVelocity += 6.9;
        y += verticalVelocity;

        if(y >= 525)
        {
            y = 525;
            verticalVelocity = -30.9;
        }

        y += verticalVelocity;
    }

    boolean isCollidable()
    {
        return false;
    }

    Json createJsonObject()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        return ob;
    }
}
