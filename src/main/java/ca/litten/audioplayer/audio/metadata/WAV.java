package ca.litten.audioplayer.audio.metadata;

import ca.litten.audioplayer.audio.AudioDecoder;
import ca.litten.audioplayer.audio.AudioMetadataProcessor;
import ca.litten.audioplayer.audio.ID3Container;

import java.awt.image.BufferedImage;
import java.io.File;

public class WAV implements AudioMetadataProcessor {
    
    private File file;
    
    public WAV(File file) {
        this.file = file;
    }
    
    public AudioDecoder getDecoder() {
        return new ca.litten.audioplayer.audio.decoder.WAV(file);
    }
    
    public ID3Container getID3() {
        return null;
    }
    
    public void setID3(ID3Container container) {}
    
    public BufferedImage getArtwork() {
        return null;
    }
    
    public void setArtwork(BufferedImage art) {}
}
