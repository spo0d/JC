package Engine;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;

import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Audio {

    private final ExecutorService kanye =
            Executors.newSingleThreadExecutor();

    private volatile boolean running = true;
    private volatile boolean isPlaying = false;
    private volatile boolean paused = false;

    private volatile String currentPath;

    private Bitstream bitstream;
    private Decoder decoder;
    private AudioDevice audioDevice;

    public Audio() {

        kanye.submit(() -> {

            while (running) {

                try {

                    if (!isPlaying || currentPath == null) {
                        Thread.sleep(5);
                        continue;
                    }

                    // Create audio stream when starting/resuming
                    if (bitstream == null) {

                        InputStream is =
                                Audio.class.getResourceAsStream(
                                        currentPath);

                        if (is == null) {
                            System.out.println(
                                    "Audio not found: "
                                            + currentPath);

                            isPlaying = false;
                            continue;
                        }

                        bitstream = new Bitstream(is);
                        decoder = new Decoder();

                        openDevice();
                    }

                    // PAUSED
                    if (paused) {
                        Thread.sleep(5);
                        continue;
                    }

                    Header header = bitstream.readFrame();

                    // Song ended
                    if (header == null) {

                        closeAudio();

                        isPlaying = false;
                        paused = false;

                        continue;
                    }

                    SampleBuffer output =
                            (SampleBuffer) decoder.decodeFrame(
                                    header,
                                    bitstream);

                    /*
                     * Check pause AGAIN before sending
                     * the decoded frame to the speaker.
                     */
                    if (paused) {
                        bitstream.closeFrame();
                        continue;
                    }

                    audioDevice.write(
                            output.getBuffer(),
                            0,
                            output.getBufferLength());

                    bitstream.closeFrame();

                } catch (Exception e) {

                    if (running) {
                        e.printStackTrace();
                    }

                    closeAudio();
                }
            }
        });
    }


    // =========================
    // OPEN DEVICE
    // =========================

    private void openDevice() throws Exception {

        audioDevice =
                FactoryRegistry.systemRegistry()
                        .createAudioDevice();

        audioDevice.open(decoder);
    }


    // =========================
    // START
    // =========================

    public synchronized void startBGSong(String path) {

        stopBGSong();

        currentPath = path;

        isPlaying = true;
        paused = false;
    }


    // =========================
    // PAUSE
    // =========================

    public synchronized void pauseBGSong() {

        if (!isPlaying || paused) {
            return;
        }

        paused = true;

        /*
         * Closing the device immediately kills any
         * buffered audio that is waiting to play.
         */
        if (audioDevice != null) {

            try {
                audioDevice.close();
            } catch (Exception ignored) {
            }

            audioDevice = null;
        }
    }


    // =========================
    // RESUME
    // =========================

    public synchronized void resumeBGSong() {

        if (!isPlaying || !paused) {
            return;
        }

        try {

            /*
             * Re-open the SAME decoder/device.
             *
             * The Bitstream is still at the paused
             * position.
             */
            openDevice();

            paused = false;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // =========================
    // STOP
    // =========================

    public synchronized void stopBGSong() {

        isPlaying = false;
        paused = false;
        currentPath = null;

        closeAudio();
    }


    // =========================
    // CLOSE
    // =========================

    private synchronized void closeAudio() {

        try {
            if (bitstream != null) {
                bitstream.close();
            }
        } catch (Exception ignored) {
        }

        try {
            if (audioDevice != null) {
                audioDevice.close();
            }
        } catch (Exception ignored) {
        }

        bitstream = null;
        decoder = null;
        audioDevice = null;
    }


    // =========================
    // SHUTDOWN
    // =========================

    public synchronized void shutdown() {

        running = false;
        isPlaying = false;
        paused = false;

        closeAudio();

        kanye.shutdownNow();
    }
}