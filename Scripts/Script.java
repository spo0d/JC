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
            try{    
                    String s[] = br.readLine().trim().split("\\|");     
                    if(s[0].equals("Say")&&s.length==7)say(s);
                    if(s[0].equals("Move")&&s.length==5){
                        for(Entity e : game.currentWorld.entities){
                            if(e.name==s[1])e.moveTo(Integer.valueOf(s[2]), Boolean.valueOf(s[3]), Integer.valueOf(s[4])) ;
                        }
                    }
                
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        public void say(String s[]){
            try{
                game.tx=Integer.valueOf(s[1]);
                game.ty=Integer.valueOf(s[2]);
                game.twidth=Integer.valueOf(s[3]);
                game.tname=s[4];
                game.ttext=s[5];
                game.tdummy=Integer.valueOf(s[6]);
                game.tframe=0;
                game.dialogueactive=true;
                game.scripting=true;
            }
            catch(Exception e){
                System.out.println(s + e.getMessage());
                e.printStackTrace();
            }
                
    }
    public void move(){
        
    }
}