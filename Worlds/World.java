package Worlds;

import Entities.*;
import java.util.ArrayList;
import java.awt.Graphics2D;
public interface World{
    ArrayList<Entity> entities = new ArrayList<>();
    public void draw(Graphics2D g2);
    public void update();    
}