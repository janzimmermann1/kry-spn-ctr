package ch.fhnw.kry;

import ch.fhnw.kry.utils.Constants;

public class Main {

    // CipherText
    public static String INPUT = "00000100110100100000101110111000000000101000111110001110011111110110000001010001010000111010000000010011011001110010101110110000";

    public static void main(String[] args) {
        // Read cipher text
        var cipherTextAsBlocks = readCipherText();
        // Initialize encryptor with static constants
        Encryptor enc = new Encryptor(Constants.m, Constants.n, Constants.r, Constants.KEY);
        // Initialize decryptor with previously created encryptor and cipher block array
        Decryptor dec = new Decryptor(enc, cipherTextAsBlocks);
        // Transform cipher blocks to decrypted blocks by decrypting each block
        var decryptedBlocks = dec.decrypt();
        // Parse the blocks to a binary string
        var binString = blocksToString(decryptedBlocks);
        // Remove padding: 10*
        var unpaddedString = removePadding(binString);
        // Convert unpadded string to a readable ASCII string
        var asciiText = convertBinaryToAscii(unpaddedString);
        System.out.println("Decrypted Text: " + asciiText);
    }

    /**
     * Splits up the cipher text (INPUT) to blocks of size m * n
     * @return int array with cipher blocks
     */
    public static int[] readCipherText() {
        int stepSize = Constants.m * Constants.n; // Define block size
        int length = INPUT.length() / stepSize;
        int[] cipherText = new int[length];
        for (int i = 0; i < INPUT.length(); i += stepSize) {
            String binaryPart = INPUT.substring(i, i + stepSize);
            // Parse binary string to an integer
            cipherText[i / stepSize] = Integer.parseInt(binaryPart, 2);
        }
        return cipherText;
    }

    /**
     * Transform decrypted blocks to a binary string
     * @param decryptedBlocks decrypted blocks of maximum length of m*n bits
     * @return binary string
     */
    public static String blocksToString(int[] decryptedBlocks){
        StringBuilder r = new StringBuilder();
        for (int decryptedBlock : decryptedBlocks) {
            StringBuilder r2 = new StringBuilder(Integer.toBinaryString(decryptedBlock));

            // As long not all m * n bits are filled, a 0 should be prepended
            while (r2.length() % Constants.m * Constants.n != 0) {
                r2.insert(0, "0"); // Prepend 0 to converted binary string
            }
            r.append(r2);
        }
        return r.toString();
    }

    /**
     * The padding at the end should be removed
     * @param paddedString
     * @return unpadded string
     */
    public static String removePadding(String paddedString) {
        // Use a Regex to remove a padding like "100000" at the end of a padded string
        return paddedString.replaceAll("10(0)*$", "");
    }

    /**
     * Convert binary string to an ASCII string
     * @param binaryString binary string without padding
     * @return ASCII string
     */
    public static String convertBinaryToAscii(String binaryString) {
        StringBuilder asciiText = new StringBuilder();

        // Split binary string into blocks of 8 and parse each block to its ascii representation
        for (int i = 0; i < binaryString.length(); i += 8) {
            String eightBits = binaryString.substring(i, Math.min(i + 8, binaryString.length()));

            int decimalValue = Integer.parseInt(eightBits, 2);
            char asciiChar = (char) decimalValue;

            asciiText.append(asciiChar);
        }

        return asciiText.toString();
    }
}
