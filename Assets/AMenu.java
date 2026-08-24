package Assets;


import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
public class AMenu{
    
    public BufferedImage menubg;
    public BufferedImage start1;
    public BufferedImage start2;
    public AMenu(){
        try{
         menubg = ImageIO.read(new File("assetsfile/Images/AMenu/Menu.png"));
         start1 = ImageIO.read(new File("assetsfile/Images/AMenu/start1.png"));
         start2 = ImageIO.read(new File("assetsfile/Images/AMEnu/start2.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}