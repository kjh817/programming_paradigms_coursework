import java.awt.Graphics;

class Tube extends Sprite
{
    boolean isMario() {return false;}
    boolean isTube() {return true;}
    boolean isGoomba() {return false;}
    boolean isFireball() {return false;}




    Tube(int x, int y, Model m)
    {
        this.type = "tube";
        this.x = x;
        this.y = y;
        this.w = 55;
        this.h = 400;
        this.currentImage = loadImage("tube.png");
        this.model = m;
    }

    Tube(Tube copy)
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
        return new Tube(this);
    }


    // method to draw the tubes on the screen
    void draw(Graphics g)
    {
        g.drawImage(currentImage, x - model.cameraPosition(), y, null);
    }

    // method to update tubes if needed/wanted
    void update()
    {
    }

    boolean isCollidable()
    {
        return true;
    }

    // creates a Json object of a tube
    Json createJsonObject()
    {
        Json ob = Json.newObject();
        ob.add("type", "tube");
        ob.add("x", x);
        ob.add("y", y);
        ob.add("w", w);
        ob.add("h", h);
        ob.add("dead", false);
        ob.add("currentImage", "tube.png");
        return ob;
    }

    // creates a tube using a Json object of a file
    Tube(Json ob, Model m)
    {
        type = ob.getString("type");
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
        w = (int)ob.getLong("w");
        h = (int)ob.getLong("h");
        dead = ob.getBool("dead");
        currentImage = loadImage(ob.getString("currentImage"));
        model = m;
    }

}
