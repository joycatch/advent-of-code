package se.phew.aoc.days.twenty22;

import se.phew.aoc.days.Challenge;

public class Day07 extends Challenge {

    public Day07() {
        super(false);

        for (String line : lines) {
            printAnswer(1,  findMarkerStart(line, 4));
            printAnswer(2,  findMarkerStart(line, 14));
        }
    }

    private int findMarkerStart(String line, int length) {
        for (int i = 0; i < line.length() - length; i++) {
            if (line.substring(i, i + length).chars().distinct().count() == length) {
                return i + length;
            }
        }
        return -1;
    }
}
