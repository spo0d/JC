package Entities;

import Engine.Game;
import Engine.Input;
import Assets.AA1S1;

import java.awt.image.BufferedImage;
public class NPC extends Entity{
    public boolean follow;
    private Player player;
    int i = 0;
    public NPC(Player player, String name, int x,int y, BufferedImage spritestand, BufferedImage spritemove[] ){
        this.player=player;
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
        i=(i+1)%42;
        if(follow){
             int distance=player.x-this.x;
             if(distance>player.sizex+10){
                moveTo(player.x-player.sizex-10,true,5);
             }
             else if(distance<-1*player.sizex-10){
                   moveTo(player.x+player.sizex+10,true,5);
             }
             // if(Math.abs(distance)>Game.screenWidth()&&npcmove){
                 // npcmove=false;
                 // this.x=player.x - (int)Math.signum(distance)*(player.sizex+10);
             // }
             
        }
        if(npcmove){
             if(xy){
               if(targetDirection>0){
                   if(target-x>0){
                        x = x+speed;
                        sprite=spritemove[i/21];
                   }
                   else{
                        sprite=spritestand;
                        npcmove = false;
                   }
               }
               else if(targetDirection<0){
                   if(target-x<0){
                        x = x-speed;
                         sprite=spritemove[i/21+2];
                   }
                   else{
                        sprite=spritestand;
                        npcmove = false;
                   }
               }
              } else {
                  
                  if((speed > 0 && y >= target) || (speed < 0 && y <= target)){
                      y = target;
                      npcmove = false;
                  }
               }
         }
    }
    public void followToggle(){
        follow = !follow;
    }
    public void followTrue(){
        follow = true;
    }
    public void followFalse(){
        follow = false;
    }
    public boolean touchRange(){
        if(Math.abs(player.x-this.x)<=player.sizex  && Math.abs(player.y-this.y)<=player.sizey)return true;
        return false;
    }
}