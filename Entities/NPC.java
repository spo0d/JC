package Entities;

import Engine.Input;
import Assets.AA1S1;

import java.awt.image.BufferedImage;
public class NPC extends Entity{
    Input input;
    public NPC(String name, int x,int y, BufferedImage spritestand, BufferedImage spritemove[] ){
        this.name=name;
        this.x=x;
        this.y=y;
        sizex=100;
        sizey=100;
        this.spritestand=spritestand;
        this.spritemove = spritemove;
        sprite=spritestand;
    }
    @Override
    public void update(){
        if(this.npcmove){
            
            if(xy)x+=(int)this.speed;
            else  y+=(int)this.speed;
        }
    }
}