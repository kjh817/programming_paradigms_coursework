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
		setFocusable(true);  // Taken from the class forum https://groups.google.com/forum/#!topic/uark-csce-programming-paradigms/j3c_oRMHRJk
		model = new Model();
		controller = new Controller(model);
		view = new View(controller, model);
		this.setTitle("Turtle attack!");
		this.setSize(500, 500);
		this.getContentPane().add(view);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
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

			// Go to sleep for 40 miliseconds
			// also slows the "game loop" down to a rate of
			// 25 loops a second using the "sleep" method
			try
			{
				Thread.sleep(40);
			} catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
			System.out.println("hi");
		}
	}

	// main function
	public static void main(String[] args)
	{
		Game g = new Game(); // creates a new Game object
		g.run(); // calls the run method on the Game object
	}
}
