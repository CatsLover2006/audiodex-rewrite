package ca.litten.audioplayer.ui;

import ca.litten.audioplayer.PlaybackEngine;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Main {
    private static UI ui;
    private static PlaybackEngine engine;
    
    public static void main(String[] args) {
        engine = new PlaybackEngine();
        if (Arrays.asList(args).contains("--cli")) {
            ui = new CLI();
            args = (String[]) Arrays.stream(args).filter(str -> !str.equals("--cli")).toArray();
        } else {
            // TODO: GUI
            ui = new CLI();
        }
        ui.setPlaybackEngine(engine);
        ui.run(args);
        while (ui.continueExecution()) {
            ui.run(new String[]{});
        }
    }
}
