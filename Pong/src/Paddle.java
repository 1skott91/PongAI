import java.awt.AWTException;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Paddle
{
    static final int WIDTH = 10;
    static final int HEIGHT = 70;
    
    Vector location;
    Vector velocity;
    
    boolean upPressed = false;
    boolean downPressed = false;
    
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
	
	public void algorithm() throws AWTException
	{
			  Robot r = new Robot();
	    	
			  //getting last two positions from the array
		      Vector e1 = Pong.panel.ballPositionOld.get(Pong.panel.ballPositionOld.size() - 1);
			  Vector e2 = Pong.panel.ballPositionOld.get(Pong.panel.ballPositionOld.size() - 2);
			  
			  int xDiff = e2.x - e1.x;
			  int yDiff = e2.y - e1.y;
			  
			  //direction?
			  double x2 = Math.sqrt(xDiff);
			  double y2 = Math.sqrt(yDiff);
			  double result = x2 + y2;
			  
			  //angle
			  int xDiff1 = e2.x - e1.x;
			  int yDiff1 = e2.y - e1.y;
			  double result1 =  Math.atan2(yDiff1, xDiff1) * 180.0 / Math.PI;
	
			  //System.out.println(e2 + "/" + e1);
			 //System.out.println(result1);
			  
			  if (result1 > 0)
			  {
//				r.keyRelease(KeyEvent.VK_DOWN);
//				r.keyPress(KeyEvent.VK_UP);
				downPressed = false;
				upPressed = true;
			  }
			  else
			  {
//				r.keyRelease(KeyEvent.VK_UP);
//				r.keyPress(KeyEvent.VK_DOWN);
				downPressed = true;
				upPressed = false;
			  }
	}
    
    
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
