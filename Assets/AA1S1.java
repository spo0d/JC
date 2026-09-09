    package Assets;

import java.io.IOException;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import javax.imageio.ImageIO;
public class AA1S1{
    public BufferedImage bg;
    public BufferedImage textwhitebg;
    //player
    public BufferedImage spritestand[] = new BufferedImage[2];
    public BufferedImage spritemove[] = new BufferedImage[4];
    
    public BufferedImage plebsSpritestand[] = new BufferedImage[2];
    public String DialogueArr;
    
    public BufferedImage waterFountain[] = new BufferedImage[8];
    public AA1S1(){
        try{
             bg = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AA1S1/MenuDummy.png"));
             textwhitebg = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AA1S1/textwhitebg.png"));
             plebsSpritestand[0] = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AA1S1/cobbler.png"));
             plebsSpritestand[1] = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AA1S1/carpenter.png"));
             spritestand[0] = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AA1S1/senatorstand1.png"));
             //
             spritemove[0] = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AA1S1/senatormove1.png"));
             spritemove[1] = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AA1S1/senatormove2.png"));
             spritemove[2] = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AA1S1/senatormove3.png"));
             spritemove[3] = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AA1S1/senatormove4.png"));
             DialogueArr = ResourceLoader.loadText("/assetsfile/scripts/a1s1talk.txt");
             
             for(int i=0; i<8; i++){
                 waterFountain[i] = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AA1S1/weter" + (i+1) + ".png"));
             }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}

