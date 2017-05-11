import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class PanelTest {

	Pong game = new Pong();

	@Test
	public void objectsTest()
	{
		//get objects from panel
		Paddle ai = game.getPanel().p1();
        Paddle player = game.getPanel().p2();
        Ball ball = game.getPanel().ball;
        
        //check if they exist
        assertNotNull(ai);
        assertNotNull(player);
        assertNotNull(ball);
	}
	
	@Test
	public void screenshotTest() throws IOException
	{
		//take screenshot
		game.getPanel().screenshot();
		
		//create file path
		File f = new File("../OpenCV/res/saved.png");
		
		//check if file exists and can be read - it will only be true if the game was run at least once
		assertTrue(f.isFile());
		assertTrue(f.canRead());
	}
	

}
