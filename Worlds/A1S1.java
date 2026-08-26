package Worlds;

import Engine.*;
import Assets.AA1S1;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import Entities.*;
import java.util.*;
import Scripts.*;
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
    
    public boolean sceneOver;
    
    
    
    
    //dummy locks
    public boolean elock;
    public A1S1(Mouse mousedummy,Input keyboarddummy,Game gdummy, Player player){
        
        in = keyboarddummy;
        mouse = mousedummy;
        g = gdummy;
        //script = new Script(g, g.scriaddy);
        aa1s1 = new AA1S1();
        this.player = player;
        entities = new ArrayList<>();
        entities.add(new NPC("mukesh", 550,475,aa1s1.spritestand,aa1s1.spritemove));
        entities.add(new NPC("rakesh", 100,100,aa1s1.spritestand,aa1s1.spritemove));
        entities.add(new NPC("dinesh", 100,100,aa1s1.spritestand,aa1s1.spritemove));
        intro = "FLAVIUS and MURELLUS enter on one side of the stage. A CARPENTER, a COBBLER, and some other commoners enter from the other end of the stage.";
        introDialogue = new Dialogue(50, 250, g.widthx-100, "bigga", intro);
        count = 0;
        g.dialogues.add(introDialogue);
        start=false;
        
         for(String s : aa1s1.DialogueArr.split("\\|")){
            String cleanLine =  s.trim();
            if(!cleanLine.isEmpty()){
                sceneLines.add(new Dialogue(25,600, g.width-100, cleanLine.split(":")[0], cleanLine.split(":")[2]));
            }
        }
    }
    void advanceDialogue(){
        if(current != null){
            g.dialogues.remove(current);
        }
        if(dialogueIndex<sceneLines.size()){
            current = sceneLines.get(dialogueIndex);
            g.dialogues.add(current);
            dialogueIndex++;
        }
        else{
            current = null;
            sceneOver=true;
        }
    }
    @Override
    public void draw(Graphics2D g2){
        g2.drawImage(aa1s1.bg,0,0,g.widthx,g.heighty,null);
        player.draw(g2);
        for(Entity e: entities){
            e.draw(g2);
        }
        if(!start){
            g2.setColor(Color.WHITE);
            g2.fillRect(0,0,g.widthx,g.heighty);
            
         }
         else{
         }
        if(elock){
            
        }
        
        
    }
    @Override
    public void update(){
        if(g.interactPressed&&(elock||!start)){
            start = true;
            if(count==0){
              advanceDialogue();
              count++;
              g.dialogues.clear();
            }
            if(current != null && !current.isFinishedTyping()){
                current.skipToEnd();   // 1st press on a line: reveal it instantly
            } else {
                advanceDialogue();     // already fully shown (or none yet): go to next / end scene
            }
        } 
        
        
        if( !elock && start && in.move[5] && ((NPC)entities.get(0)).touchRange(player) ){
            advanceDialogue();
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