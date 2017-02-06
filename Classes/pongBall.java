import java.awt.Color;
import java.awt.Graphics;

public class pongBall 
{
	private Pong game;
	
	//ball
	private int x;
	private int y;
	private int ballDiameter = 20;
	private int ballDeltaX = -4;
    private int ballDeltaY = 5;
	
    public pongBall(Pong game, int x, int y) 
    {
        this.game = game;
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics g) 
    {
    	g.setColor(Color.WHITE);
        g.fillOval(x, y, ballDiameter, ballDiameter);
        
    }
    
	public void update()
	{
		checkCollision();
		
        //repaint panel to update everything that moved
        game.getPanel().repaint();
	}
	
	public void checkCollision()
	{
		
		//ball movement - where will it go
        int BallLeft = x + ballDeltaX;
        int BallRight = x + ballDiameter + ballDeltaX;
        int BallTop = y + ballDeltaY;
        int BallBottom = y + ballDiameter + ballDeltaY;
        
        int paddleLeft = game.getPanel().getRacket().getX();
        int paddleTop = game.getPanel().getRacket().getY();
        int paddleBottom = paddleTop + game.getPanel().getRacket().getHeight();


        //bottom and top collision
        if (BallTop < 0 || BallBottom > game.getHeight() - 25)
        {
            ballDeltaY *= -1;
        }

        // left side collision
        if (BallLeft < 0) 
        { 
            ballDeltaX *= -1;
        }

        //right side collision
        if (BallRight > paddleLeft) 
        {
            //if ball misses the paddle
            if (BallTop > paddleBottom || BallBottom < paddleTop) 
            {

                x = 390;
                y = 220;
            }
            else {
                ballDeltaX *= -1;
            }
        }

        //move the ball
        x += ballDeltaX;
        y += ballDeltaY;
	}

}
