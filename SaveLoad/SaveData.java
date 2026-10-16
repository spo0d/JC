package SaveLoad;

import java.util.ArrayList;
public class SaveData{
    public int saveVersion = 1;
    public short scene;

    public PlayerData player = new PlayerData();
    public WorldData worldData = new WorldData();

    public ArrayList<NPCData> npcs = new ArrayList<NPCData>();
}