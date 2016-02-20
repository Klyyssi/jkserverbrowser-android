package com.thelairofmarkus.markus.jk2serverbrowser;

import java.util.Arrays;

/**
 * Created by markus on 20.2.2016.
 */
public class ByteHelper {
    /**
     * Search for the first index of 'ofByte' in a byte array.
     *
     * @param array the array to look for the 'ofByte'.
     * @param ofByte the first index of this byte
     * @return the index of the first byte if found, -1 otherwise
     */
    public static int firstIndex(byte[] array, byte ofByte) {
        for (int i = 0; i < array.length; i++) {
            if ((array[i] & 0xff) == ofByte) {
                return i;
            }
        }
        return -1;
    }

    public static byte[] trim(byte[] bytes) {
        int lastNonZero = 0;

        for (int i = bytes.length - 1; i > 0; i--) {
            if (bytes[i] != 0x00) {
                lastNonZero = i + 1;
                break;
            }
        }

        return Arrays.copyOf(bytes, lastNonZero);
    }
}
