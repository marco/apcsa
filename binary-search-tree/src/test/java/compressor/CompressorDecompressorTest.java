package compressor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class CompressorDecompressorTest {
    @Test
    public void testCompressDecompress1() throws UnsupportedEncodingException {
        String sampleString = "Hello, world";
        byte[] compressed = Compressor.compressString(sampleString);

        assertFalse(
            "Compressed version should be different from sample.",
            new String(compressed).equals(sampleString)
        );

        String decompressed = Decompressor.decompressBytes(compressed);

        assertEquals("Sample and decompressed should be the same.", sampleString, decompressed);
    }

    @Test
    public void testCompressDecompress2() throws UnsupportedEncodingException {
        String sampleString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit posuere.";
        byte[] compressed = Compressor.compressString(sampleString);

        assertFalse(
            "Compressed version should be different from sample.",
            new String(compressed).equals(sampleString)
        );

        String decompressed = Decompressor.decompressBytes(compressed);

        assertEquals("Sample and decompressed should be the same.", sampleString, decompressed);
    }
}
