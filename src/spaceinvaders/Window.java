package spaceinvaders;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;

public class Window extends Canvas {
    public static int paneWidth, paneHeight;
    
    public Window(int width, int height, String title,Game game){
        JFrame frame = new JFrame(title);
        
        frame.setPreferredSize(new Dimension (width,height));
        frame.setMaximumSize(new Dimension (width,height));
        frame.setMinimumSize(new Dimension (width,height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        
        Window.paneWidth = frame.getContentPane().getWidth();
        Window.paneHeight = frame.getContentPane().getHeight();
        
        game.start();
    }
}
