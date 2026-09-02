package Entities;

import Engine.*;
import Assets.AA1S1;

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
    public Player(Input input,Mouse mouse){
        this.name="Flavius";
        this.input=input;
        this.mouse = mouse;
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
        i=(i+1)%42;  
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
    }
}