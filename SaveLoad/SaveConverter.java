package SaveLoad;

import Entities.*;
import java.util.ArrayList;
import java.util.HashMap;

public class SaveConverter{
    public static PlayerData getPlayerData(Player player){
        PlayerData data = new PlayerData();
        data.name = player.name;
        data.x=player.x;
        data.y=player.y;
        data.lor=player.lorAfter;
        data.sitLevel=player.sitLevel;data.hlcolor=new int[]{player.hlcolour.getRed(),player.hlcolour.getGreen(),player.hlcolour.getBlue()};
        return data;
    }

    public static void applyPlayerData(
    Player player, PlayerData data) {

        player.x = data.x;
        player.y = data.y;
        player.lorAfter = data.lor;
        player.sitLevel = data.sitLevel;
        player.name=data.name;
        player.setColor(data.hlcolor);
    }

    public static ArrayList<NPCData> getNPCData(ArrayList<Entities.Entity> entities){
        ArrayList<NPCData> npcData = new ArrayList<>();
        for(Entities.Entity e: entities){
            if(e instanceof NPC){
                NPC npc = (NPC) e;
                NPCData dummy = new NPCData();
                dummy.name = e.name;
                dummy.x = e.x;
                dummy.y=e.y;
                dummy.lor=e.lorAfter;
                npcData.add(dummy);
            }
        }
        return npcData;
    }

    public static void applyNPCData(ArrayList<Entities.Entity> entities, ArrayList<NPCData> npcData) {
        if(npcData == null) return;
        HashMap<String, NPCData> byName = new HashMap<>();

        for (NPCData data : npcData) {
            if (data.name != null) {
                byName.put(data.name, data);
            }
        }

        for (Entities.Entity e : entities) {
            if (e instanceof NPC) {
                NPC npc = (NPC) e;
                NPCData data = byName.get(npc.name);

                if (data != null) {
                    npc.x = data.x;
                    npc.y = data.y;
                    npc.lorAfter = data.lor;
                }
            }
        }
    }
}
