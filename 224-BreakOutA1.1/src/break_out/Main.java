package break_out;

import break_out.controller.Controller;
import break_out.view.View;

/**
 * The entry point of the program. The game get started here and all components
 * are initialized here.
 * 
 * @author dmlux, modified by I. Schumacher
 * 
 */
public class Main {

	/**
	 * The main method
	 * 
	 * @param args The arguments that were passed by the command line.
	 */
	public static void main(String[] args) {

		// Enable OpenGL for 2D graphics objects. (Linux Performance, but sometimes buggy)
		// -Dsun.java2d.opengl=true as JVM argument seems to be a little bit more stable
		System.setProperty("sun.java2d.opengl", "True");

		// Create the view
		View view = new View();
		// Create the controller and pass the view object to it
		new Controller(view);
	}
}
