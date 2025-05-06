package ca.litten.audioplayer.audio;

import javax.sound.sampled.AudioFormat;

public interface AudioDecoder {
    AudioSample getNextSample();
    void goToTime(double time);
    double getCurrentTime();
    double getFileDuration();
    AudioFormat getAudioOutputFormat();
    boolean moreSamples();
}
