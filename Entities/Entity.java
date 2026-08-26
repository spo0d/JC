package Entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Entity {
    
    public String name;
    
    
    public int x;
    public int y;
    public int gravity;
    public int sizex;
    public int sizey;
    public int speed;
    public int jumpspeed;
    public boolean jcheck;
    public int yold;
    
    public int target;
    public double targetspeed;
    public boolean npcmove;
    public boolean xy;
    
    public BufferedImage sprite;
    public BufferedImage spritestand;
    public BufferedImage spritemove[] = new BufferedImage[2];
    public BufferedImage spritesprint[] = new BufferedImage[2];
    public BufferedImage spritejump;
    public BufferedImage spriteattack[] = new BufferedImage[2];
    
    protected boolean walk=false;
    public void draw(Graphics2D g2) {
        g2.drawImage(sprite, x, y,sizex,sizey, null);
    }
    public void update() {
    }
    
    public void moveTo(int target, boolean xy, int timemillis){
        this.target = target;
        this.xy=xy;
        if(xy){
            this.speed=(1000*(target-x))/(16*timemillis);
            if(walk)this.sprite=this.spritemove[0];
            else this.sprite=this.spritemove[1];
            walk=!walk;
            }
        
        else{
            this.speed=(1000*(target-y))/(16*timemillis);
            this.sprite=this.spritejump;
        }
        this.npcmove=true;
    }

    

}