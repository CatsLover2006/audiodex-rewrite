package ca.litten.audioplayer.audio.decoder;

import ca.litten.audioplayer.ExceptionIgnore;
import ca.litten.audioplayer.audio.AudioDecoder;
import ca.litten.audioplayer.audio.AudioSample;
import org.fusesource.jansi.AnsiConsole;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;

public class WAV implements AudioDecoder {
    AudioFormat format;
    double duration;
    double bytesPerSecond;
    private int numberBytesRead = 0;
    private long bytesPlayed = 0;
    private long lastMark;
    private int markLength = -1;
    private AudioInputStream in;
    private final byte[] data = new byte[4096];
    private File file;
    
    public WAV(File file) {
        try {
            this.file = file;
            in = AudioSystem.getAudioInputStream(file);
            format = in.getFormat();
            double audioFrameRate = format.getFrameRate();
            long audioFileLength = file.length();
            int frameSize = format.getFrameSize();
            bytesPerSecond = frameSize * audioFrameRate;
            if (in.markSupported()) {
                markLength = (int) (10 * bytesPerSecond);
                in.mark(markLength);
                lastMark = 0;
            }
            duration = audioFileLength / (frameSize * audioFrameRate);
            AnsiConsole.out().println("WAV/PCM decoder ready!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public AudioSample getNextSample() {
        numberBytesRead = -2;
        while (moreSamples()) {
            ExceptionIgnore.ignoreExc(() -> numberBytesRead = in.read(data, 0, data.length));
            if (numberBytesRead < 0) {
                continue;
            } // Yes I have to do this to track time
            bytesPlayed += numberBytesRead;
            if (markLength != -1 && numberBytesRead > lastMark + markLength) {
                in.mark(markLength);
                lastMark = bytesPlayed;
            }
            return new AudioSample(data, numberBytesRead);
        }
        return new AudioSample();
    }
    
    public void goToTime(double time) {
        ExceptionIgnore.ignoreExc(() -> {
            if (markLength != -1)  {
                in.reset();
                bytesPlayed = lastMark;
            }
            if (getCurrentTime() > time) in = AudioSystem.getAudioInputStream(file);
            long toSkip = (long) (time * bytesPerSecond) - bytesPlayed;
            long skipped;
            while (toSkip != 0) {
                skipped = in.skip(toSkip);
                toSkip -= skipped;
                bytesPlayed += skipped;
                if (skipped == 0) {
                    numberBytesRead = -1;
                    return;
                }
            }
        });
    }
    
    @Override
    public double getCurrentTime() {
        return bytesPlayed / bytesPerSecond;
    }
    
    @Override
    public double getFileDuration() {
        return duration;
    }
    
    @Override
    public AudioFormat getAudioOutputFormat() {
        return format;
    }
    
    @Override
    public boolean moreSamples() {
        return numberBytesRead != -1;
    }
}
