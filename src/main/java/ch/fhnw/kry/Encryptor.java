package ch.fhnw.kry;

import ch.fhnw.kry.utils.SPNUtil;

public class Encryptor {
    private final int r; // Holds the given round parameter during initialization
    private final int m; // Holds the given m parameter during initialization
    private final int n; // Holds the given n parameter during initialization
    private final int key; // Holds the key round parameter during initialization

    public Encryptor(int m, int n, int r, int key) {
        this.m = m;
        this.n = n;
        this.r = r;
        this.key = key;
    }

    /**
     * Encrypt a given block according to the "Substitutionspermutationsnetzwerk" encryption
     * 1. "Weissschritt"
     * 2. for each round-1: Apply SBox, apply bitPermutation, XOR with given key
     * 3. shortened round
     * @param block: current block to decrypt
     * @return decrypted block
     */
    public int encryptBlock(int block) {
        // Define counter for current round
        int blockRound = 0;
        // Apply "Weissschritt" by XOR the round-key with the block
        int x = block ^ getKeyForRound(blockRound);
        blockRound++;

        while (blockRound < r) {
            x = applySBox(x); // Apply sBox to given x variable
            x = applyBitPermutation(x); // Apply bitPermutation to given x variable
            x ^= getKeyForRound(blockRound); // XOR round-key with given x variable
            blockRound++;
        }
        // At the end the shortened round will be applied too
        x = applySBox(x); // Apply sBox to given x variable
        // XOR key for round
        x = x ^ getKeyForRound(blockRound); // XOR round-key with given x variable

        return x;
    }

    /**
     * Applies the sBox to a block of length m * n
     * @param block: current x during the encryption process
     * @return block with applied sBox
     */
    private int applySBox(int block) {
        int newVal = 0;
        // Split block to 4 bit blocks and iterate until end of given block
        for (int i = 0; i < m * n; i += 4) {
            // apply sBox and assign it to the resulting number
            // With & 0b1111 only the last four digits will by given into the sBox
            // The result will be shifted to the right position
            newVal = newVal | (SPNUtil.getSBox(block & 0b1111) << i);
            // remove the bits that were processed by the sBox
            block = block >> 4;
        }
        return newVal;
    }

    /**
     * Applies the bit-permutation to a block of length m * n
     *
     * @param block: current x during the encryption process
     * @return block with applied bit-permutation
     */
    private int applyBitPermutation(int block) {
        int result = 0;

        // Iterate through every position of the block
        for (int i = 0; i < m * n; i++) {

            // Get new position of current position
            int newPosition = SPNUtil.getPermutationMapping(i);
            // Shift current iterating position at the end and extract the least significant bit (LSB).
            int bit = (block >> i) & 1;
            // Shift bit to the newPosition and applies it to the result
            result |= (bit << newPosition);
        }

        return result;
    }


    /**
     * For each round new round key will be extracted from the actual key
     * @param currentRound current round 0 <= currentRound <= r
     * @return the key of the current round
     */
    private int getKeyForRound(int currentRound) {
        // Get the last 16 bits of the key shifted by "4 * current round"
        return (key >> (16 - 4 * currentRound)) & 0b1111_1111_1111_1111;
    }
}
