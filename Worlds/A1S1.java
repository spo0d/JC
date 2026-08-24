package Worlds;

import Engine.*;
import Assets.AA1S1;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import Entities.*;
import java.util.ArrayList;
public class A1S1 implements World{
    Input in;
    Mouse mouse;
    Game g;
    Camera camera;
    AA1S1 aa1s1;
    Player player;
    int count;
    public boolean start;
    String intro;
    int index;
    public A1S1(Mouse mousedummy,Input keyboarddummy,Game gdummy, Player player){
        in = keyboarddummy;
        mouse = mousedummy;
        g = gdummy;
        aa1s1 = new AA1S1();
        this.player = player;
        entities.add(new NPC("mukesh", 100,100,aa1s1.stand,aa1s1.move));
        entities.add(new NPC("rakesh", 100,100,aa1s1.stand,aa1s1.move));
        entities.add(new NPC("dinesh", 100,100,aa1s1.stand,aa1s1.move));
        intro = "FLAVIUS and MURELLUS enter on one side of the stage. A CARPENTER, a COBBLER, and some other commoners enter from the other end of the stage.";
        count = 255;
        start=false;
        index=0;
    }
    @Override
    public void draw(Graphics2D g2){
        Font font = new Font("Arial", Font.PLAIN, 35);
        g2.drawImage(aa1s1.bg,0,0,g.widthx,g.heighty,null);
        player.draw(g2);
        for(Entity e: entities){
            e.draw(g2);
        }
        if(!start){
            
          if(count<=255&&count>=0){
            Color c = new Color(255,255,255,count);
            g2.setColor(c);
            g2.fillRect(0,0,g.widthx,g.heighty);
           }
          if(index<intro.length())index++;
          g2.setColor(Color.BLACK);
          g.textLoader(g2,"bigga",intro,index,50,250,g.widthx-100, font);
         }
        
        
        
    }
    @Override
    public void update(){
        if(in.move[4])start=true;
        if(start&&count>=0)count-=1.5;
        player.update();
        
        for(Entity e: entities){
            e.update();
        }
     }
}