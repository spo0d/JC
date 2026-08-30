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
import java.util.ArrayList;

public class Game extends JPanel implements Runnable{
    public Input keyboard;
    Camera camera;
    public Mouse mouse;
    public Player player;
    public Worlds.World currentWorld;
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
    Font font;
    
    //world check menu
    public boolean menuCheck;
    
    public Scripts.Script script;
    public String scriaddy;
    public boolean scripting;
    public int tindex;
    public boolean interactPressed;
    public ArrayList<Entities.Dialogue> dialogues = new ArrayList<>();
    public Game(){
        scriaddy="assetsfile/scripts/menu.txt";
        font=new Font("Arial", Font.PLAIN, 35);;
        script = new Script(this, scriaddy);
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
        player = new Player(keyboard,mouse);    
        camera=new Camera(player,this);
        currentWorld = new Worlds.Menu(mouse,keyboard, this,player);
        
        
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
        script.run();
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
        
        java.awt.geom.AffineTransform oldTransform = g2.getTransform();
        
        g2.translate(offsetx-(int)(camera.camerax*scalex),offsety);
        g2.scale(scalex,scaley);
        currentWorld.draw(g2);
        if(menuCheck){
            g2.setColor(Color.WHITE);
            g2.fillRect(0,590,widthx,135);
        }
        
        
        g2.setTransform(oldTransform);
        g2.translate(offsetx, offsety); 
        g2.scale(scalex, scaley);
        for(Entities.Dialogue d : dialogues){
         if(d.active) textLoader(g2, d, font);
        }
    }
    boolean prevEnter;  
    public void update(){
        interactPressed = keyboard.move[4] && !prevEnter; // true only the exact frame Enter goes down
        prevEnter = keyboard.move[4];
        currentWorld.update();
        camera.update();
        for(Dialogue d: dialogues){
            if(d.active) d.update();
        }
    }
    public void textLoader(Graphics2D g2, Entities.Dialogue d, Font font) {
        
        g2.setFont(font);
        g2.setColor(Color.BLACK);
        FontMetrics fm = g2.getFontMetrics();
        int lineHeight = fm.getHeight();
        
        // Draw the character name relative to the top-left boundary
        g2.drawString(d.name + ": ", d.x, d.y + fm.getAscent());
        
        // Step 1: Pre-calculate word wrapping over the FULL text
        // This stops words from jumping around while typing out
        String[] words = d.text.split(" ");
        ArrayList<String> lines = new ArrayList<>();
        String currentLine = "";
    
        for (String word : words) {
            String testLine = currentLine.isEmpty() ? word : currentLine + " " + word;
            if (fm.stringWidth(testLine) > width) {
                lines.add(currentLine);
                currentLine = word;
            } else {
                currentLine = testLine;
            }
        }
        if (!currentLine.isEmpty()) {
            lines.add(currentLine);
        }
    
        // Step 2: Track characters for the typewriter animation
        String visible = d.visibleText();
        int allowed = visible.length(),processed=0;
        
        // Position first line of text right below the speaker's name
        int drawY = d.y + lineHeight + 40; 
    
        for (String line : lines) {
            if (processed >= allowed) break;
    
            // Slice line fragment based on typewriter progress
            String lineToDraw = line;
            if (processed + line.length() > allowed) {
                lineToDraw = line.substring(0, allowed - processed);
            }
            processed += line.length() + 1; // +1 accounts for the split space character
    
            // FIX: Draw strictly from the left bound 'x' instead of centering it!
            g2.drawString(lineToDraw, d.x, drawY);
            
            // Push the next line down
            drawY += lineHeight; 
        }
    }
}
