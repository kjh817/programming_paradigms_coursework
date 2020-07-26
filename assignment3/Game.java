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
		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);
		controller.setView(view); //sets view in the controller
		model.setView(view); //sets view in the model

		// Configure window
		this.setTitle("Mario");
		this.setSize(900, 700);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFocusable(true);  // Taken from the class forum https://groups.google.com/forum/#!topic/uark-csce-programming-paradigms/j3c_oRMHRJk
		this.setVisible(true);
		this.setResizable(false);

		//hook up input
		this.addMouseListener(controller); // adds MouseListener under Controller.java control
		this.addKeyListener(controller); // adds KeyListener under Controller.java control
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
				Thread.sleep(40);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
//			System.out.println("hi");
		}
	}

	void setModel(Model m)
	{
		model = m;
	}

	// main function
	public static void main(String[] args)
	{
		Game g = new Game(); // creates a new Game object
		g.run(); // calls the run method on the Game object
	}
}
