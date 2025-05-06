package ca.litten.audioplayer.audio;

public class InvalidTagException extends Exception {
    public InvalidTagException (String tag) {
        super("Invalid ID3 tag: " + tag);
    }
}
