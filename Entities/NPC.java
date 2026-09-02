package Entities;

import Engine.Game;
import Engine.Input;
import Assets.AA1S1;

import java.awt.image.BufferedImage;
public class NPC extends Entity{
    public boolean follow;
    private Player player;
    int i = 0;
    public NPC(String name, int x,int y, BufferedImage spritestand[], BufferedImage spritemove[] ){
        //this.player=player;
        this.name=name;
        this.x=x;
        this.y=y;
        sizex=200;
        sizey=200;
        this.spritestand=spritestand;
        this.spritemove = spritemove;
        sprite=spritestand[0];
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
                       if(!lor)lor=true;
                        x = x+speed;
                        sprite=spritemove[i/21];
                   }
                   else{
                        sprite=spritestand[0];
                        npcmove = false;
                   }
               }
               else if(targetDirection<0){
                   if(target-x<0){
                       if(lor)lor=false;
                        x = x-speed;
                         sprite=spritemove[i/21];
                   }
                   else{
                        sprite=spritestand[0];
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
    public void followToggle(Player p){
        follow = !follow;
        player = p;
    }
    public void followTrue(Player p){
        follow = true;
        player = p;
    }
    public void followFalse(Player p){
        follow = false;
        player = p;
    }
    public boolean touchRange(Entity e){
        if(Math.abs(e.x-this.x)<=e.sizex  && Math.abs(e.y-this.y)<=e.sizey)return true;
        return false;
    }
}