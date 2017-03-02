import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Ball
{
	Pong game;
	
	final int DIAMETER = 20;

    Vector location = new Vector (Pong.WIDTH / 2 - DIAMETER / 2, Pong.HEIGHT / 2 - DIAMETER / 2, 0 );
	Vector velocity = new Vector (-4, 5, 0);
	

	
	public Ball(Pong game)
	{
		this.game = game;
	}

	
	public void checkCollision() throws InterruptedException
	{
		//ball movement - where will it go
        int BallLeft = location.x + velocity.x;
        int BallRight = location.x + DIAMETER + velocity.x;
        int BallTop = location.y + velocity.y;
        int BallBottom = location.y + DIAMETER + velocity.y;
        
        //right paddle
        int paddleX = game.panel.p2.location.x;
        int paddleY = game.panel.p2.location.y;
        int paddleBottom = paddleY + game.panel.p2.HEIGHT;
        
        //left paddle
        int paddle2X = game.panel.p1.location.x;
        int paddle2Xwidth = game.panel.p1.location.x + game.panel.p1.WIDTH;
        int paddle2Y = game.panel.p1.location.y;
        int paddle2Bottom = paddle2Y + game.panel.p1.HEIGHT;
        		
        //bottom and top collision
        if (BallTop < 0 || BallBottom > game.HEIGHT)
        {
            velocity.y *= -1;
        }

        // left side collision
        if (BallLeft < paddle2Xwidth) 
        { 
        	if (BallTop > paddle2Bottom || BallBottom < paddle2Y)
        	{
        		resetBall();
            	game.panel.p2Score ++;
            	
            	if (game.panel.p2Score > 10)
            	{
            		game.gameOver();
            	}
        	}
        	else
        	{
        		// invert x speed
            	velocity.x = -velocity.x;
            	
            	//get center of the paddle
            	int center = paddle2Y + (game.panel.p1.HEIGHT/2);
            	
            	//set y speed
            	velocity.y = velocity.y + (location.y - center) / 6;
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
            	
            	if (game.panel.p1Score > 10)
            	{
            		game.gameOver();
            	}
            }
            else 
            {
            	// invert x speed
            	velocity.x = -velocity.x;
            	
            	//get center of the paddle
            	int center = paddleY + (game.panel.p2.HEIGHT/2);
            	
            	//set y speed
            	velocity.y = velocity.y + (location.y - center) / 6;

            }
        }

        //move the ball
        location.add(velocity);
        
	
	}
	
	public void resetBall()
	{
		velocity.x = -4;
	    velocity.y = 5;
		
		location.x = Pong.WIDTH / 2 - DIAMETER / 2;
		location.y = Pong.HEIGHT / 2 - DIAMETER / 2;
	}
	
	
	
    public Rectangle getBounds() 
    {
        return new Rectangle(location.x, location.y, DIAMETER, DIAMETER);
    }
    
    public void paint(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillOval(location.x, location.y, DIAMETER, DIAMETER);
    }
}
