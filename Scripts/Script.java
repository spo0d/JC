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
                    if(s[0]=="Say"&&s.length==6)say(s);
                    if(s[0]=="Move"&&s.length==5){
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
            String dummy[] = new String[5];
            try{
                game.tx=Integer.valueOf(dummy[1]);
                game.ty=Integer.valueOf(dummy[2]);
                game.twidth=Integer.valueOf(dummy[3]);
                game.tname=dummy[4];
                game.ttext=dummy[5];
                game.dialogueactive=true;
            }
            catch(Exception e){
                System.out.println(s + e.getMessage());
                e.printStackTrace();
            }
                
    }
    public void move(){
        
    }
}