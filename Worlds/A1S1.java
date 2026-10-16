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

public class A1S1 implements World{
    //IMP FOLLOW THIS
    static final int INTRO = 0, ROAM = 1, CUTSCENE = 2, DONE = 3; int stage = INTRO;

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
    Entities.Objects endShiny;
    ArrayList<Entity> entities;
    ArrayList<Dialogue> sceneLines = new ArrayList<>(); 
    int dialogueIndex;
    Dialogue current;
    HashMap<String, Entity> names;
    Entity Speaker;
    public boolean sceneOver;
    public int screenColor;

    //opacity presets
    public AlphaComposite opp;
    public AlphaComposite ac;

    //dummy locks
    public boolean elock;

    //scene end boolean
    public boolean nextScene;
    int entercount;
    Script script;
    public A1S1(Mouse mousedummy,Input keyboarddummy,Game gdummy, Player player){

        in = keyboarddummy;
        mouse = mousedummy;
        g = gdummy;
        g.dialogues.clear();
        //g.audio.startBGSong("");
        this.player = player;
        player.name = "Flavius";
        player.highlight=false;
        player.hlcolour=new Color(244, 208, 63);
        player.y=390;
        player.x=200;
        g.camera.camerax=0;
        g.camera.cameray=0;
        player.setWall(0,2710);
        //script = new Script(g, g.scriaddy);
        aa1s1 = new AA1S1();
        entities = new ArrayList<>();

        names = new HashMap<>();

        g.audio.startBGSong("/assetsfile/Sounds/A1S1bgsound.mp3");
        dialogueIndex=0;
        screenColor = 1;
        entercount=0;
        weterFount1 = new Entities.Objects(aa1s1.waterFountain,1060,357,63,106);
        weterFount2 = new Entities.Objects(aa1s1.waterFountain,1600,357,63,106);
        entities.add(new NPC("Cobbler", 2082,390,aa1s1.plebsSpritestand,aa1s1.spritemove,new int[]{74, 46, 27}));
        entities.add(new NPC("Carpenter", 2182,390,aa1s1.plebsSpritestand,aa1s1.spritemove,new int[]{195, 155, 120}));
        entities.add(new NPC("Murellus", player.x-player.sizex-10,390,aa1s1.spritestand,aa1s1.spritemove,new int[]{78, 29, 75}));

        //add names to hasmap
        names.put("FLAVIUS", player);
        names.put("COBBLER", entities.get(0));
        names.put("CARPENTER", entities.get(1));
        names.put("MURELLUS", entities.get(2));
        names.put("player", player);

        //
        ((NPC)entities.get(1)).sprite=aa1s1.plebsSpritestand[1];
        intro = "FLAVIUS and MURELLUS enter on one side of the stage. A CARPENTER, a COBBLER, and some other commoners enter from the other end of the stage.";
        introDialogue = new Dialogue(50, 250, g.widthx-100,"say", "Narrator", intro);
        //current=introDialogue;
        count = 0;
        g.dialogues.add(introDialogue);
        script = new Script(g,aa1s1.DialogueArr,names);
        ((NPC)entities.get(2)).follow(player,true);
        ((NPC)entities.get(0)).opaToggle=true;
        ((NPC)entities.get(1)).opaToggle=true;
        player.sitLevel=-1;
        enterStage(INTRO);
    }

    @Override
    public void draw(Graphics2D g2){
        g2.drawImage(aa1s1.bg,0,0,2710,720,null);
        weterFount1.draw(g2);
        weterFount2.draw(g2);
        if(endShiny!=null)endShiny.draw(g2);
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
        if(!start||screenColor<=180){
            g2.setColor(new Color(255,255,255,255/screenColor));
            g2.fillRect(0,0,g.widthx,g.heighty);

        }
    }

    @Override
    public void update(){
        if(g.interactPressed&&(elock||!start)){

            if(elock){
                script.enter();
                if(script.isFullyOver()){
                    enterStage(DONE);
                }
            }
            else if(!start){
                if(entercount==0&&!introDialogue.isFinishedTyping()){
                    introDialogue.skipToEnd();
                }
                else if(entercount==1||(entercount==0&&introDialogue.isFinishedTyping())){
                    g.dialogues.clear();
                    enterStage(ROAM);
                    //advanceDialogue(); 
                }
                if(entercount<2)entercount++;

            }
        } 

        if(start&&screenColor<=180)screenColor++;

        if( !elock && start && in.move[5] && ((NPC)entities.get(0)).touchRange(player) ){
            script.next();
            enterStage(CUTSCENE);
        }

        weterFount1.update();
        weterFount2.update();
        if(endShiny!=null){
            endShiny.update();
            if(endShiny.touchRange(player)&&in.move[5]){
                g.audio.stopBGSong();
                entities.clear();
                names.clear();
                mouse.pressed=false;
                sceneLines.clear();
                g.dialogues.clear();
                g.currentWorld= new A1S2(mouse, in, g, player);

            }
        }
        player.update();
        for(Entity e: entities){
            e.update();
        }

    }

    void enterStage(int d){
        stage = d;
        NPC cobbler   = (NPC)entities.get(0);
        NPC carpenter = (NPC)entities.get(1);
        NPC muru = (NPC)entities.get(2);
        switch(d){
            case INTRO:
                intro = "FLAVIUS and MURELLUS enter on one side of the stage. A CARPENTER, a COBBLER, and some other commoners enter from the other end of the stage.";
                introDialogue = new Dialogue(50, 250, g.widthx-100,"say", "Narrator", intro);
                //current=introDialogue;
                // count = 0;
                g.dialogues.add(introDialogue);
                entercount=0;
                start=false;
                elock=false;
                endShiny=null;
                player.sitLevel=-1;
                break;
            case ROAM:
                start = true;
                elock = false;
                endShiny = null;
                player.sitLevel = 0;
                player.setWall(0,2710);
                cobbler.opaToggle = true;  carpenter.opaToggle = true;
                break;
            case CUTSCENE:
                start = true;  elock = true;
                cobbler.opaToggle = false;
                carpenter.opaToggle = false;
                player.setWall(cobbler.x - 600, 2710);   // can't walk back left
                g.audio.stopBGSong();
                break;
            case DONE:
                start = true;  elock = true;
                player.sitLevel = 0;
                player.setWall(cobbler.x - 600, 2710);
                cobbler.opaToggle = true;  carpenter.opaToggle = true;
                endShiny = new Entities.Objects(aa1s1.waterFountain, 2445, 390, 63, 106);
                g.audio.stopBGSong();
                break;
            
        }
    }
    @Override
    public short getSceneID(){
        return 11;
    }
    @Override
    public SaveLoad.WorldData saveWorld(){
        SaveLoad.WorldData d = new SaveLoad.WorldData();
        d.stage = stage;
        return d;
    }
    @Override
    public void loadWorld(SaveLoad.WorldData d){
        int s = d.stage;
        if(s==CUTSCENE) s=ROAM;
        
        g.dialogues.clear(); 
        enterStage(s);      
        Entity muru = entities.get(2);
        muru.x=player.x-200;
        muru.y = 390;
    }
    @Override
    public ArrayList<Entity> getEntities(){
        return this.entities;
    }
}