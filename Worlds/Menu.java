package Worlds;

import Engine.*;
import Assets.AMenu;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import Scripts.*;
import Entities.*;
public class Menu implements World{
    int posx;
    int posy;
    Input in;
    Game game;
    AMenu amenu;
    Player player;
    Mouse mouse;
    public int count;
    public boolean check;
    public ArrayList<Entity> entities;
    
    public Menu(Mouse mouse,Input in,Game game, Player player){
        this.game = game;
        this.mouse = mouse;
        this.in = in;
        this.player = player;
        entities = new ArrayList<>();
        amenu = new AMenu();
    }
    @Override
    public void draw(Graphics2D g2){
        int buttonW = (2*amenu.start1.getWidth());
        int buttonH = (2*amenu.start1.getHeight());

        int buttonX = (1280 - buttonW) / 2;
        int buttonY = 720 - buttonH-40; 
        g2.drawImage(amenu.menubg, 0,0,game.widthx,game.heighty,null);
        if(mouse.x>buttonX*game.scalex+game.offsetx && mouse.x<(buttonX+buttonW)*game.scalex+game.offsetx && mouse.y>buttonY*game.scaley+game.offsety && mouse.y<(buttonY+buttonH)*game.scaley+game.offsety){
            g2.drawImage(amenu.start1, (25600-23*buttonW)/40,(40*buttonY-3*buttonH)/40,23*buttonW/20,23*buttonH/20,null);
            if(mouse.pressed){
                game.currentWorld= new A1S1(mouse, in, game, player);
                game.script.scriptUpdate("assetsfile/scripts/a1s1.txt");
                mouse.pressed=false;
            }
        }
        else{
            g2.drawImage(amenu.start2, buttonX,buttonY,buttonW,buttonH,null);
        }
    }
    @Override
    public void update(){
    }
    @Override
    public ArrayList<Entity> getEntities(){
        return this.entities;
    }
}