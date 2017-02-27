import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ball
{
	Pong game;
	
	final int DIAMETER = 20;
	private int ballDeltaX = -4;
    private int ballDeltaY = 5;
	
	int x = Pong.WIDTH / 2 - DIAMETER / 2;
	int y = Pong.HEIGHT / 2 - DIAMETER / 2;
	
	public Ball(Pong game)
	{
		this.game = game;
	}

	
	public void checkCollision() throws InterruptedException
	{
		//ball movement - where will it go
        int BallLeft = x + ballDeltaX;
        int BallRight = x + DIAMETER + ballDeltaX;
        int BallTop = y + ballDeltaY;
        int BallBottom = y + DIAMETER + ballDeltaY;
        
        //right paddle
        int paddleX = game.panel.p2.x;
        int paddleY = game.panel.p2.y;
        int paddleBottom = paddleY + game.panel.p2.HEIGHT;
        
        //left paddle
        int paddle2X = game.panel.p1.x;
        int paddle2Xwidth = game.panel.p1.x + game.panel.p1.WIDTH;
        int paddle2Y = game.panel.p1.y;
        int paddle2Bottom = paddle2Y + game.panel.p1.HEIGHT;
        		
        //bottom and top collision
        if (BallTop < 0 || BallBottom > game.HEIGHT)
        {
            ballDeltaY *= -1;
        }

        // left side collision
        if (BallLeft < paddle2Xwidth) 
        { 
        	if (BallTop > paddle2Bottom || BallBottom < paddle2Y)
        	{
        		resetBall();
            	game.panel.p2Score ++;
            	
            	if (game.panel.p2Score > 5)
            	{
            		game.gameOver();
            	}
        	}
        	else
        	{
        		// invert x speed
            	ballDeltaX = -ballDeltaX;
            	
            	//get center of the paddle
            	int center = paddle2Y + (game.panel.p1.HEIGHT/2);
            	
            	//set y speed
            	ballDeltaY = ballDeltaY + (y - center) / 6;
        	}
        }

        //right side collision
        if (BallRight > paddleX) 
        {
            //if ball misses the paddle
            if (BallTop > paddleBottom || BallBottom < paddleY) 
            {
            	resetBall();
            	game.panel.p1Score ++;
            	
            	if (game.panel.p1Score > 5)
            	{
            		game.gameOver();
            	}
            }
            else 
            {
            	// invert x speed
            	ballDeltaX = -ballDeltaX;
            	
            	//get center of the paddle
            	int center = paddleY + (game.panel.p2.HEIGHT/2);
            	
            	//set y speed
            	ballDeltaY = ballDeltaY + (y - center) / 6;

            }
        }

        //move the ball
        x += ballDeltaX;
        y += ballDeltaY;
        
	
	}
	
	public void resetBall()
	{
		ballDeltaX = -4;
	    ballDeltaY = 5;
		
		x = Pong.WIDTH / 2 - DIAMETER / 2;
		y = Pong.HEIGHT / 2 - DIAMETER / 2;
	}
	
	
	
    public Rectangle getBounds() 
    {
        return new Rectangle(x, y, DIAMETER, DIAMETER);
    }
    
    public void paint(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, DIAMETER, DIAMETER);
    }
}
