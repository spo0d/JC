package SaveLoad;


import Entities.Player;
import Entities.NPC;
import java.util.ArrayList;

public class SaveConverter{
    public static PlayerData getPlayerData(Player player){
        PlayerData data = new PlayerData();
        data.name = player.name;
        data.x=player.x;
        data.y=player.y;
        data.lor=player.lorAfter;
        data.sitLevel=player.sitLevel;
        return data;
    }
    public static void applyPlayerData(
            Player player, PlayerData data) {

        player.x = data.x;
        player.y = data.y;
        player.lorAfter = data.lor;
        player.sitLevel = data.sitLevel;
        player.name=data.name;
        
    }
    public static ArrayList<NPCData> getNPCData(ArrayList<NPC> npc){
        ArrayList<NPCData> npcData = new ArrayList<>();
        NPCData dummy = new NPCData();
        for(NPC e: npc){
            dummy.name = e.name;
            dummy.x = e.x;
            dummy.y=e.y;
            dummy.lor=e.lorAfter;
        }
        return npcData;
    }
    // public static void applyNPCData(ArrayList<NPC> npc, ArrayList<NPCData> npcData) {
        // // npc.clear();
        // // NPCData npcDatae;
        // // while(!npcData.isEmpty()){
            // // npcDatae = npcData.remove(0);
            // // npce.name = npcDatae.name;
            // // npce.x = npcDatae.x;
            // // npce.y=npcDatae.y;
            // // npce.lorAfter=npcDatae.lor;
            // // npc.add(npce);
        // // }
    // }
}