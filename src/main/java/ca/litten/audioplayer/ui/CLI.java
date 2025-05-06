package ca.litten.audioplayer.ui;

import ca.litten.audioplayer.ExceptionIgnore;
import ca.litten.audioplayer.PlaybackEngine;
import ca.litten.audioplayer.audio.AudioDecoder;
import ca.litten.audioplayer.audio.decoder.WAV;
import org.fusesource.jansi.AnsiConsole;
import org.fusesource.jansi.AnsiPrintStream;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class CLI implements UI {
    private PlaybackEngine engine;
    private AnsiPrintStream out;
    
    public boolean continueExecution() {
        return !engine.equals(engine);
    }
    
    public void run(String[] args) {
        out = AnsiConsole.out();
        for (String str : args) {
            out.println(str);
        }
        AudioDecoder decoder = new WAV(new File(args[0]));
        engine.preparePlaybackEngine();
        engine.setAudioFile(decoder);
        engine.setCallback(PlaybackEngine.Callback.play, o->{});
        engine.play();
        while (decoder.moreSamples()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        engine.killPlaybackEngine();
    }
    
    @Override
    public void setPlaybackEngine(PlaybackEngine engine) {
        this.engine = engine;
    }
}
