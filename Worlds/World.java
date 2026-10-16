package Worlds;

import Entities.*;
import java.util.ArrayList;
import java.awt.Graphics2D;
public interface World{
    public void draw(Graphics2D g2);
    public void update();
    public ArrayList<Entity> getEntities();     
    
    //save stuff
    
    default short getSceneID(){return -67;};
    default SaveLoad.WorldData saveWorld(){ return null;}
    default void  loadWorld(SaveLoad.WorldData d){};
}
    