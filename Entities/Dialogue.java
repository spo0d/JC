package Entities;


public class Dialogue
{
    public int x,y, width,tframe,tdummy;
    public String name,text;
    public boolean active = true;
    public Dialogue(int x,int y,int width, String name, String text, int tdummy){
      this.x=x;
      this.y=y;
      this.width=width;
      this.name=name;
      this.text=text;
      this.tdummy=tdummy;
      this.tframe=0;
    }
    public void update(){
        if(tframe<tdummy)tframe++;
    }
    public boolean isFinishedTyping(){
        return tframe>=tdummy;
    }
    public String visibleText(){
        if(isFinishedTyping()) return text;
        return text.substring(0,text.length()*tframe/tdummy);
    }
    public void skipToEnd(){
        tframe=tdummy;
    }
    
}
