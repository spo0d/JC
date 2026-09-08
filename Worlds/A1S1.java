package Worlds;

import Engine.*;
import Entities.*;
import Scripts.*;
import Assets.AA1S1;
//
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
//
import java.util.*;


public class A1S1 implements World{
    Input in;
    Mouse mouse;
    Game g;
    Camera camera;
    AA1S1 aa1s1;
    Player player;
    Dialogue introDialogue;
    int count;
    public boolean start;
    String intro;
    int index;
    ArrayList<Entity> entities;
    ArrayList<Dialogue> sceneLines = new ArrayList<>(); // all the lines for this scene, in order
    ArrayList<String> a1s1talk;
    int dialogueIndex=0;
    Dialogue current;
    HashMap<String, Entity> names;
    Entity Speaker;
    public boolean sceneOver;
    public int screenColor = 3;
    
    //opacity presets
    public AlphaComposite opp;
    public AlphaComposite ac;
    
    //dummy locks
    public boolean elock;
    public A1S1(Mouse mousedummy,Input keyboarddummy,Game gdummy, Player player){
        
        in = keyboarddummy;
        mouse = mousedummy;
        g = gdummy;
        
        this.player = player;
        player.setWall(0,2710);
        //script = new Script(g, g.scriaddy);
        aa1s1 = new AA1S1();
        entities = new ArrayList<>();
        
        names = new HashMap<>();
        
        
        entities.add(new NPC("Cobbler", 2082,390,aa1s1.plebsSpritestand,aa1s1.spritemove,new int[]{74, 46, 27}));
        entities.add(new NPC("Carpenter", 2182,390,aa1s1.plebsSpritestand,aa1s1.spritemove,new int[]{195, 155, 120}));
        entities.add(new NPC("Murellus", player.x-player.sizex-10,player.y,aa1s1.spritestand,aa1s1.spritemove,new int[]{78, 29, 75}));
        //add names to hasmap
        names.put("FLAVIUS", player);
        names.put("COBBLER", entities.get(0));
        names.put("CARPENTER", entities.get(1));
        names.put("MURELLUS", entities.get(2));
        
        
        //
        ((NPC)entities.get(1)).sprite=aa1s1.plebsSpritestand[1];
        intro = "FLAVIUS and MURELLUS enter on one side of the stage. A CARPENTER, a COBBLER, and some other commoners enter from the other end of the stage.";
        introDialogue = new Dialogue(50, 250, g.widthx-100, "Narrator", intro);
        count = 0;
        g.dialogues.add(introDialogue);
        start=false;
        
         for(String s : aa1s1.DialogueArr.split("\\|")){
            String cleanLine =  s.trim();
            if(!cleanLine.isEmpty()){
                sceneLines.add(new Dialogue(25,600, g.width-100, cleanLine.split(":")[0], cleanLine.split(":",2)[1]));
            }
        }
        ((NPC)entities.get(2)).follow(player,true);
            ((NPC)entities.get(0)).opaToggle=true;
            ((NPC)entities.get(1)).opaToggle=true;
    }
    void advanceDialogue(){
        if(current != null){
            g.dialogues.remove(current);
        }
        if(dialogueIndex<sceneLines.size()){
            if(Speaker!=null)Speaker.highlight=false;
            current = sceneLines.get(dialogueIndex);
            Speaker=names.get(current.name);
            if(Speaker!=null)Speaker.highlight=true;
            g.dialogues.add(current);
            dialogueIndex++;
        }
        else{
            current = null;
            sceneOver=true;
            player.sitLevel=0;
            ((NPC)entities.get(0)).opaToggle=true;
            ((NPC)entities.get(1)).opaToggle=true;
        }
    }
    @Override
    public void draw(Graphics2D g2){
        g2.drawImage(aa1s1.bg,0,0,2710,590,null);
        for(Entity e: entities){
            if(((NPC)e).opaToggle){
                int dummyDist = Math.abs(e.x-player.x);
                if(dummyDist<=300 && dummyDist>=200)g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (300-dummyDist)/200.0f));
                else if (dummyDist>200)g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0f));
                else g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }
            e.draw(g2);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        }
        player.draw(g2);
        if(!start||count<=765){
            g2.setColor(new Color(255,255,255,765/screenColor));
            g2.fillRect(0,0,g.widthx,g.heighty);
            
         }
        
        
    }
    @Override
    public void update(){
        if(g.interactPressed&&(elock||!start)){
            start = true;
            
            if(count==0){
              count++;
              advanceDialogue();
              g.dialogues.clear();
            }
            if(current != null && !current.isFinishedTyping()){
                current.skipToEnd();   // 1st press on a line: reveal it instantly
            } else {
                advanceDialogue();     // already fully shown (or none yet): go to next / end scene
            }
        } 
        if(start&&screenColor<=765)screenColor++;
        
        if( !elock && start && in.move[5] && ((NPC)entities.get(0)).touchRange(player) ){
            advanceDialogue();
            player.sitLevel=-1;
            ((NPC)entities.get(0)).opaToggle=false;
            ((NPC)entities.get(1)).opaToggle=false;
            elock=true;
           
        }
        
        
        
        player.update();
        if(start&&count==0){
            count++;
        }
        for(Entity e: entities){
            e.update();
        }
        
     }
    @Override
    public ArrayList<Entity> getEntities(){
        return this.entities;
    }
}