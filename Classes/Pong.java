import java.awt.Color;

import javax.swing.JFrame;

public class Pong extends JFrame {
    private final static int gameWIDTH = 800, gameHEIGHT = 480;
    private pongPanel panel;

    public Pong()
    {
     	setSize(gameWIDTH, gameHEIGHT);
        setTitle("Pong");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        panel = new pongPanel(this);
        add(panel);
    }

    public pongPanel getPanel() 
    {
        return panel;
    }

    public static void main(String[] args) 
    {
    	Pong game = new Pong(); 
    	
    }
}