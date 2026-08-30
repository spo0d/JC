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
    public BufferedImage spritestand;
    public BufferedImage spritemove[] = new BufferedImage[4];
    public BufferedImage spritesprint[] = new BufferedImage[4];
    public BufferedImage spritejump;
    public BufferedImage spriteattack[] = new BufferedImage[2];
    public String DialogueArr;
    public AA1S1(){
        try{
         bg = ImageIO.read(new File("assetsfile/Images/AA1S1/MenuDummy.png"));
         textwhitebg = ImageIO.read(new File("assetsfile/Images/AA1S1/textwhitebg.png"));
         spritestand = ImageIO.read(new File("assetsfile/stand.png"));
         //
         
         spritemove[0] = ImageIO.read(new File("assetsfile/right1.png"));
         spritemove[1] = ImageIO.read(new File("assetsfile/right2.png"));
         spritemove[2] = ImageIO.read(new File("assetsfile/left1.png"));
         spritemove[3] = ImageIO.read(new File("assetsfile/left2.png"));
         spritesprint[0] = ImageIO.read(new File("assetsfile/leftdash.png"));
         spritesprint[1] = ImageIO.read(new File("assetsfile/rightdash.png"));
         spriteattack[0] = ImageIO.read(new File("assetsfile/attack1.png"));
         spriteattack[1] = ImageIO.read(new File("assetsfile/attack2.png"));
         spritejump = ImageIO.read(new File("assetsfile/jump.png"));
         // Works perfectly in all BlueJ versions without changing any settings
        try {
            DialogueArr = new String(java.nio.file.Files.readAllBytes(new java.io.File("assetsfile/scripts/a1s1talk.txt").toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}

