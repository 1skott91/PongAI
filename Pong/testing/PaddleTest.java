import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.junit.Test;

public class PaddleTest {

	Pong game = new Pong();

	@Test
	public void paddleTest() throws InterruptedException
	{
		Paddle paddle = new Paddle(10, 70);
		
		assertNotNull(paddle);

	}
	
	@Test public void paddleMovementtest() throws InterruptedException, AWTException
	{
		//check coordinates of x and y before adding velocity
		Vector location = game.panel.p1.location;

		int paddleBeforeX = game.panel.p1.location.x;
		int paddleBeforeY  = game.panel.p1.location.y;
		
		game.panel.p1.playerControls();
		
		Vector velocity = game.panel.p1.velocity;
		location = location.add(velocity);
		
		System.out.println(paddleBeforeX + "/" + paddleBeforeY);
		
		//check coordinates of y again
		int paddleAfterX = game.panel.p1.location.x;
		int paddleAfterY  = game.panel.p1.location.y;
		
		System.out.println(paddleAfterX + "/" + paddleAfterY);
	
		//check if y is equal, if it is not it means the paddle has moved
		assertNotEquals(paddleBeforeY, paddleAfterY);
	}

}
