package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

import java.util.HashMap;

public class Day08 extends Challenge {

    public Day08() {
        super();

        int part1 = 0;
        int part2 = 0;
        for (String line : lines) {
            String[] split = line.split(" \\| ");
            String[] digits = split[1].trim().split(" ");
            for (String digit : digits) {
                part1 += (digit.length() == 2 || digit.length() == 4 || digit.length() == 3 || digit.length() == 7) ? 1 : 0;
            }
            HashMap<Character, Integer> map = new HashMap<>();
            for (Character c : split[0].replaceAll(" ", "").toCharArray()) {
                map.put(c, map.get(c) == null ? 1 : map.get(c) + 1);
            }
            part2 += digit(digits[0], map) * 1000 + digit(digits[1], map) * 100 + digit(digits[2], map) * 10 + digit(digits[3], map);
        }
        printAnswer(1, part1);
        printAnswer(2, part2);
    }

    private int digit(String digit, HashMap<Character, Integer> map) {
        int charSum = 0;
        for (Character c : digit.toCharArray()) {
            charSum += map.get(c);
        }
        switch (charSum) {
            case 42: return 0;
            case 17: return 1;
            case 34: return 2;
            case 39: return 3;
            case 30: return 4;
            case 37: return 5;
            case 41: return 6;
            case 25: return 7;
            case 49: return 8;
            case 45: return 9;
        }
        return -1;
    }
}
