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


public class A1S2 implements World{
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
    Entities.Objects weterFount1;
    Entities.Objects weterFount2;
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
    public A1S2(Mouse mousedummy,Input keyboarddummy,Game gdummy, Player player){
        
        in = keyboarddummy;
        mouse = mousedummy;
        g = gdummy;
        //g.audio.startBGSong("");
        this.player = player;
        player.setWall(0,2710);
        //script = new Script(g, g.scriaddy);
        aa1s1 = new AA1S1();
        entities = new ArrayList<>();
        
        //
        ((NPC)entities.get(1)).sprite=aa1s1.plebsSpritestand[1];
        intro = "FLAVIUS and MURELLUS enter on one side of the stage. A CARPENTER, a COBBLER, and some other commoners enter from the other end of the stage.";
        introDialogue = new Dialogue(50, 250, g.widthx-100, "Narrator", intro);
        count = 0;
        g.dialogues.add(introDialogue);
        start=false;
        try{
             for(String s : aa1s1.DialogueArr.split("\\|")){
                String cleanLine =  s.trim();
                if(!cleanLine.isEmpty()){
                    sceneLines.add(new Dialogue(25,600, g.width-100, cleanLine.split(":")[0], cleanLine.split(":",2)[1]));
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
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
        }
    }
    @Override
    public void draw(Graphics2D g2){
        g2.drawImage(aa1s1.bg,0,0,2710,590,null);
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
            ((NPC)entities.get(0)).opaToggle=false;
            ((NPC)entities.get(1)).opaToggle=false;
            player.setWall(((NPC)entities.get(0)).x-600,2710);
            elock=true;
           
        }
        
        
        weterFount1.update();
        weterFount2.update();
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