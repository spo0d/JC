package Scripts;

import Engine.Game;
import Entities.*;
import java.io.*;
import java.util.ArrayList;
public class Script{
    Game game;
    String scriaddy;
    BufferedReader br;
    public Script(Game game, String s){
        this.game=game;
        scriaddy=s;
        try{
                 br = new BufferedReader(new FileReader(scriaddy));
                
                 }
            catch(Exception e){
                e.printStackTrace();
            }
    }
    public void scriptUpdate(String file){
            try{
                 br.close();
                 scriaddy=file;
                 br = new BufferedReader(new FileReader(scriaddy));
                 }
            catch(Exception e){
                e.printStackTrace();
            }
    }
    public void run(){
        while(!game.scripting){
            try{    
                    String line =  br.readLine();
                    if(line==(null)){
                        game.scripting=true;
                    }
                    line=line.trim();
                    if(line.isEmpty()){
                    }
                    String s[] =line.split("\\|"); 
                    
                    switch(s[0]){
                        case "start": 
                            game.scripting=false;
                            break;
                        case "stop": 
                            game.scripting=true;
                            break;
                        case "Move":
                            if(s.length==5){
                           for(Entity e : game.currentWorld.getEntities()){
                             if(e.name.equals(s[1]))e.moveTo(Integer.valueOf(s[2]), (s[3].equals("x")?(true):(false)), Integer.valueOf(s[4])) ;
                           }
                         }
                         break;
                    }
                }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        }
}