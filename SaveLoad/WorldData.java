package SaveLoad;
import java.util.HashMap;
public class WorldData{
    public int stage;
    public int scriptBlock;
    public int lineSayNo;
    public HashMap<String,Integer> extra = new HashMap<>(); // for rare scene-specific stuff
}