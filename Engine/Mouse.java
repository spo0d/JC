package Engine;

import java.awt.event.*;

public class Mouse implements MouseListener, MouseMotionListener {

    public int x;
    public int y;
    public boolean pressed;
    public boolean clicked;
    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;
    }
    @Override public void mouseClicked(MouseEvent e) {
        clicked = true;
    }
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}