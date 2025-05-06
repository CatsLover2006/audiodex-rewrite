package ca.litten.audioplayer.audio;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Container for ID3 data
public class ID3Container {
    public final static Map<String, String> validTags = new HashMap<>();
    
    static {
        validTags.put("title", "Track Name");
        validTags.put("title-sort", "Track Name (Sorting Order)");
        validTags.put("artist", "Song Artist");
        validTags.put("artist-sort", "Song Artist (Sorting Order)");
        validTags.put("album", "Album");
        validTags.put("album-sort", "Album (Sorting Order)");
        validTags.put("album-artist", "Album Artist");
        validTags.put("album-artist-sort", "Album Artist (Sorting Order)");
        validTags.put("album-compilation", "Album is Compilation");
        validTags.put("genre", "Genre");
        validTags.put("year", "Year");
        validTags.put("track", "Track Number");
        validTags.put("track-count", "Total Track Count");
        validTags.put("disc", "Disc Number");
        validTags.put("disc-count", "Total Disc Count");
        validTags.put("comment", "Comment");
        validTags.put("bpm", "BPM");
        validTags.put("fbpm", "Floating-Point BPM");
        validTags.put("composer", "Composer");
        validTags.put("publisher", "Publisher");
        validTags.put("copyright", "Copyright");
    }
    
    private final Map<String, Object> id3data;
    
    public ID3Container() {
        id3data = new HashMap<>();
    }

    public ID3Container(JSONObject obj) {
        id3data = new HashMap<>();
        id3data.putAll(obj.toMap());
    }
    
    public Object getID3Data(String key) throws InvalidTagException {
        if (!validTags.containsKey(key)) throw new InvalidTagException(key);
        return id3data.get(key);
    }
    
    public void setID3Data(String key, long value) throws InvalidTagException {
        if (!validTags.containsKey(key)) throw new InvalidTagException(key);
        id3data.put(key, value);
    }

    public void setID3Data(String key, Object value) throws InvalidTagException {
        if (!validTags.containsKey(key)) throw new InvalidTagException(key);
        if (value == null || value.toString().isEmpty()) {
            return;
        }
        id3data.put(key, value);
    }

    public JSONObject encode() {
        return new JSONObject(id3data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id3data.hashCode(), id3data.size());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ID3Container) {
            return obj.hashCode() == hashCode();
        }
        return false;
    }
}
