package Entities;
import java.awt.image.BufferedImage;
public class Objects extends Entity{
    BufferedImage spriteanimate[];
    public int asize;
    int count=0;
    public Objects(BufferedImage inputanimate[], int x, int y, int sizex, int sizey){
        spriteanimate = inputanimate;
        asize=inputanimate.length;
        this.x=x;
        this.y=y;
        this.sizex=sizex;
        this.sizey=sizey;        
    }
    public void update(){
        sprite=spriteanimate[count/4];
        count=(count+1)%(4*asize);
    }
    public boolean touchRange(Entity e){
        if(Math.abs(e.x-this.x)<=e.sizex  && Math.abs(e.y-this.y)<=e.sizey)return true;
        return false;
    }
}