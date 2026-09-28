package Worlds;

import Engine.*;
import Entities.*;
import Assets.AGeneral;
//
import java.awt.*;
import java.awt.image.*;
//
import java.util.*;


public class PauseScreen implements World{
    ArrayList<Entity> entities= new ArrayList<>();
    Game g;
    Mouse m;
    AGeneral ageneral;
    private short frameCount;
    private BufferedImage pauseScrollCurrent;
    private static Font caesar = Engine.Game.caesarDressingLoad();
    public PauseScreen(Game g,Mouse m){
        this.g=g;
        this.m=m;
        ageneral = new AGeneral();
    }
    public void draw(Graphics2D g2){
        g2.setColor(new Color(0, 0, 0, 100)); 
        g2.fillRect(0, 0, g.getWidth(), g.getHeight()+100);
        //
        g2.setColor(Color.WHITE);
        
        g2.drawImage(pauseScrollCurrent,10,-12,1280,740,null);
        g2.setFont(caesar);
        g2.drawString("GAME PAUSED", g.getWidth() / 2 - 160, g.getHeight() / 2 - 100);
    }
    

    public void update(){
        if(frameCount<36){
            pauseScrollCurrent=ageneral.pauseScroll[frameCount/6];
            frameCount++;
        }
        
    }
    public ArrayList<Entity> getEntities(){
        return this.entities;
    }
    
}