package Assets;


import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
public class General{
    
    public BufferedImage menubg;
    public BufferedImage start1;
    public BufferedImage start2;
    public General(){
        try{
         menubg = ImageIO.read(new File("assetsfile/Images/Menu.png"));
         start1 = ImageIO.read(new File("assetsfile/Images/start1.png"));
         start2 = ImageIO.read(new File("assetsfile/Images/start2.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}