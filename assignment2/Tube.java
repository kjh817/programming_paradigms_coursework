class Tube
{
    int x;
    int y;
    final int width = 55;
    final int height = 400;


    Tube(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    // checks to see if a tube is clicked on
    boolean isThereATube(int mouse_x, int mouse_y)
    {
        // checks for a click outside of the tube's width
        // and returns false if the click is outside tube
        // dimensions. Othewise the function returns true.
        if(mouse_x < this.x)
        {
            return false;
        }else if(mouse_x > this.x + width)
        {
            return false;
        }else if(mouse_y < this.y)
        {
            return false;
        }else if (mouse_y > this.y + height)
        {
            return false;
        }

        return true;
    }

    // creates a Json object of tubes
    Json tubeToJson()
    {
        Json ob = Json.newObject();
        ob.add("x", x);
        ob.add("y", y);
        return ob;
    }

    Tube(Json ob)
    {
        x = (int)ob.getLong("x");
        y = (int)ob.getLong("y");
    }

}
