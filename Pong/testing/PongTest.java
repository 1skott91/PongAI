import static org.junit.Assert.*;

import org.junit.Test;

public class PongTest {

	Pong game = new Pong();

	@Test
	public void gameOverTest() throws InterruptedException
	{
		game.gameOver();

        //get scores
        int p1Score = game.panel.p1Score;
        int p2Score = game.panel.p2Score;
        
        //check if they are reseted
        assertTrue(p1Score == 0);
        assertTrue(p2Score == 0);
        
        
	}
	
	@Test
	public void getPanelTest()
	{
		//get panel object
		Panel panel = game.getPanel();
		
		//check if panel exists
		assertNotNull(panel);
	}

}
