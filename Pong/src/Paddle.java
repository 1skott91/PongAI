import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Paddle
{
    static final int WIDTH = 10;
    static final int HEIGHT = 70;
    
    
    int x, y;
    
    private boolean upPressed = false;
    private boolean downPressed = false;
    
    public Paddle (int startX, int startY)
    {
    	x = startX;
    	y = startY;
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
		int ballCenter = Pong.panel.ball.y + (Pong.panel.ball.DIAMETER/2);
		int center = Pong.panel.p1.y + (HEIGHT/2);
		int middlePoint = Pong.WIDTH / 2; 
		
		if (middlePoint > Pong.panel.ball.x)
		{
			if (ballCenter < center)
			{
				if (Pong.panel.p1.y - 5 > 0) 
		           {
						Pong.panel.p1.y -= 4.5;
		           }
			}
			if (ballCenter > center)
			{
				if (Pong.panel.p1.y + 5 + HEIGHT < 400) 
		           {
						Pong.panel.p1.y += 4.5;
		           }
			}
		}
	}
	
	public void playerControls()
	{
		 if (upPressed) 
		 {
	       if (y - 5 > 0) 
	       {
	           y -= 5;
	       }
		 }

		 if (downPressed) 
		 {
			if (y + 5 + HEIGHT < 400) 
			{
			y += 5;
			}
		 }
	}
    
    public Rectangle getBounds() 
    {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
    
    public void paint(Graphics2D g) 
    {
        g.fillRect(x, y, WIDTH, HEIGHT);
    }
    
    public int getX()
    {
    	return x;
    }
    
    public int getY()
    {
    	return y;
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
