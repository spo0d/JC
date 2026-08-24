package Engine;

import Entities.Player;
import Engine.Game;
public class Camera{
    Player player;
    Game game;
    public int camerax;
    public Camera(Player player, Game game){
        this.player = player;
        this.game = game;
        camerax=0;
    }
    public void update(){
        int screenLeft = camerax;
        int screenRight = camerax + game.widthx;
    
        int playerLeft = player.x;
        int playerRight = player.x + player.sizex;
    
        // Player went past RIGHT edge
        if(playerRight > screenRight){
            camerax += playerRight - screenRight;
        }
    
        // Player went past LEFT edge
        if(playerLeft < screenLeft){
            camerax -= screenLeft - playerLeft;
        }
    
        // Don't let camera go before the beginning of the world
        if(camerax < 0){
            camerax = 0;
            player.x=0;
        }
    }
}