package se.phew.aoc.days.twenty20;

import se.phew.aoc.days.Challenge;

public class Day25 extends Challenge {

    public Day25() {
        super(false);

        long cardPublicKey = (isTest ? 5764801 : Long.parseLong(lines.get(0)));
        long doorPublicKey = (isTest ? 17807724 : Long.parseLong(lines.get(1)));

        int cardLoopSize = findLoopSize(7, cardPublicKey);
        int doorLoopSize = findLoopSize(7, doorPublicKey);

        long encryptionKey = calculateEncryptionKey(cardPublicKey, doorLoopSize);
        long encryptionKey2 = calculateEncryptionKey(doorPublicKey, cardLoopSize);

        if (encryptionKey == encryptionKey2) {
            printAnswer(1, encryptionKey);
        } else {
            print("Could not figure out the encryption key...?");
        }
    }

    public int findLoopSize(int subjectNumber, long publicKey) {
        int transformed = 1;
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            transformed *= subjectNumber;
            transformed = transformed % 20201227;
            if (transformed == publicKey) {
                return i;
            }
        }
        return 0;
    }

    public long calculateEncryptionKey(long subjectNumber, int loopSize) {
        long result = 1;
        for (int i = 0; i < loopSize; i++) {
            result *= subjectNumber;
            result = result % 20201227;
        }
        return result;
    }
}
