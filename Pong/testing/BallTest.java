import static org.junit.Assert.*;

import org.junit.Test;

public class BallTest 
{
	Pong game = new Pong();

	@Test
	public void ballTest() throws InterruptedException
	{
		Ball ball = new Ball(game);
		
		assertNotNull(ball);

	}
	
	@Test public void ballMovementtest() throws InterruptedException
	{
		//check coordinates of x and y before update() is called
		int ballBeforeX = game.panel.ball.location.x;
		int ballBeforeY  = game.panel.ball.location.y;
		
		//call update function
		game.panel.ball.update();
		
		//check coordinates of x and y after update() is called
		int ballAfterX = game.panel.ball.location.x;
		int ballAfterY = game.panel.ball.location.y;
	
		//check if x and y are equal, if they are not it means the ball has moved
		assertNotEquals(ballBeforeX, ballAfterX);
		assertNotEquals(ballBeforeY, ballAfterY);
	}
	

}
