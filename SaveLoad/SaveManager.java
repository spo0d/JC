package SaveLoad;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

public class SaveManager{
    private static final Gson gson = 
                new GsonBuilder().setPrettyPrinting().create();
    public static void save(SaveData data, String path) throws IOException{
        File file= new File(path);
        File parent = file.getParentFile();
        if(parent!=null){
            parent.mkdirs();
        }
        try(FileWriter writer = new FileWriter(file)){
            gson.toJson(data,writer);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static SaveData load(String path) throws IOException {

        File file = new File(path);

        if (!file.exists()) {
            return null;
        }

        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, SaveData.class);
        }
    }
    
    //
    
    public static boolean saveGame(Engine.Game g, String path){
        WorldData w = g.currentWorld.saveWorld();
        if(w==null)return false;
        SaveData d = new SaveData();
        d.scene = g.currentWorld.getSceneID();
        d.player = SaveConverter.getPlayerData(g.player);
        d.worldData=w;
        try{
            save(d,path);
            return true;
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }
    }
    public static boolean loadGame(Engine.Game g, String path){
        SaveData d;
        try{
            d=load(path);
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }
        if(d==null||d.player==null||d.worldData==null) return false;
        g.setActScene(d.scene);
        if(g.currentWorld.getSceneID()!=d.scene)return false;
        SaveConverter.applyPlayerData(g.player,d.player);
        g.currentWorld.loadWorld(d.worldData);
        return true;
    }
}
