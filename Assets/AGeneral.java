package Assets;


import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
public class AGeneral{
    
    public BufferedImage[] pauseScroll = new BufferedImage[6];
    public BufferedImage start1;
    public BufferedImage start2;
    public AGeneral(){
        try{
         pauseScroll[0] = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AGeneral/pauseScroll0.png"));
         pauseScroll[1] = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AGeneral/pauseScroll1.png"));
         pauseScroll[2] = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AGeneral/pauseScroll2.png"));
         pauseScroll[3] = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AGeneral/pauseScroll3.png"));
         pauseScroll[4] = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AGeneral/pauseScroll4.png"));
         pauseScroll[5] = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AGeneral/pauseScroll5.png"));
         start1 = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AMenu/start1.png"));
         start2 = ImageIO.read(getClass().getResourceAsStream("/assetsfile/Images/AMenu/start2.png"));
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}