package ca.litten.audioplayer.ui;

import ca.litten.audioplayer.PlaybackEngine;

public interface UI {
    boolean continueExecution();
    void run(String[] args);
    void setPlaybackEngine(PlaybackEngine engine);
}
