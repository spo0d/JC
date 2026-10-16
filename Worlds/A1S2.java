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

    //IMP FOLLOW THIS
    static final int INTRO = 0, TOUCH = 1, BUCASp1 = 2, BUCASp2 = 3; int stage = INTRO;
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
    Dialogue current;
    //String currentMove;
    Entity Speaker;
    public int screenColor = 1;

    //opacity presets
    public AlphaComposite opp;
    public AlphaComposite ac;

    //dummy locks
    public boolean elock;
    int entercount=0;
    int i=0;

    int whiteBang = 0;
    Script script;
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
        names.put("player", player);
        entities.get(1).speed=2;
        enterStage(INTRO);
    }

    @Override
    public void draw(Graphics2D g2){
        g2.drawImage(aa1s2.bgmain,0,0,2710,720,null);
        for(Entity e: entities){
            e.draw(g2);
        }
        player.draw(g2);
        if((!start||screenColor<=180)&&script.block<2){
            g2.setColor(new Color(255,255,255,255/screenColor));
            g2.fillRect(0,g.camera.cameray,g.widthx,g.heighty);
        }
        if((script.block==2&&screenColor<510)){
            g2.setColor(new Color(255,255,255,255-Math.abs(255-screenColor)));
            g2.fillRect(0,g.camera.cameray,g.widthx,g.heighty);
        }
    }

    @Override
    public void update(){
        //general dialogue stuff
        if(g.interactPressed&&(elock||!start)&& player.sitLevel>=-1){
            if(elock){
                if(!(script.block==2&&screenColor<512)){
                    script.enter();
                }
                if(script.isFullyOver()&&script.block==1){
                    names.get("SOOTHSAYER").hlcolour= new Color(138, 43, 226);
                }
            }
            else if(!start){
                if(entercount==0&&!introDialogue.isFinishedTyping()){
                    introDialogue.skipToEnd();
                }
                else if(entercount==1||(entercount==0&&introDialogue.isFinishedTyping())){
                    start = true;
                    g.dialogues.clear();
                    script.next();
                    elock=true;
                }
                if(entercount<2)entercount++;
            }
        }
        if(script.block<2){
            if(script.block==1&&script.sceneOver)script.load();
            if((start&&screenColor<=360))screenColor++;
            if(start && screenColor>180 && screenColor<=360){
                g.camera.cameray = (9*g.camera.cameray) / 10; // eases from -720 toward 0
            } 
            if(screenColor==361){
                g.camera.cameray = 0;
                screenColor = 513;
            }
        }
        else if(script.block==2){
            if(start && screenColor<=510 && screenColor!=513){
                screenColor+=2;
            }
            else if(screenColor==513){
                script.load();
                player.sitLevel=0;
                screenColor=0;
            }
            if(screenColor==256){
                entities.clear();
                names.clear();
                entities.add(new NPC("Cassius", 409,player.y,aa1s2.spritestand,aa1s2.spritemove,new int[]{46, 139, 87}));
                names.put("CASSIUS", entities.get(0));
                player.x=534;
                player.lorAfter=false;
            }
        }
        else if(script.block>2){
            if(script.sceneOver)script.load();
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
        
                String intro = "A trumpet sounds. CAESAR, ANTONY (who is dressed for a traditional foot race), CALPHURNIA, PORTIA, DECIUS, CICERO, BRUTUS, CASSIUS, and CASCA enter, followed by great crowd of commoners, including a SOOTHSAYER. MURELLUS and FLAVIUS follow after them.";
                introDialogue =new Dialogue(50, 250, g.widthx-100,"say", "Narrator", intro);
                g.dialogues.add(introDialogue);

                script = new Script(g,aa1s2.DialogueArr,names);    
                player.sitLevel=-1;
                break;
            case TOUCH:
                script.block=1;
                screenColor=0;
                break;
            case BUCASp1:
                break;
            case BUCASp2:
                break;

        }
    }

    @Override
    public short getSceneID(){
        return 12;
    }

    @Override
    public SaveLoad.WorldData saveWorld(){
        SaveLoad.WorldData d = new SaveLoad.WorldData();
        d.stage = stage;
        return d;
    }

    @Override
    public void loadWorld(SaveLoad.WorldData d){
        
    }

    @Override
    public ArrayList<Entity> getEntities(){
        return this.entities;
    }
}