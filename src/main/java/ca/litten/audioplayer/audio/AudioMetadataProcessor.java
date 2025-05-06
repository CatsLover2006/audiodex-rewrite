package ca.litten.audioplayer.audio;

import java.awt.image.BufferedImage;

public interface AudioMetadataProcessor {
    AudioDecoder getDecoder();
    ID3Container getID3();
    void setID3(ID3Container container);
    BufferedImage getArtwork();
    void setArtwork(BufferedImage art);
}
