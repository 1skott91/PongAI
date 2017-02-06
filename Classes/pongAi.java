import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;


public class pongAi
{
	private int racketHeight;
	private int racketWidth;
	private int x, y;
	private Pong game;
	
    private boolean upPressed = false;
    private boolean downPressed = false;
    private int paddleSpeed = 5;
	
	public pongAi(Pong game, int racketHeight, int racketWidth, int x, int y)
	{
		this.game = game;
		this.racketHeight = racketHeight;
		this.racketWidth = racketWidth;
		this.x = x;
        this.y = y;
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
		return racketHeight;
	}

    public void paint(Graphics g) 
    {
        g.fillRect(x, y, racketWidth, racketHeight);
    }
    
    public void update()
    {
    	 if (upPressed) {
             if (y - paddleSpeed > 0) 
             {
                 y -= paddleSpeed;
             }
         }
    	 
         if (downPressed) 
         {
             if (y + paddleSpeed + racketHeight < game.getHeight() - 25) 
             {
                 y += paddleSpeed;
             }
         }

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
}
