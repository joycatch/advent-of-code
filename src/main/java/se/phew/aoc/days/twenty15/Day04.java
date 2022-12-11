package se.phew.aoc.days.twenty15;

import se.phew.aoc.days.Challenge;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Day04 extends Challenge {

    public Day04() {
        super(false);

        String secret = "bgvyzdsv";
        boolean part1Found = false;
        for (int i = 1; i < Integer.MAX_VALUE; i++) {
            String hash = md5(secret + i);
            if (hash.startsWith("00000") && !part1Found) {
                printAnswer(1, i);
                part1Found = true;
            }
            if (hash.startsWith("000000")) {
                printAnswer(2, i);
                break;
            }
        }
    }

    private String md5(String temp) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger bigInt = new BigInteger(1, md.digest(temp.getBytes()));
            String result = bigInt.toString(16);
            while (result.length() < 32) {
                result = "0" + result;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
