package ch.fhnw.kry;

public class Decryptor {
    // The Decryptor uses the encryption function
    private final Encryptor enc;

    // The Decryptor stores all the cipher blocks given from the input file
    private final int[] cipherBlocks;

    public Decryptor(Encryptor enc, int[] cipherBlocks) {
        this.enc = enc;
        this.cipherBlocks = cipherBlocks;
    }

    /**
     * Decrypt block ciphers with CTR (randomized counter) modi
     *
     * @return results the decrypted blocks as an array
     */
    public int[] decrypt() {
        int blockCount = cipherBlocks.length;
        // Array for the decrypted blocks -> result array
        var decrypted = new int[blockCount - 1];

        // The first block of the cipher text is always the rand
        var rand = cipherBlocks[0];

        // Iterate through all blocks
        for (int i = 0; i < blockCount - 1; i++) {
            // decrypted block = encrypted (rand + current index) XOR with the next cipherBlock
            // Equals to e(y_minus_1) (+) y0
            decrypted[i] = enc.encryptBlock(rand + i) ^ cipherBlocks[i + 1];
        }
        return decrypted;
    }
}
