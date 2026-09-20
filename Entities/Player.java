package Entities;

import Engine.*;
import Assets.AA1S1;

import java.awt.Color;
import java.awt.image.BufferedImage;
public class Player extends Entity{
    Input input;
    Mouse mouse;
    AA1S1 aa1s1;
    int i;
    long frameatk;
    public int stamina;
    public String lastPressed;
    
    //
    public short sitLevel=0;
    int maxXL;
    int maxXR;
    public Player(Input input,Mouse mouse){
        this.name="Flavius";
        this.input=input;
        this.mouse = mouse;
        this.hlcolour=new Color(244, 208, 63);
        speed=6;
        jumpspeed=15;
        gravity=2;
        this.x=200;
        this.y=390;
        sizex=200;
        sizey=200;
        aa1s1 = new AA1S1();
        spritestand=aa1s1.spritestand;
        spritemove = aa1s1.spritemove;
    }
    @Override
    public void update(){
        //always set to stand cause if nothn i.e idle then spritestand
        sprite=spritestand[0];
        if(lor!=lorAfter)lor=lorAfter;
        if(x+sizex>=maxXR && lor){
            speed=0;
        }
        else if(x<=maxXL && !lor){
            speed=0;
        }
        else{
            speed=6;
        }
        i=(i+1)%84;  
        if(sitLevel>=0){
            //A
            if(input.move[1]){
                x-=speed;
                sprite=spritemove[i/21];
                lastPressed="A";
                lor=false;
            }
            //D
            if(input.move[3]){
                x+=speed;
                sprite=spritemove[i/21];
                lastPressed="D";
                lor=true;
            }
        }
        if(sitLevel>=1){
            //check if dashing
            if(input.move[6]&&stamina>0){
                speed=25;
                stamina--;
                sprite=spritesprint[(i/6)%2];
            }
            else {
                if(!input.move[6])stamina=20;
                speed=6;
                
            }
            //jump
            if(input.move[0]){
                if(!jcheck)
                { 
                    jcheck=true;
                    yold=y;
                    sprite=spritestandNPC; 
                }
                lastPressed="Space";
            }
            //shift
            if(input.move[2]){
                lastPressed="Shift";
            }
            //jumpcontinue
            if(jcheck){
                if(jumpspeed>-15){
                    y-=jumpspeed;
                    jumpspeed-=gravity;
                    sprite=spritejump;
                }
                else{
                    jumpspeed=15;
                    jcheck=false;
                    y=yold;
                }
            }
        }
        if(sitLevel>=2){
            //attack
            if(12>frameatk && frameatk>=0){
                    sprite=spriteattack[0];
            }else if(frameatk<-1){
                    sprite=spriteattack[1];
            }
            if(mouse.clicked){
                if(frameatk>=12||frameatk==-1){
                    frameatk=0;
                }
                else if(frameatk<12&&frameatk!=-1){ 
                    frameatk=-8;
                }
                mouse.clicked=false;
            }
            if(frameatk>=-8&&frameatk!=-1)frameatk++;
        }
        
        
        //make player follow
        if(npcmove&&sitLevel==-1){
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
    public void setWall(int x1, boolean lor){
        if(lor){
            maxXR=x1-100;
        }
        else{
            maxXL=x1;
        }
    }
    public void setWall(int x1,int x2){
        maxXL=x1;
        maxXR=x2-100;;
    }
    public void setChara(String name, int x,int y, BufferedImage spritestand[], BufferedImage spritemove[],int colour[]){
        this.name=name;
        this.hlcolour=new Color(colour[0], colour[1], colour[2]);
        this.x=x;
        this.y=y;
        this.spritestand=spritestand;
        this.spritemove = spritemove;
    }
}