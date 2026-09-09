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
         menubg = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AMenu/Menu.png"));
         start1 = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AMenu/start1.png"));
         start2 = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AMenu/start2.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}