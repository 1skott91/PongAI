import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.*;

public class Panel extends JPanel implements ActionListener, KeyListener
{
	final Pong game;
	
	Paddle p1;
	Paddle p2;
	Ball ball;
	ArrayList <Vector> ballPosition;
	ArrayList <Vector> ballPositionNew;
	
	int p1Score = 0;
	int p2Score = 0;
	
	public Panel(final Pong game)
	{
		this.game = game;
		
		setBackground(Color.BLACK);
		setFocusable(true);
		
		p1 = new Paddle(10, Pong.HEIGHT / 2 - Paddle.HEIGHT / 2);
        p2 = new Paddle(Pong.WIDTH - 10 - Paddle.WIDTH, Pong.HEIGHT / 2 - Paddle.HEIGHT / 2);
        ball = new Ball(game);

        Timer timer = new Timer(1000/60, this);
        timer.start();
//        p1.start();
//        p2.start();
//        ball.start();
        addKeyListener(this);
        
        ballPosition = new ArrayList<Vector>();
        ballPositionNew = new ArrayList<Vector>();
        
        ballPosition.add(ball.location);
        ballPositionNew.add(ball.getBallOldPosition());
        
	}
	
	public Paddle p1()
	{
		return p1;
	}
	
	public Paddle p2()
	{
		return p2;
	}
	
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        super.paint(g);
        
        g.setColor(new Color(231, 76, 60));
        p1.paint(g2d);
        
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(Pong.WIDTH / 2, 0, Pong.WIDTH / 2, Pong.HEIGHT);
        
        g.setColor(new Color(52, 152, 219));
        p2.paint(g2d);
        
        g.setColor(Color.BLACK);
        ball.paint(g2d);
        
        g2d.drawString(String.valueOf(p1Score), 300, 20);
        g2d.drawString(String.valueOf(p2Score), 330, 20);

    }
    
    public void update() throws InterruptedException, AWTException
    {
    	
    	p1.botAi();
    	ball.update();
    	ballPosition.add(ball.location);
    	ballPositionNew.add(ball.getBallOldPosition());
    	p2.playerControls(); // needed to be able to use algorithm since it uses simulated key presses
    	p2.algorithm();
    	
    }

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		try {
			
			update();
			
			
		} catch (InterruptedException | AWTException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		repaint();
	}
	
	
	public void keyTyped(KeyEvent e) {}
	
	public void keyPressed(KeyEvent e) 
	{
        p2.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) 
    {
        p2.keyReleased(e);
    }
}
