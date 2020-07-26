import java.util.ArrayList;
import java.util.Iterator;


class Model
{
	ArrayList<Sprite> sprites;
	Mario mario;
	View view;

// temporary hack to fix mouse and image alignment
int fix_y = -26;


	// varibals to track AI movement
	double jumpCount;
	double fireballsThrown;
	double deadGoombas;

	// actions mario can perform
	final public static int run = 0;
	final public static int run_and_jump = 1;
	final public static int run_and_shoot = 2;


	Model()
	{
		mario = new Mario(this);
		sprites = new ArrayList<Sprite>();
		sprites.add(mario);
	}

	Model(Model original)
	{
		this.sprites = new ArrayList<Sprite>();

		for(int i = 0; i < original.sprites.size(); i++)
		{
			Sprite s = original.sprites.get(i);
			Sprite c = s.makeClone();
			c.setModel(this);
			this.sprites.add(c);
			if(c.isMario())
			{
				this.mario = (Mario)c;
			}
		}
	}

	public void setView(View v)
	{
		view = v;
	}

	int cameraPosition()
	{
		return mario.x - 200;
	}

	public void update()
	{
		// for(Iterator<Sprite> it = sprites.iterator(); it.hasNext(); )
		for(int i = 0; i < sprites.size(); i++)
		{
			// Sprite s = it.next();
			Sprite s = sprites.get(i);
			s.update();

			if(s.isSpriteDead())
			{
				if(s.isGoomba())
				{
					deadGoombas++;
				}
				sprites.remove(i);
			}

		}

	}


	double evaluateAction(int action, int depth) {
		int d = 5;
		int k = 2;
		// Evaluate the state
		if(depth >= d)
		{
			return mario.x + 20 * deadGoombas - fireballsThrown - jumpCount;
		}

		// Simulate the action
		Model copy = new Model(this); // uses the copy constructor  
		copy.doAction(action);
		copy.update(); // advance simulated time

		// Recurse
		if(depth % k != 0)
		{
			return copy.evaluateAction(run, depth + 1);
		}
		else
		{
		   	double best = copy.evaluateAction(run, depth + 1);
		   	best = Math.max(best,
				copy.evaluateAction(run_and_jump, depth + 1));
		   	best = Math.max(best,
			   	copy.evaluateAction(run_and_shoot, depth + 1));
		   	return best;
		}
	}

	void doAction(int action)
	{
		if (action == run_and_jump)
		{
			mario.jump();
			mario.moveMario(12);
			jumpCount++;

		}
		else if (action == run_and_shoot)
		{
			mario.moveMario(12);
			addFireBallSprite();
			fireballsThrown++;

		}
		else
		{
			mario.moveMario(12);
		}
	}


	void rememberState()
	{
		for(Iterator<Sprite> it = sprites.iterator(); it.hasNext(); )
		{
			Sprite s = it.next();
			s.rememberState();
		}

	}



	// adds a tube to the ArrayList
	public void addTubeSprite(int x, int y)
	{
        sprites.add(new Tube(x - 200 + mario.x, y + fix_y, this)); // scrollPosition edits the x value where the tube is stored
	}


	// performs operations on the ArrayList of sprites
	// when the mouse is clicked
	public void editTube(int mouse_x, int mouse_y)
	{
		boolean clickedOnSprite = false;

		// checks all sprites within ArrayList to see if a tube was cliked on
		for(int i = 0; i < sprites.size(); i++)
		{
			Sprite s = sprites.get(i); // gets i tube
			if(s.isThereASprite(mouse_x - 200 + mario.x, mouse_y)) //checks if a tube exists where clicked, with the adjustment of scroll position
			{
				sprites.remove(i); //removes tube from ArrayList
				clickedOnSprite = true; //sets clickedOnTube to true
			}
		}

		if(clickedOnSprite == false) // adds a new tube if a tube was not clicked on
		{
			addTubeSprite(mouse_x, mouse_y);
		}
	}


	//creates a Json object based on the model class
	Json modelToJson()
	{
		Json jsonModel = Json.newObject();
		Json jsonSprites = Json.newList();
		jsonModel.add("sprites", jsonSprites);

		for(int i = 0; i < sprites.size(); i++)
		{
			jsonSprites.add(sprites.get(i).createJsonObject());
		}

		return jsonModel;
	}


	//creates a model object based on a Json object
	void jsonToModel(Json obj)
	{
		sprites = new ArrayList<Sprite>();
		Json jsonList = obj.get("sprites");
		for(int i = 0; i < jsonList.size(); i++)
		{
			Sprite s = createSpriteFromJson(jsonList.get(i), this);
			sprites.add(s);
		}
	}


	Sprite createSpriteFromJson(Json ob, Model m)
    {
        if(ob.getString("type").equals("mario"))
        {
			mario = new Mario(ob, m);
            return mario;
        }
		else if (ob.getString("type").equals("tube"))
        {
            return new Tube(ob, m);
        }
		else if(ob.getString("type").equals("goomba"))
		{
			return new Goomba(ob, m);
		}

		return null;
    }

	//saves a Json object to a file
	public void saveToFile()
	{
		Json obj = modelToJson();
		obj.save("map.json");
	}

	//loads a Json object from a file
	public void loadFromFile()
	{
		Json obj = Json.load("map.json");
		jsonToModel(obj);

	}


	public void addFireBallSprite()
	{
		int fireBallCount = 0;
		for(int i = 0; i < sprites.size(); i++ )
		{
			Sprite s = sprites.get(i);
			if(s.getType() == "fireBall")
			{
				fireBallCount++;
			}
		}

		if(fireBallCount < 2)
		{

			sprites.add(new Fireball(mario.x + mario.w, mario.y + fix_y, this));

		}
	}


	public void editGoomba(int mouseX, int mouseY)
	{
		boolean clickedOnGoomba = false;

		// checks all sprites within ArrayList to see if a goomba was cliked on
		for(int i = 0; i < sprites.size(); i++)
		{
			Sprite s = sprites.get(i); // gets i tube
			if((s.isThereASprite(mouseX - 200 + mario.x, mouseY)) && s.getType() == "goomba") //checks if a goomba exists where clicked, with the adjustment of scroll position
			{
				sprites.remove(i); //removes Goomba from ArrayList
				clickedOnGoomba = true; //sets clickedOnGoomba to true
			}
		}

		if(clickedOnGoomba == false) // adds a new goomba if a goomba was not clicked on
		{
			addGoombaSprite(mouseX, mouseY);
		}

	}

	public void addGoombaSprite(int x, int y)
	{
		sprites.add(new Goomba(x - 200 + mario.x, y + fix_y, this)); // scrollPosition edits the x value where the goomba is stored
	}
}
