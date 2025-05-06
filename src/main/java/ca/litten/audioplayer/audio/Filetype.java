package ca.litten.audioplayer.audio;

public enum Filetype {
    AIFF(null),
    WAV(null);
    public final Class<AudioMetadataProcessor> metadataProcessor;
    Filetype(Class<AudioMetadataProcessor> metadataProcessor) {
        this.metadataProcessor = metadataProcessor;
    }
}
