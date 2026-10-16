package Engine;
import Entities.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Script{
    Game g;
    String all[];
    HashMap<String, Entity> names;
    ArrayList<Dialogue> sceneLines = new ArrayList<>(); 
    public int block=0;
    Dialogue current;
    Entity Speaker;
    public boolean sceneOver;
    public Script(Game g, String[] all, HashMap<String,Entity> names){
        this.g=g;
        this.all=all;
        this.names=names;
        load();
    }
    public Script(Game g, String all, HashMap<String,Entity> names){
        this.g=g;
        this.all=new String[]{all};
        this.names=names;
        load();
    }
    // call this when Enter is pressed
    public void enter(){
        if(current != null && !current.isFinishedTyping()) current.skipToEnd();
        else next();
    }

    public void next(){
        if(!sceneOver){
            if(current != null){
                g.dialogues.remove(current);
            }
            if(!sceneLines.isEmpty()){
                if(Speaker!=null)Speaker.highlight=false;
                current = sceneLines.remove(0);
                Speaker=names.get(current.name);
                if(Speaker!=null)Speaker.highlight=true;
                switch(current.keyCode.toLowerCase()){
                    case "say":   
                        g.dialogues.add(current);
                        break;
                    case "move":
                        String[] currentMove = current.text.split(",",2);
                        Speaker.moveTo(Integer.parseInt(currentMove[0].trim()),Integer.parseInt(currentMove[1].trim()));
                        next();
                        break;
                    case "look":
                        Speaker.lorAfter=Boolean.valueOf(current.text);
                        next();
                        break;

                }
            }
            else{
                current = null;
                sceneOver=true;
                block++;
            }
        }
    }
    public void load(){
        try{
            for(String s : all[block].split("\\|")){
                String cleanLineArr[] =  s.trim().split(":");
                if(cleanLineArr.length>=3){
                    sceneLines.add(new Dialogue(25,600, g.width-100, cleanLineArr[0],cleanLineArr[1], cleanLineArr[2]));
                }
            }
            sceneOver=false;
        }
        catch(Exception e){
            e.printStackTrace();
        }
    } 
    public void setBlock(int b){
        sceneLines.clear();
        current = null;
        sceneOver=true;
        Speaker.highlight=false;
        block=b;
        load();
    }
    public boolean isFinishedTyping(){
        return this.current.isFinishedTyping();
    }
    public boolean isFullyOver(){
        return !this.sceneLines.isEmpty();
    }
}
