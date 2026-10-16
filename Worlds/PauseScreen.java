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
    Engine.Button save;
    Engine.Button load;
    //Engine.Button ss;
    Engine.Button quit;
    private short frameCount;
    private BufferedImage pauseScrollCurrent;
    private BufferedImage ee;
    private BufferedImage ee2;
    private static Font caesar = Engine.Game.caesarDressingLoad();

    //
    private static final String SLOT = "saves/slot1.json";
    private String msg = "";
    private long msgUntil;
    public PauseScreen(Game g,Mouse m){
        this.g=g;
        this.m=m;
        ageneral = new AGeneral();

        ee = ageneral.pauseScroll[4];
        ee2 = ageneral.pauseScroll[5];
        pauseScrollCurrent=ageneral.pauseScroll[0];
        save = new Engine.Button(ee,ee2,440,153,400,100,m,g);
        load = new Engine.Button(ee,ee2,440,303,400,100,m,g);
        quit = new Engine.Button(ee,ee2,440,453,400,100,m,g);
    }

    public void draw(Graphics2D g2){
        g2.setColor(new Color(0, 0, 0, 100)); 
        g2.fillRect(0, 0, 1280, 840);
        //
        g2.setColor(Color.WHITE);

        g2.drawImage(pauseScrollCurrent,10,-12,1280,740,null);
        if(frameCount>=36){
            save.draw(g2);
            load.draw(g2);
            quit.draw(g2);
        }
        g2.drawString(msg, 440, 620);
    }

    public void update(){
        if(frameCount<36){
            pauseScrollCurrent=ageneral.pauseScroll[frameCount/6];
            frameCount++;
        }

        save.update();
        load.update();
        quit.update();
        if(save.isPressed()){
            System.out.println("save");
            m.pressed = false;
            say((SaveLoad.SaveManager.saveGame(g, SLOT)) ? ("Game saved") : ("Can't save here"));
        }
        else if(load.isPressed()){
            System.out.println("load");
            m.pressed = false;
            if(SaveLoad.SaveManager.loadGame(g, SLOT)){
                g.keyboard.screenPaused = false;   // unpause so you land in the loaded game
            }
            else say("No save found");
        }
        else if(quit.isPressed()){
            System.out.println("quit");
            g.audio.shutdown();
            //
            java.awt.Window frame = javax.swing.SwingUtilities.getWindowAncestor(g);

            if (frame != null) {
                // 2. Dispatch the WINDOW_CLOSING event directly to the frame
                frame.dispatchEvent(new java.awt.event.WindowEvent(frame, java.awt.event.WindowEvent.WINDOW_CLOSING));
            } else {
                // Fallback safety just in case the frame isn't found
                g.audio.shutdown();
                System.exit(0);
            }
        }
    }

    private void say(String s){
        msg = s;
        msgUntil = System.currentTimeMillis() + 2000;
    }

    public ArrayList<Entity> getEntities(){
        return this.entities;
    }

}