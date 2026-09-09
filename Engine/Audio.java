package Engine;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javazoom.jl.player.Player;
import java.net.URL;

public class Audio{
    private javazoom.jl.player.Player bgMusic;
    private final ExecutorService kanye = Executors.newSingleThreadExecutor();
    private volatile boolean isPlaying = false;
    private String currentPath;
    public void startBGSong(String path){
        this.currentPath=path;
        this.isPlaying=true;
        
        kanye.submit(()->
        {
            while(isPlaying){
            try{
                bgMusic = new javazoom.jl.player.Player(Audio.class.getResourceAsStream(currentPath));  
                bgMusic.play();
            }
            catch(Exception e){
                e.printStackTrace();
                isPlaying=false;
            }
           }
        });
    }
    public void stopBGSong(){
        isPlaying=false;
        if(bgMusic!=null){
            bgMusic.close();
        }
    }
    public void shutdown() {
        kanye.shutdown();
        if (bgMusic != null) {
            bgMusic.close();
        }
    }
}