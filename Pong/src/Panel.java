import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Panel extends JPanel implements ActionListener, KeyListener
{
	final Pong game;
	
	Paddle p1;
	Paddle p2;
	Ball ball;
	//Current position of the ball
	ArrayList <Vector> ballPosition;
	//ball history position
	ArrayList <Vector> ballPositionOld;
	
	int p1Score = 0;
	int p2Score = 0;
	int step = 0;
	
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
        ballPositionOld = new ArrayList<Vector>();
        
        ballPosition.add(ball.location);
        ballPositionOld.add(ball.getBallOldPosition());
        
	}
	
	public Paddle p1()
	{
		return p1;
	}
	
	public Paddle p2()
	{
		return p2;
	}
	
    
    public void update() throws InterruptedException, AWTException, IOException
    {
    	step++;
    	p1.botAi();
    	ball.update();
    	ballPosition.add(ball.location);
    	ballPositionOld.add(ball.getBallOldPosition());
    	p2.playerControls(); // needed to be able to use algorithm since it uses simulated key presses
    	p2.algorithm();
    	//System.out.println("Step: " + step);
    	
    	if (step > 40)
    	{
    		step = 0;
    	}
    	else if (step == 40)
    	{
    		//System.out.println("30");
    		screenshot();
    		//System.out.println("ss taken");
    	}
//    	else if (step == 60)
//    	{
//    		//System.out.println("60");
//    		screenshot2();
//    		//System.out.println("SS old taken");
//    	}
    	//screenshotEachFrame();
    	
    	//System.out.println("Current" + ballPosition.toString());
    	//System.out.println("Old" + ballPositionOld.toString());
    	
    }
    

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		try 
		{
			update();
			//updateSS();
		} catch (InterruptedException | AWTException | IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		repaint();
	}
	
	public void screenshot() throws IOException
	{
		try
		{
			BufferedImage img = new BufferedImage(game.getWidth(), game.getHeight(), BufferedImage.TYPE_INT_RGB);
			game.paint(img.getGraphics());
			File outputfile = new File("../OpenCV/res/saved.png");
			ImageIO.write(img, "png", outputfile);
		}catch (Exception e1) {  
            // TODO Auto-generated catch block  
            System.out.println("Issue while trying to save image");  
            e1.printStackTrace(); 
		}

	}
	
	public void screenshot2() throws IOException
	{
		try
		{
			BufferedImage img = new BufferedImage(game.getWidth(), game.getHeight(), BufferedImage.TYPE_INT_RGB);
			game.paint(img.getGraphics());
			File outputfile = new File("../OpenCV/res/saved1.png");
			ImageIO.write(img, "png", outputfile);
		}catch (Exception e1) {  
            // TODO Auto-generated catch block  
            System.out.println("Issue while trying to save image");  
            e1.printStackTrace(); 
		}

	}
	
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        super.paint(g);
        // g.setColor(new Color(231, 76, 60));
        g.setColor(new Color(230, 0, 0));
        p1.paint(g2d);
        
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(Pong.WIDTH / 2, 0, Pong.WIDTH / 2, Pong.HEIGHT);
        
        //g.setColor(new Color(52, 152, 219));
        g.setColor(new Color(0, 150, 200));
        p2.paint(g2d);
        
        ball.paint(g2d);
        
        g2d.drawString(String.valueOf(p1Score), 300, 20);
        g2d.drawString(String.valueOf(p2Score), 330, 20);

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
