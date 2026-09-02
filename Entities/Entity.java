package Entities;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.geom.*;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.Color;
public class Entity {
    
    public String name;

    public boolean lor=true;
    
    public int x;
    public int y;
    public int gravity;
    public int sizex;
    public int sizey;
    public int speed;
    public int speedTop;
    public int speedBottom;
    public int jumpspeed;
    public boolean jcheck;
    public int yold;
    
    public int target;
    public int targetDirection;
    public boolean npcmove;
    public boolean xy;
    
    public BufferedImage sprite;
    public BufferedImage spritestand[] = new BufferedImage[2];
    public BufferedImage spritestandNPC;
    public BufferedImage spritemove[] = new BufferedImage[4];
    public BufferedImage spritesprint[] = new BufferedImage[4];
    public BufferedImage spritejump;
    public BufferedImage spriteattack[] = new BufferedImage[2];
    protected boolean walk=false;
    public final static Font caesarDressingFont = caesarDressingLoad();
    public static Font caesarDressingLoad(){
        try{
            Font dummyFont = Font.createFont(Font.TRUETYPE_FONT, new File("assetsfile/fonts/CaesarDressing-Regular.ttf"));
            return dummyFont.deriveFont(Font.PLAIN,15);
        }
        catch(Exception e){
            return new Font("SansSerif", Font.PLAIN, 15);
        }
    }
    public void draw(Graphics2D g2) { 
        if(lor)g2.drawImage(sprite, x, y,sizex,sizey, null);
        else g2.drawImage(sprite, x+sizex, y,-1*sizex,sizey, null);
        //
        g2.setFont(caesarDressingFont);
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(this.name);
        int textHeight = fm.getHeight();
        int dummy = (2*this.x + this.sizex - textWidth)/2;
        
        //draw bg
        g2.setColor(new Color(0,0,0, 175));
        RoundRectangle2D roundedRect = new RoundRectangle2D.Double(dummy-5, this.y-textHeight+4,textWidth+10, textHeight+1, 7, 7);
        g2.fill(roundedRect);
        g2.setColor(Color.WHITE);
        g2.draw(roundedRect);
        //draw text
        g2.drawString(this.name,dummy,this.y);

    }
    public void update() {
    }
    
    public void moveTo(int target, boolean xy, int speed){
        this.target = target;
        this.xy=xy;
        //this.speedTop=speedTop;
        //this.speedBottom=speedBottom;
        this.speed=speed;
        this.targetDirection=target-this.x;
        this.npcmove=true;
    }

    

}