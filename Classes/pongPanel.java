import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class pongPanel extends JPanel implements ActionListener, KeyListener
{
	private Pong game;
	private pongAi ai;
	private pongBall ball;
    
	
   //create panel for pong
	 public pongPanel(Pong game)
	 {
        setBackground(Color.BLACK);
        this.game = game;
        setFocusable(true);
        ai = new pongAi(game, 70, 20, 770, 200);
        ball = new pongBall(game, 390, 220);
        
        Timer timer = new Timer(1000/60, this);
        timer.start();
        
        addKeyListener(this);
	 }

	 
	 public void update()
	 {
		ball.update();
		ai.update();
	 }
	 
	 
	public void actionPerformed(ActionEvent e)
	{
		update();
        repaint();
	}
	
	public void keyTyped(KeyEvent e) {}
	
	public void keyPressed(KeyEvent e) 
	{
        ai.keyPressed(e);
    }

    public void keyReleased(KeyEvent e) 
    {
        ai.keyReleased(e);
    }
	
	 public pongAi getRacket()
	 {
		 return ai;
	 }
	 
	 @Override
	 public void paintComponent(Graphics g) 
	 {
        super.paintComponent(g);
        ai.paint(g);
        ball.paint(g);
	 }

}