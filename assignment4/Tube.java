import java.awt.Graphics;

class Tube extends Sprite
{
    Model model;

    Tube(int x, int y, Model m)
    {
        type = "tube";
        this.x = x;
        this.y = y;
        w = 55;
        h = 400;
        currentImage = loadImage("tube.png");
        model = m;
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
