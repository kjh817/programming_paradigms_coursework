import javax.swing.JFrame;

public class Game extends JFrame
{
	//creation of "global variables"
	Model model;
	Controller controller;
	View view;

	// game constructor
	public Game()
	{
		// Init Member variables
		model = new Model();				// creates new model object
		controller = new Controller(model); // creates new controller object
		view = new View(controller, model); // creates new view object
		controller.setView(view); 			//sets view in the controller
		model.setView(view); 				//sets view in the model

		// Configure window
		this.setTitle("Mario");				// sets title of window
		this.setSize(900, 700);				// sets windwo size
		this.getContentPane().add(view);	// adds the view to content to be displayed
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // terminates the program on exit
		this.setFocusable(true);    		// allows JPanel to be a focasable object
		this.setVisible(true); 				// Makes the window visable
		this.setResizable(false);			// Makes the windo non-resizable

		// hook up input
		this.addMouseListener(controller); 	// adds MouseListener under Controller.java control
		this.addKeyListener(controller); 	// adds KeyListener under Controller.java control


	}


	// Game method that will run until the program is closed
	public void run()
	{
		// infinite "game loop"
		while(true)
		{
			controller.update(); // updates controller variable
			model.update(); //updates model variable
			view.repaint(); // Indirectly calls View.paintComponent

			// Go to sleep for 40 milliseconds
			// also slows the "game loop" down to a rate of
			// 25 loops a second using the "sleep" method
			try
			{
				Thread.sleep(100);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
//System.out.println("hi");
		}
	}

	// setter for Game.model
	void setModel(Model m)
	{
		model = m;
	}


	// main function
	public static void main(String[] args)
	{
		Game g = new Game();	 // creates a new Game object
		g.run(); 				// calls the run method on the Game object
	}
}
