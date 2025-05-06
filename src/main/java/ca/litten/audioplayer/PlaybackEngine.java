package ca.litten.audioplayer;

import ca.litten.audioplayer.audio.AudioDecoder;
import ca.litten.audioplayer.audio.AudioMetadataProcessor;
import ca.litten.audioplayer.audio.AudioSample;
import org.fusesource.jansi.AnsiConsole;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class PlaybackEngine {
    private LambdaAssist onNext, onPrev, onProgressUpdate, onPlay, onPause;
    
    private SourceDataLine line;
    
    private AudioDecoder decoder;
    
    private DecodingThread decodingThread;
    
    private boolean paused = true;
    
    public PlaybackEngine() {
        onPlay = input -> pause();
        onPause = input -> {};
        onNext = input -> {};
        onPrev = input -> {};
        onProgressUpdate = input -> {};
    }
    
    public void setAudioFile(AudioDecoder decoder) {
        this.decoder = decoder;
        try {
            line = AudioSystem.getSourceDataLine(decoder.getAudioOutputFormat());
            line.open();
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void killPlaybackEngine() {
        decodingThread.alive = false;
        while (decodingThread.isAlive()) ExceptionIgnore.ignoreExc(() -> decodingThread.join());
    }
    
    public void preparePlaybackEngine() {
        decodingThread = new DecodingThread();
        decodingThread.start();
    }
    
    public void pause() {
        paused = true;
        onPause.exec(null);
        line.stop();
    }
    
    public void play() {
        line.start();
        paused = false;
        onPlay.exec(null);
    }
    
    public enum Callback {
        next,
        prev,
        progressUpdate,
        play,
        pause
    }
    
    public void setCallback(Callback callback, LambdaAssist func) {
        switch (callback) {
            case progressUpdate:
                onProgressUpdate = func;
                break;
            case next:
                onNext = func;
                break;
            case prev:
                onPrev = func;
                break;
            case play:
                onPlay = func;
                break;
            case pause:
                onPause = func;
                break;
        }
    }
    
    private class DecodingThread extends Thread {
        public volatile boolean alive = true;
        
        @Override
        public void run() {
            Thread.currentThread().setPriority(MAX_PRIORITY);
            AudioSample sample;
            while (alive) {
                while (paused || decoder == null) {
                    ExceptionIgnore.ignoreExc(() -> sleep(1, 0));
                }
                sample = decoder.getNextSample();
                // TODO: downsample
                line.write(sample.getData(), 0, sample.getLength());
                onProgressUpdate.exec(decoder.getCurrentTime());
                if (!decoder.moreSamples()) pause();
            }
        }
    }
}
