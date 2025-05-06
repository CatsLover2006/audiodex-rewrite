package ca.litten.audioplayer.audio;

import java.util.LinkedList;
import java.util.List;

public class AudioSample {
    private byte[] data;
    private int length;

    public AudioSample() {
        new AudioSample(new byte[]{});
    }

    public AudioSample(byte[] data) {
        new AudioSample(data, data.length);
    }

    public AudioSample(byte[] data, int length) {
        this.data = data;
        this.length = length;
    }

    public byte[] getData() {
        return data;
    }

    public int getLength() {
        return length;
    }

    public void reduceBitdepth(int currentSampleBitdepth, boolean bigEndian) {
        int j = 0;
        for (int i = 0; i < data.length; i++) {
            if ((i + (bigEndian ? 1 : 0)) % currentSampleBitdepth != 0) {
                data[j] = data[i];
                j++;
            }
        }
        length = length * (currentSampleBitdepth - 1) / currentSampleBitdepth;
    }
}
