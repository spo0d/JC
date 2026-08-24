package Main;

import Engine.Game;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Toolkit;

public class MainClass{
    public static void main(String args[]){
        JFrame frame = new JFrame();
        frame.setTitle("Julius Caesar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
        
        frame.setSize(s.width - 20, s.height - 60);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        
        Game game = new Game();
        frame.add(game);
        frame.setLocationRelativeTo(null);
        
        frame.setVisible(true);        
        
    }
}
