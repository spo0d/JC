package Entities;

import Engine.Game;
import Engine.Input;
import Assets.AA1S1;
import java.awt.Color;
import java.awt.image.BufferedImage;
public class NPC extends Entity{
    public boolean follow;
    public boolean opaToggle;
    private Player player;
    int i = 0;
    public NPC(String name, int x,int y, BufferedImage spritestand[], BufferedImage spritemove[],int colour[]){
        //this.player=player;
        this.hlcolour=new Color(colour[0], colour[1], colour[2]);
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
        if(lor!=lorAfter)lor=lorAfter;
        i=(i+1)%84;
        if(follow){
             int distance=player.x-this.x;
             if(distance>this.sizex-100){
                moveTo(player.x-(this.sizex-100),player.y);
             }
             else if(distance<-1*(this.sizex-100)){
                   moveTo(player.x+(this.sizex-100),player.y);
             }
             // if(Math.abs(distance)>Game.screenWidth()&&npcmove){
                 // npcmove=false;
                 // this.x=player.x - (int)Math.signum(distance)*(player.sizex+10);
             // }
             
        }
        if(npcmove){
               if(Math.abs(targetX-x)>speed){
                   if(targetX>x){
                       if(!lor)lor=true;
                        x = x+speed;
                        sprite=spritemove[i/21];
                   }
                   else if(targetX<x){
                        if(lor)lor=false;
                        x = x-speed;
                        sprite=spritemove[i/21];
                   }
               }
               else{
                   x=targetX;
                   xdone=true;
               }
               if(Math.abs(targetY-y)>speed){
                   if(targetY>y){
                        y = y+speed;
                        sprite=spritemove[i/21];
                   }
                   else if(targetY<y){
                        y = y-speed;
                        sprite=spritemove[i/21];
                   }
               }
               else{
                   y=targetY;
                   ydone=true;
               }
               if(xdone&&ydone){
                   sprite=spritestand[0];
                   npcmove=false;
                   xdone=false;
                   ydone=false;
               }
        }           
    }
    public void follow(Player p, boolean b){
        follow = b;
        player = p;
    }
    public boolean touchRange(Entity e){
        if(Math.abs(e.x-this.x)<=e.sizex  && Math.abs(e.y-this.y)<=e.sizey)return true;
        return false;
    }
}