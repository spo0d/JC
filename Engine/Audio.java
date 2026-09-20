package Engine;

import javazoom.jl.player.Player;
import java.io.InputStream;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Audio {

    private Player bgMusic;

    // ONE executor / ONE audio thread
    private final ExecutorService kanye = Executors.newSingleThreadExecutor();

    private int currentFrame = 0;

    private volatile boolean isPlaying = false;
    private volatile boolean running = true;

    private volatile String currentPath;
    private volatile String requestedPath;

    public Audio() {

        // Kanye starts ONCE and stays alive
        kanye.submit(() -> {

            while (running) {

                try {

                    // Nothing to play
                    if (!isPlaying || currentPath == null) {
                        Thread.sleep(10);
                        continue;
                    }

                    // Remember which song we are playing
                    String path = currentPath;

                    InputStream is =
                            Audio.class.getResourceAsStream(path);

                    if (is == null) {
                        System.out.println("Audio not found: " + path);
                        isPlaying = false;
                        continue;
                    }

                    Player player = new Player(is);
                    bgMusic = player;

                    // Play until song ends OR another song is requested
                    player.play();

                    player.close();

                    if (bgMusic == player) {
                        bgMusic = null;
                    }

                } catch (Exception e) {

                    if (running) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public synchronized void startBGSong(String path) {
        if (isPlaying && path.equals(currentPath)) {
            return;
        }
        if (bgMusic != null) {
            bgMusic.close();
            bgMusic = null;
        }
        currentPath = path;
        requestedPath = path;
        isPlaying = true;
    }
    public synchronized void stopBGSong() {
        isPlaying = false;
        currentPath = null;
        requestedPath = null;
        if (bgMusic != null) {
            bgMusic.close();
            bgMusic = null;
        }
    }
    public void shutdown() {
        running = false;
        isPlaying = false;
        currentPath = null;
        if (bgMusic != null) {
            bgMusic.close();
            bgMusic = null;
        }
        kanye.shutdownNow();
    }
}