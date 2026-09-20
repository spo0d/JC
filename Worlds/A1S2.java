package Worlds;

import Engine.*;
import Entities.*;
import Assets.AA1S2;
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
    AA1S2 aa1s2;
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
    ArrayList<String> moveStuff = new ArrayList<>(); // movement
    HashMap<String,Entity> names = new HashMap<>();
    int blockIndex=0;
    Dialogue current;
    //String currentMove;
    Entity Speaker;
    public boolean sceneOver;
    public int screenColor = 1;
    
    //opacity presets
    public AlphaComposite opp;
    public AlphaComposite ac;
    
    //dummy locks
    public boolean elock;
    int entercount=0;
    int i=0;
    public int blockNo=0;
    
    public A1S2(Mouse mousedummy,Input keyboarddummy,Game gdummy, Player player){
        
        in = keyboarddummy;
        mouse = mousedummy;
        g = gdummy;
        g.dialogues.clear();
        //g.audio.startBGSong("");
        this.player = player;
        player.x=200;
        player.y=390;
        player.highlight=false;
        g.camera.camerax=0;
        g.camera.cameray=-g.heighty;
        player.sitLevel=-1;
        player.setWall(0,2710);
        aa1s2 = new AA1S2();
        player.setChara("Marcus Brutus", 563,390,aa1s2.spritestand,aa1s2.spritemove,new int[]{70, 130, 180});
        entities = new ArrayList<>();
        entities.add(new NPC("Soothsayer", 50,390,aa1s2.spritestand,aa1s2.spritemove,new int[]{138, 43, 226}));
        entities.add(new NPC("Julius Caesar", 731,390,aa1s2.spritestand,aa1s2.spritemove,new int[]{226, 88, 62}));
        entities.add(new NPC("Calpurnia", 288,390,aa1s2.spritestand,aa1s2.spritemove,new int[]{212, 175, 55}));
        entities.add(new NPC("Mark Antony", 910,390,aa1s2.spritestand,aa1s2.spritemove,new int[]{255, 36, 0}));
        entities.add(new NPC("Cassius", 409,player.y,aa1s2.spritestand,aa1s2.spritemove,new int[]{46, 139, 87}));
        entities.add(new NPC("Casca", 480,390,aa1s2.spritestand,aa1s2.spritemove,new int[]{112, 128, 144}));
        entities.add(new NPC("Cicero", 163,390,aa1s2.spritestand,aa1s2.spritemove,new int[]{245, 222, 179}));
        entities.add(new NPC("Portia", 228,player.y,aa1s2.spritestand,aa1s2.spritemove,new int[]{255, 182, 193}));
        entities.add(new NPC("Decius Brutus", 108,390,aa1s2.spritestand,aa1s2.spritemove,new int[]{160, 82, 45}));
        entities.add(new NPC("Flavius",-60 ,390,aa1s2.spritestand,aa1s2.spritemove,new int[]{156, 127, 26}));
        entities.add(new NPC("Murellus", -10,player.y,aa1s2.spritestand,aa1s2.spritemove,new int[]{46, 17, 44}));
        entities.get(0).highlight=true;
        entities.get(0).hlcolour=new Color(0,0,0,0);
        //add names to hasmap
        names.put("SOOTHSAYER", entities.get(0));
        names.put("CAESAR", entities.get(1));
        names.put("CALPURNIA", entities.get(2));
        names.put("ANTONY", entities.get(3));
        names.put("CASSIUS", entities.get(4));
        names.put("CASCA", entities.get(5));
        names.put("CICERO", entities.get(6));
        names.put("PORTIA", entities.get(7));
        names.put("DBRUTUS", entities.get(8));
        names.put("FLAVIUS", entities.get(9));
        names.put("MURELLUS", entities.get(10));
        names.put("BRUTUS", player);
        entities.get(1).speed=2;
        String intro = "A trumpet sounds. CAESAR, ANTONY (who is dressed for a traditional foot race), CALPHURNIA, PORTIA, DECIUS, CICERO, BRUTUS, CASSIUS, and CASCA enter, followed by great crowd of commoners, including a SOOTHSAYER. MURELLUS and FLAVIUS follow after them.";
        introDialogue =new Dialogue(50, 250, g.widthx-100, "Narrator", intro);
        g.dialogues.add(introDialogue);
        try{
             for(String s : aa1s2.DialogueArr[0].split("\\|")){
                String cleanLine =  s.trim();
                String arrCleanLine[];
                if(!cleanLine.isEmpty()) {
                     arrCleanLine = cleanLine.split(":",3);
                     sceneLines.add(new Dialogue(25,600, g.width-100, arrCleanLine[0],arrCleanLine[2]));
                     moveStuff.add(arrCleanLine[1]);
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    void textBlockLoader(){
            blockNo++;
            if(blockIndex>=aa1s2.DialogueArr.length)return;
            try{
                 for(String s : aa1s2.DialogueArr[blockIndex].split("\\|")){
                    String cleanLine =  s.trim();
                    String arrCleanLine[];
                    if(!cleanLine.isEmpty()) {
                         arrCleanLine = cleanLine.split(":",3);
                         sceneLines.add(new Dialogue(25,600, g.width-100, arrCleanLine[0],arrCleanLine[2]));
                         moveStuff.add(arrCleanLine[1]);
                    }
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
    }
    void advanceDialogue(){
        if(!sceneOver){
                if(current != null){
                g.dialogues.remove(current);
            }
            if(!(sceneLines.isEmpty()||moveStuff.isEmpty())){
                if(Speaker!=null)Speaker.highlight=false;
                current = sceneLines.remove(0);
                Speaker=names.get(current.name);
                if((entities.get(0)).equals(Speaker) && i==0){
                    entities.get(0).hlcolour=  new Color(138, 43, 226);
                }
                if(Speaker!=null)Speaker.highlight=true;
                String[] currentMove = moveStuff.remove(0).split(",");
                if(currentMove.length==2){
                 Speaker.moveTo(Integer.parseInt(currentMove[0].trim()),Integer.parseInt(currentMove[1].trim()));
                }
                
                if(current.text.equals("left")){
                  Speaker.setAfterMoveLOR(false);  
                  advanceDialogue();
                }
                else if(current.text.equals("right")){
                  Speaker.setAfterMoveLOR(true  );  
                  advanceDialogue();
                }
                else if(!current.text.equals("son")) g.dialogues.add(current);
            }
            else{
                Speaker.highlight=false;
                sceneOver=true;
                blockIndex++;
                textBlockLoader();
            }
        }
    }
    @Override
    public void draw(Graphics2D g2){
        g2.drawImage(aa1s2.bgmain,0,0,2710,590,null);
        for(Entity e: entities){
            e.draw(g2);
        }
        player.draw(g2);
        if(!start||screenColor<=180){
            g2.setColor(new Color(255,255,255,255/screenColor));
            g2.fillRect(0,g.camera.cameray,g.widthx,5*g.heighty);
         }       
        }
    @Override
    public void update(){
       //general dialogue stuff
       if(g.interactPressed&&(elock||!start)){
            
            if(elock){
                if(current != null && !current.isFinishedTyping()&&start){
                        current.skipToEnd();   // 1st press on a line: reveal it instantly
                        
                    }
                    else {
                        advanceDialogue();     // already fully shown (or none yet): go to next / end scene
                    }
            }
            else if(!start){
                    if(entercount==0&&!introDialogue.isFinishedTyping()){
                    introDialogue.skipToEnd();
                }
                else if(entercount==1||(entercount==0&&introDialogue.isFinishedTyping())){
                   start = true;
                   g.dialogues.clear();
                   advanceDialogue(); 
                   elock=true;
                }
                if(entercount<2)entercount++;
            }
        }  
       if(start&&screenColor<=360)screenColor++;
       // in update(), once start:
       if(start && screenColor>180 && screenColor<=360){
           g.camera.cameray = (19*g.camera.cameray) / 20; // eases from -720 toward 0
       }
       if(screenColor==361){
           g.camera.cameray = 0;
           screenColor = 482;
       }
       if(blockNo==1){
           //g.audio.startBGSong("");
           if(sceneOver)sceneOver=false;
       }
       player.update();
       for(Entity e: entities){
           e.update();
       }
     }
    @Override
    public ArrayList<Entity> getEntities(){
        return this.entities;
    }
}