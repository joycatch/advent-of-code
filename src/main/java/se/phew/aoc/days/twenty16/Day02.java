package se.phew.aoc.days.twenty16;

import se.phew.aoc.days.Challenge;

public class Day02 extends Challenge {

    private int[][] keypad = {{ 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
    private Character[][] newKeypad = {
            { null, null, '1', null, null },
            { null, '2', '3', '4', null },
            { '5', '6', '7', '8', '9' },
            { null, 'A', 'B', 'C', null },
            { null, null, 'D', null, null }};
    private int x = 1, y = 1;

    public Day02() {
        super(false);

        // Part 1
        String result = "";
        for (String line : lines) {
            for (char c : line.toCharArray()) {
                walk(c, true);
            }
            result += keypad[y][x];
        }

        printAnswer(1, result);

        // Part 2
        x = 0;
        y = 2;
        result = "";
        for (String line : lines) {
            for (char c : line.toCharArray()) {
                walk(c, false);
            }
            result += newKeypad[y][x];
        }

        printAnswer(2, result);
    }

    public void walk(char c, boolean part1) {
        int tempX = x, tempY = y;
        switch (c) {
            case 'U': y--; break;
            case 'D': y++; break;
            case 'L': x--; break;
            case 'R': x++; break;
        }
        int max = (part1 ? keypad.length : newKeypad.length) - 1;
        y = y < 0 ? 0 : y;
        x = x < 0 ? 0 : x;
        y = y > max ? max : y;
        x = x > max ? max : x;

        if (part1) {
            return;
        }

        if (newKeypad[y][x] == null) {
            y = tempY;
            x = tempX;
        }
    }
}
