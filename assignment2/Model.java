import java.util.ArrayList;


class Model
{
	ArrayList<Tube> tubes;

	// temporary hack to fix mouse and image alligment
	int fix_y = -26;

	View view;

	Model()
	{
		tubes = new ArrayList<Tube>();
	}

	public void setView(View v)
	{
		view = v;
	}

	public void update()
	{
		sortTubes();
	}



	// adds a tube to the ArrayList
	public void addTube(int x, int y)
	{
        tubes.add(new Tube(x + view.scrollPosition, y + fix_y)); // scrollPosition edits the x value where the tube is stored
	}



	// performs operations on the ArrayList of tubes
	// when the mouse is clicked
	public void mouseClick(int mouse_x, int mouse_y)
	{
		boolean clickedOnTube = false;

		// checks all tubes within ArrayList to see if a tube was cliked on
		for(int i = 0; i < tubes.size(); i++)
		{
			Tube t = tubes.get(i); // gets i tube
			if(t.isThereATube(mouse_x + view.scrollPosition, mouse_y)) //checks if a tube exists where clicked, with the adjustment of scroll position
			{
				tubes.remove(i); //removes tube from ArrayList
				clickedOnTube = true; //sets clickedOnTube to true
			}
		}

		if(clickedOnTube == false) // adds a new tube if a tube was not clicked on
		{
			addTube(mouse_x, mouse_y);
		}
	}


	//creates a Json object based on the model class
	Json modelToJson()
	{
		Json jsonModel = Json.newObject();
		Json jsonTubes = Json.newList();
		jsonModel.add("tubes", jsonTubes);

		for(int i = 0; i < tubes.size(); i++)
		{
			jsonTubes.add(tubes.get(i).tubeToJson());
        }

		return jsonModel;
	}

	//creates a model object based on a Json object
	void jsonToModel(Json obj)
	{
		tubes = new ArrayList<Tube>();
		Json jsonList = obj.get("tubes");
		for(int i = 0; i < jsonList.size(); i++)
		{
			tubes.add(new Tube(jsonList.get(i)));
		}
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

	// keeps tubes sorted by tube.x values witin the tube list
	public void sortTubes()
	{
		tubes.sort(new TubeComparator());
	}


	
}
