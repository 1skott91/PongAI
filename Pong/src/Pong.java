import java.awt.*;

import javax.swing.*;

public class Pong extends JFrame
{
	static final int WIDTH = 640;
	static final int HEIGHT = 400;
	static Panel panel;
	
	public Pong()
	{
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        panel = new Panel(this);
        add(panel);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        pack();
        setVisible(true);
	}
	
	public void gameOver() throws InterruptedException
	{
		Thread.sleep(2000);
		
        panel.ball.location.x = WIDTH / 2 - panel.ball.DIAMETER / 2;
        panel.ball.location.y = HEIGHT / 2 - panel.ball.DIAMETER /2;
        
        panel.p1.location.x = 10;
        panel.p1.location.y = Pong.HEIGHT / 2 - Paddle.HEIGHT / 2;
        panel.p2.location.x = Pong.WIDTH - 10 - Paddle.WIDTH;
        panel.p2.location.y = Pong.HEIGHT / 2 - Paddle.HEIGHT / 2;
        
        panel.p1Score = 0;
        panel.p2Score = 0;
		
	}
	
    public Panel getPanel() 
    {
        return panel;
    }
	
    public static void main(String[] args) throws InterruptedException {
      new Pong();
      
//      for (int i = 0; i < 10; i ++)
//      {
//    	  Pong.panel.update();
//      }
//      Vector e = Pong.panel.ballPosition.get(Pong.panel.ballPosition.size() - 1);
//	  Vector e1 = Pong.panel.ballPosition.get(Pong.panel.ballPosition.size() - 2);
//	  System.out.println(Pong.panel.ballPosition.toString());
//	  System.out.println(e + "/" + e1);
    }
	
}
