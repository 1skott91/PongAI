package cv;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class predictionPanel extends JFrame
{
	private JPanel panel;
	Vector pos;
	
	 public predictionPanel() 
	 {
		 setPreferredSize(new Dimension(656, 439));
		 setVisible(true);
		 setResizable(false);
		 pack();
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
		 panel = new JPanel();
		 panel.setPreferredSize(new Dimension(640, 400));
		 panel.setBackground(Color.BLACK);
		 add(panel);
		 setAlwaysOnTop(true);
	 }

    @Override
    public void paint(Graphics g) 
    {
    	Graphics2D g2d = (Graphics2D)g;
    	super.paint(g2d);    	
    	

        if (Processing.predictionPos.size() == 2)
        {
	    	Vector previousPos = Processing.ballPos.get(Processing.ballPos.size() - 1);
	    	Vector currentPred = Processing.predictionPos.get(Processing.predictionPos.size() - 1);
	    	
	    	int prevPosX = previousPos.x;
	    	int prevPosY = previousPos.y;
	    	
	    	// define the position
	        int posX = currentPred.x;
	        int posY = currentPred.y;
	        
	        g2d.setColor(new Color(255,255,255));
        	g2d.drawLine(prevPosX, prevPosY, posX, posY);

        }
    	repaint();

        
//    	g2d.setColor(new Color(255,255,255));
//    	g2d.fillRect(posX, posY, 10, 10);
    	
    }
	
}
