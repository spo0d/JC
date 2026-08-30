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
    public boolean lor;
    //false left true right
    public Player(Input input,Mouse mouse){
        this.name="Flavius";
        this.input=input;
        this.mouse = mouse;
        speed=6;
        jumpspeed=15;
        gravity=2;
        this.x=100;
        this.y=475;
        sizex=100;
        sizey=100;
        aa1s1 = new AA1S1();
        spritestand=aa1s1.spritestand;
        spritemove = aa1s1.spritemove;
        spritejump = aa1s1.spritejump;
        spritesprint = aa1s1.spritesprint;
        spriteattack = aa1s1.spriteattack;
    }
    @Override
    public void update(){
        //always set to stand cause if nothn i.e idle then spritestand
        sprite=spritestand;
        i=(i+1)%42;       
        //A
        if(input.move[1]){
            x-=speed;
            sprite=spritemove[i/21+2];
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
                sprite=spritestand; 
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