package Entities;

import Engine.Input;
import Assets.AA1S1;

import java.awt.image.BufferedImage;
public class Player extends Entity{
    Input input;
    AA1S1 aa1s1;
    int i;
    public int stamina;
    public Player(Input inputdummy){
        input=inputdummy;
        speed=10;
        jumpspeed=15;
        gravity=2;
        this.x=100;
        this.y=475;
        sizex=100;
        sizey=100;
        aa1s1 = new AA1S1();
        spritestand=aa1s1.stand;
        spritemove = aa1s1.move;
    }
    @Override
    public void update(){
        if(input.move[6]&&stamina>0){
            speed=25;
            stamina--;
        }
        else {
            if(!input.move[6])stamina=20;
            speed=10;
        }
        sprite=spritestand;
        if(input.move[1]){
            x-=speed;
            sprite=spritemove[i/11];
            i=(i+1)%22;
        }
        if(input.move[3]){
            x+=speed;
            sprite=spritemove[i/11];
            i=(i+1)%22;
        }
        if(input.move[0]){
            if(!jcheck)
            { 
                jcheck=true;
                yold=y;
                sprite=spritestand; 
            }
            
        }
        if(input.move[2]){
            
        }
        //if(input.move[3]){
          //  x+=speed;
        //}
        if(jcheck){
            if(jumpspeed>-15){
                y-=jumpspeed;
                jumpspeed-=gravity;
            }
            else{
                jumpspeed=15;
                jcheck=false;
                y=yold;
            }
        }
    }
}