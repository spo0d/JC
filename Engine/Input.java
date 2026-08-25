package Engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener
{   
    public char input;
    public boolean move[] = new boolean[7];
    @Override
    public void keyPressed(KeyEvent e){
        input = e.getKeyChar();
        switch(e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                move[0]=true;
                break;
            case KeyEvent.VK_A:
                move[1]=true;
                break;
            case KeyEvent.VK_SHIFT:
                move[2]=true;
                break;
            case KeyEvent.VK_D:
                move[3]=true;
                break;
            case KeyEvent.VK_ENTER:
                move[4]=true;
                break;
            case KeyEvent.VK_E:
                move[5]=true;
                break;
            case KeyEvent.VK_CONTROL:
                move[6]=true;
                break;
        }
    }
    @Override
    public void keyTyped(KeyEvent e){
        
    }
    @Override
    public void keyReleased(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                move[0]=false;
                break;
            case KeyEvent.VK_A:
                move[1]=false;
                break;
            case KeyEvent.VK_SHIFT:
                move[2]=false;
                break;
            case KeyEvent.VK_D:
                move[3]=false;
                break;
            case KeyEvent.VK_ENTER:
                move[4]=false;
                break;
            case KeyEvent.VK_E:
                move[5]=false;
                break;
            case KeyEvent.VK_CONTROL:
                move[6]=false;
                break;
        }
        input='\0';
        
    }
}
