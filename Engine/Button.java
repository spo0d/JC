package Engine;
import java.awt.image.*;
import java.awt.Graphics2D;

public class Button{
    int x, y, width, height;

    BufferedImage normal;
    BufferedImage hover;
    Mouse m;
    Game g;
    private boolean hovering;
    private boolean isPressed;
    public Button( int x, int y, int width, int height,Mouse m, Game g){
        
        this.m=m;
        this.g=g;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }
    public Button(BufferedImage normal, BufferedImage hover, int x, int y, int width, int height,Mouse m, Game g){
        
        this.m=m;
        this.g=g;
        this.normal=normal;
        this.hover=hover;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }
    public Button(BufferedImage normal, int x, int y, int width, int height,Mouse m, Game g){
        this.m=m;
        this.g=g;
        this.normal=normal;
        this.hover=normal;
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
    }
    public boolean isPressed(){
        return isPressed;
    }
    public void update(){
        hovering = g.mouseX>=x &&
                   g.mouseX<=x + width &&
                   g.mouseY>=y && 
                   g.mouseY<=y + height;
        if(hovering&&m.pressed){
            isPressed=true;
        }
        else if(isPressed){
            isPressed=false;
        }
    }
    public void draw(Graphics2D g2) {
        g2.drawImage(
            hovering ? hover : normal,
            x, y, width, height, null
        );
    }
}
