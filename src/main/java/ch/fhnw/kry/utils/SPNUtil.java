package ch.fhnw.kry.utils;

/**
 * This class holds all static mappings like the given SBox and the permutation matrix
 */
public class SPNUtil {

    // x    | 0 1 2 3 4 5 6 7 8 9 A B C D E F
    // S(x) | E 4 D 1 2 F B 8 3 A 6 C 5 9 0 7
    public static int getSBox(int input) {
        return switch (input) {
            case 0x0 -> 0xE;
            case 0x1 -> 0x4;
            case 0x2 -> 0xD;
            case 0x3 -> 0x1;
            case 0x4 -> 0x2;
            case 0x5 -> 0xF;
            case 0x6 -> 0xB;
            case 0x7 -> 0x8;
            case 0x8 -> 0x3;
            case 0x9 -> 0xA;
            case 0xA -> 0x6;
            case 0xB -> 0xC;
            case 0xC -> 0x5;
            case 0xD -> 0x9;
            case 0xE -> 0x0;
            case 0xF -> 0x7;
            default -> throw new IllegalArgumentException("Not valid");
        };
    }

    // x    | 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15
    // β(x) | 0 4 8 12 1 5 9 13 2 6 10 14 3 7 11 15
    public static int getPermutationMapping(int pos) {
        return switch (pos) {
            case 0 -> 0;
            case 1 -> 4;
            case 2 -> 8;
            case 3 -> 12;
            case 4 -> 1;
            case 5 -> 5;
            case 6 -> 9;
            case 7 -> 13;
            case 8 -> 2;
            case 9 -> 6;
            case 10 -> 10;
            case 11 -> 14;
            case 12 -> 3;
            case 13 -> 7;
            case 14 -> 11;
            case 15 -> 15;
            default -> throw new IllegalArgumentException("Invalid input");
        };
    }

}
