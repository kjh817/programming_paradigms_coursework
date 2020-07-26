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
	boolean editGoomba;
	boolean space;
	boolean save;
	boolean load;
	int jumpQueue = 0;
	boolean fireBall;
	int fireBallQueue = 0;
	boolean gameLoaded = false;

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
		// if(e.getButton() == MouseEvent.BUTTON3)
		// {
		// 	model.editGoomba(e.getX(), e.getY());
		// }
		// else if(e.getButton() == MouseEvent.BUTTON1)
		// {
		// 	model.editTube(e.getX(), e.getY());
		// }
		// if(editGoomba)
		// {
		// 	model.editGoomba(e.getX(), e.getY());
		// }
		// else
		// {
		// 	model.editTube(e.getX(), e.getY());
		// }
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
			case KeyEvent.VK_SPACE: space = true; jumpQueue++; break;
			case KeyEvent.VK_G: editGoomba = true; break;
			case KeyEvent.VK_CONTROL: fireBall = true; fireBallQueue++; break;
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
			case KeyEvent.VK_G: editGoomba = false; break;
			case KeyEvent.VK_CONTROL: fireBall = false; break;
		}
	}

	// keyListener function that has not been implemented yet
	public void keyTyped(KeyEvent e)
	{
	}

	// controller method that updates the destination the turtle is to travel to
	// based on which key is pressed or released
	// void update()
	// {
	// 	model.rememberState();
	//
	// 	if(!gameLoaded)
	// 	{
	// 		model.loadFromFile();
	// 		gameLoaded = !gameLoaded;
	// 	}
	//
	// 	if(keyRight)
	// 	{
	// 		model.mario.moveMario(12);
	// 	}
	//
	// 	if(keyLeft)
	// 	{
	// 		model.mario.moveMario(-12);
	// 	}
	//
	//
	// 	if(save)
	// 	{
	// 		model.modelToJson().save("map.json");
	// 		System.out.println("Map saved!");
	// 	}
	//
	// 	if(load)
	// 	{
	// 		model.loadFromFile();
	// 		System.out.println("Map loaded!");
	// 	}
	//
	// 	if(space || jumpQueue > 0)
	// 	{
	// 		model.mario.jump();
	// 		jumpQueue = 0;
	// 	}
	//
	// 	if(fireBall || fireBallQueue > 0)
	// 	{
	// 		model.addFireBallSprite();
	// 		fireBallQueue = 0;
	// 	}
	// }

	void update()
	{
		if(!gameLoaded)
		{
			model.loadFromFile();
			gameLoaded = !gameLoaded;
		}

		// Evaluate each possible action
		double score_run = model.evaluateAction(model.run, 0);
		double score_run_and_jump = model.evaluateAction(model.run_and_jump, 0);
		double score_run_and_shoot = model.evaluateAction(model.run_and_shoot, 0);

		// Do the best one
		if(score_run_and_shoot > score_run_and_jump && score_run_and_shoot > score_run)
			model.doAction(model.run_and_shoot);
		else if(score_run_and_jump > score_run)
			model.doAction(model.run_and_jump);
		else
			model.doAction(model.run);
	}
}
