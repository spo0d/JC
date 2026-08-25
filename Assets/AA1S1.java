package Assets;

import java.io.IOException;
import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import javax.imageio.ImageIO;
public class AA1S1{
    public BufferedImage bg;
    public BufferedImage textwhitebg;
    public BufferedImage stand;
    public BufferedImage move[] = new BufferedImage[2];
    public String DialogueArr;
    public AA1S1(){
        try{
         bg = ImageIO.read(new File("assetsfile/Images/AA1S1/MenuDummy.png"));
         textwhitebg = ImageIO.read(new File("assetsfile/Images/AA1S1/textwhitebg.png"));
         stand = ImageIO.read(new File("assetsfile/stand.png"));
         move[0] = ImageIO.read(new File("assetsfile/left.png"));
         move[1] = ImageIO.read(new File("assetsfile/right.png"));
         DialogueArr = Files.readString(Path.of("assetsfile/scripts/a1s1talk.txt"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}

