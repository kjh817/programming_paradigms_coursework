import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

class Controller implements ActionListener, MouseListener, KeyListener
{

	View view;
	Model model;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	boolean space;
	boolean save;
	boolean load;

	Controller(Model m)
	{
		model = m;
	}

	void setView(View v)
	{
		view = v;
	}

	void setModel(Model m)
	{
		model = m;
	}

	// removes the button from the screen which was created in View.java
	public void actionPerformed(ActionEvent e)
	{

	}

	// creates the destination for the turtle to move to when mouse is clicked
	public void mousePressed(MouseEvent e)
	{
		model.mouseClick(e.getX(), e.getY());
	}

	// various mouseListener functions that do nothing at the moment
	public void mouseReleased(MouseEvent e){	}
	public void mouseEntered(MouseEvent e){		}
	public void mouseExited(MouseEvent e){		}
	public void mouseClicked(MouseEvent e){		}

	// keyListener function that responds to a key being pressed or held
	public void keyPressed(KeyEvent e)
	{
		// switch statement that changes boolean values depending on which key is pressed
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = true; break;
			case KeyEvent.VK_LEFT: keyLeft = true; break;
			case KeyEvent.VK_UP: keyUp = true; break;
			case KeyEvent.VK_DOWN: keyDown = true; break;
			case KeyEvent.VK_S: save = true; break;
			case KeyEvent.VK_L: load = true; break;
			case KeyEvent.VK_SPACE: space = true; break;
		}
	}

	// keyListener function that responds to the release of a key
	public void keyReleased(KeyEvent e)
	{
		// switch statement that changes boolean values depending on which key is pressed
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: keyRight = false; break;
			case KeyEvent.VK_LEFT: keyLeft = false; break;
			case KeyEvent.VK_UP: keyUp = false; break;
			case KeyEvent.VK_DOWN: keyDown = false; break;
			case KeyEvent.VK_S: save = false; break;
			case KeyEvent.VK_L: load = false; break;
			case KeyEvent.VK_SPACE: space = false; break;

		}
	}

	// keyListener function that has not been implemented yet
	public void keyTyped(KeyEvent e)
	{
	}

	// controller method that updates the destination the turtle is to travel to
	// based on which key is pressed or released
	void update()
	{
		model.rememberState();


		if(keyRight)
		{
			model.mario.moveMario(12);
		}

		if(keyLeft)
		{
			model.mario.moveMario(-12);
		}


		if(save)
		{
			model.modelToJson().save("map.json");
			System.out.println("Map saved!");
		}

		if(load)
		{
			model.loadFromFile();
			System.out.println("Map loaded!");
		}

		if(space)
		{
			model.mario.jump();
		}
	}

}
