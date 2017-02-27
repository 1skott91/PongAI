import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class Panel extends JPanel implements ActionListener, KeyListener
{
	final Pong game;
	
	Paddle p1;
	Paddle p2;
	Ball ball;
	
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
    
    public void update() throws InterruptedException
    {
    	p2.playerControls();
    	p1.botAi();
    	ball.checkCollision();
    }

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		try {
			update();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
