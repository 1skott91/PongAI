import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class pongPanel extends JPanel{

   //create panel for pong
    public pongPanel(){
        setBackground(Color.BLACK);
    }

    //create a ball
    public void paintComponent(Graphics ball){

        super.paintComponent(ball);
        ball.setColor(Color.WHITE);

        ball.fillOval(385, 225, 20, 20);
    }

}