import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Paddle
{
    static final int WIDTH = 10;
    static final int HEIGHT = 70;
    
    Vector location;
    Vector velocity;
    
    private boolean upPressed = false;
    private boolean downPressed = false;
    
    public Paddle (int startX, int startY)
    {
    	location = new Vector (startX, startY, 0);
    }
    
	public void keyTyped(KeyEvent e) {}
	
	    public void keyPressed(KeyEvent e) 
	    {
	        if (e.getKeyCode() == KeyEvent.VK_UP)
	        {
	            upPressed = true;
	        }
	        else if (e.getKeyCode() == KeyEvent.VK_DOWN) 
	        {
	            downPressed = true;
	        }
	    }
	
	
    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_UP) 
        {
            upPressed = false;
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            downPressed = false;
        }
    }
    
	public void botAi()
	{
		int ballCenter = Pong.panel.ball.location.y + (Pong.panel.ball.DIAMETER/2);
		int paddleCenter = Pong.panel.p1.location.y + (HEIGHT/2);
		int middlePoint = Pong.WIDTH / 2; 
		double paddleSpeed = 4.5;
		
		if (middlePoint > Pong.panel.ball.location.x)
		{
			if (ballCenter < paddleCenter)
			{
				if (Pong.panel.p1.location.y - 5 > 0) 
		           {
						Pong.panel.p1.location.y -= paddleSpeed;
		           }
			}
			if (ballCenter > paddleCenter)
			{
				if (Pong.panel.p1.location.y + 5 + HEIGHT < 400) 
		           {
						Pong.panel.p1.location.y += paddleSpeed;
		           }
			}
		}
	}
	
	public void playerControls()
	{
		
		velocity = new Vector (0, 5, 0);
		
		 if (upPressed) 
		 {
	       if (location.y - 5 > 0) 
	       {
	           location.sub(velocity);
	       }
		 }

		 if (downPressed) 
		 {
			if (location.y + 5 + HEIGHT < 400) 
			{
				location.add(velocity);
			}
		 }
	}
    
//    public Rectangle getBounds() 
//    {
//        return new Rectangle(x, y, WIDTH, HEIGHT);
//    }
    
    public void paint(Graphics2D g) 
    {
        g.fillRect(location.x, location.y, WIDTH, HEIGHT);
    }
    
    public int getX()
    {
    	return location.x;
    }
    
    public int getY()
    {
    	return location.y;
    }
    
    public int getHeight()
    {
    	return HEIGHT;
    }
    
    public int getWidth()
    {
    	return WIDTH;
    }
    
}
