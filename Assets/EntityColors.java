package Assets;

import java.util.HashMap;
import java.awt.Color;
public class EntityColors{
    private HashMap<String, Color> entityColors = new HashMap<>();
    public EntityColors(){
        //entityColors.put();
    }
    public Color eColors(String s){
        return entityColors.get(s);
    }
}