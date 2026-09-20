package Engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener
{   
    public boolean switcher;
    public int[] tdnombre = new int[2];
    public int wsi=0;
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
            case KeyEvent.VK_ESCAPE:
                switcher=true;          
                break;
        }
    }
    @Override
    public void keyTyped(KeyEvent e){
        if(switcher&&wsi<2){
            if(Character.isDigit(e.getKeyChar())){
               tdnombre[wsi]=e.getKeyChar()-48;
               wsi++;
            }
        }
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
