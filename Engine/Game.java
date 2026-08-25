package Engine;
import Entities.*;
import Worlds.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.*;
import Scripts.*;
public class Game extends JPanel implements Runnable{
    public Input keyboard;
    Camera camera;
    public Mouse mouse;
    public Player player;
    public Worlds.World currentWorld;
    public Scripts.Script script;
    public  int widthreal;
    public  int heightreal;
    public  int height;
    public int width;
    public double scalex;
    public double scaley;
    public int world;
    final public  int heighty;
    final public  int widthx;
    public int offsetx;
    public int offsety;
    
    public String scriaddy;
    public boolean scripting;
    public boolean dialogueactive;
    public int tx;
    public int ty;
    public int twidth;
    public int tindex;
    public String tname;
    public String ttext;
    public int tframe;
    public int tdummy;
    public final Font tfont = new Font("Arial", Font.PLAIN, 24);
    public Game(){
        scriaddy="assetsfile/scripts/menu.txt";
        heighty=720;
        widthx=1280;
        world=0;
        width=getWidth();
        height=getHeight();
        keyboard = new Input();
        mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(keyboard);
        tindex=0;
        player = new Player(keyboard);
        camera=new Camera(player,this);
        currentWorld = new Worlds.Menu(mouse,keyboard, this,player);
        script = new Script(this, scriaddy);
        
        
        Thread gameThread = new Thread(this);
        gameThread.start();
        
        //loadImages();
        //loadSounds();
    }
    @Override
    public void run(){
       while(true){
           if(widthreal!=getWidth()||heightreal!=getHeight()){
            widthreal=getWidth();
            heightreal=getHeight();
            if(widthreal*9<heightreal*16){
                height=((9*widthreal)/16);
                width=widthreal;
                
            }
            else{
                height=heightreal;
                width=((16*heightreal)/9);
               
            }
            scaley=(height+0.0)/heighty;
            scalex=(width+0.0)/widthx;
            offsety=(heightreal-height)/2;
            offsetx=(widthreal-width)/2;
        }
        
        
        update();
        if(!scripting)script.run();
        repaint();

        try{
            Thread.sleep(16);
        }
        catch(Exception e){}

       }
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(offsetx-(int)(camera.camerax*scalex),offsety);
        g2.scale(scalex,scaley);
        currentWorld.draw(g2);
        if(dialogueactive){
            if(tframe<tdummy){
             textLoader(g2, tname, ttext.substring(0,(ttext.length()*tframe)/tdummy), tx, ty, twidth, tfont);
             tframe+=16;}
            else{
                dialogueactive=false;
                scripting=false;
            }
        }
    }
    public void update(){
        currentWorld.update();
        camera.update();
    }
        public void textLoader(Graphics2D g2, String name, String s,int x, int y, int width, Font font) {
            g2.setFont(font);
            FontMetrics fm = g2.getFontMetrics();
            
            g2.drawString(name+": ",x,y);
            
            String text = s;
        
            String[] words = text.split(" ");
            String line = "";
        
            int lines = 0;
        
            // First pass: find how many lines
            for (String word : words) {
                String test = line.isEmpty() ? word : line + " " + word;
        
                if (fm.stringWidth(test) > width) {
                    lines++;
                    line = word;
                } else {
                    line = test;
                }
            }
        
            if (!line.isEmpty())
                lines++;
        
            int lineHeight = fm.getHeight();
            int totalHeight = lines * lineHeight;
        
            // y = center of entire text block
            int drawY = y+fm.getHeight()+10 ;
        
            // Second pass: actually draw
            line = "";
        
            for (String word : words) {
                String test = line.isEmpty() ? word : line + " " + word;
        
                if (fm.stringWidth(test) > width) {
                    int lineWidth = fm.stringWidth(line);
                    int drawX = x - lineWidth / 2;
        
                    g2.drawString(line, x, drawY);
        
                    drawY += lineHeight;
                    line = word;
                } else {
                    line = test;
                }
            }  
            if (!line.isEmpty()) {
                int lineWidth = fm.stringWidth(line);
                int drawX = x - lineWidth / 2;
        
                g2.drawString(line, x, drawY);
            }
    }
}