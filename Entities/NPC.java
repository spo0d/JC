package Entities;

import Engine.Input;
import Assets.AA1S1;

import java.awt.image.BufferedImage;
public class NPC extends Entity{
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
        if(npcmove){
      if(xy){
          x += speed;
          if((speed > 0 && x >= target) || (speed < 0 && x <= target)){
              x = target;      // snap exactly
              npcmove = false;
          }
      } else {
          y += speed;
          if((speed > 0 && y >= target) || (speed < 0 && y <= target)){
              y = target;
              npcmove = false;
          }
       }
     }
    }
    public boolean touchRange(Player player){
        if(Math.abs(player.x-this.x)<=player.sizex  && Math.abs(player.y-this.y)<=player.sizey)return true;
        return false;
    }
}