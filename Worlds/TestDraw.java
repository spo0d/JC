package Worlds;

import Engine.*;
import Entities.*;
import Assets.AA1S1;
//
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
//
import java.util.*;


public class TestDraw implements World{
    ArrayList<Entity> entities= new ArrayList<>();
    HashSet<int[]> coords = new HashSet<>();
    Game g;
    Mouse m;
    Input in;
    public TestDraw(Game g,Mouse m, Input in){
        this.g=g;
        this.m=m;
        this.in=in;
    }
    public void draw(Graphics2D g2){
        g2.setColor(Color.BLACK);
        g2.drawRect(g.mouseX, g.mouseY, 200, 200);
        g2.drawRect(g.mouseX+43, g.mouseY, 114, 200);
        for(int arr[] : coords){
            g2.drawRect(arr[0], arr[1], 200, 200);
            g2.drawRect(arr[0]+43, arr[1], 114, 200);
        }
    }
    public void update(){
        if(m.pressed) {
            coords.add(new int[]{g.mouseX,g.mouseY});
            m.pressed=false;
        }
    }
    public ArrayList<Entity> getEntities(){
        return this.entities;
    }
    
}