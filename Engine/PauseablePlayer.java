package Engine;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.InputStream;

public class PauseablePlayer extends AdvancedPlayer {

    private Bitstream bitstream;
    private int currentFrame = 0;

    public PauseablePlayer(InputStream input)
            throws JavaLayerException {

        super(input);

        // AdvancedPlayer has its own Bitstream internally,
        // so this constructor is only here for compatibility.
    }

    public int getCurrentFrame() {
        return currentFrame;
    }
}