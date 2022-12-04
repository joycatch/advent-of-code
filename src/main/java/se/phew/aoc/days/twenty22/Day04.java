package se.phew.aoc.days.twenty22;

import se.phew.aoc.days.Challenge;

import java.util.*;
import java.util.stream.Collectors;

public class Day04 extends Challenge {

    public Day04() {
        super();

        int part1 = 0;
        int part2 = 0;

        for (String line : lines) {
            String[] split = line.split(",");
            String[] s = split[0].split("-");
            int x1 = Integer.parseInt(s[0]);
            int x2 = Integer.parseInt(s[1]);
            s = split[1].split("-");
            int y1 = Integer.parseInt(s[0]);
            int y2 = Integer.parseInt(s[1]);

            if (isRangeFullyContained(x1, x2, y1, y2)) {
                part1++;
            }
            if (isOverlapping(x1, x2, y1, y2)) {
                part2++;
            }
        }

        printAnswer(1, part1);
        printAnswer(2, part2);
    }

    private static boolean isRangeFullyContained(int x1, int x2, int y1, int y2) {
        return x1 >= y1 && x2 <= y2 || y1 >= x1 && y2 <= x2;
    }

    public boolean isOverlapping(int x1, int x2, int y1, int y2) {
        return Integer.max(x1, y1) <= Integer.min(x2, y2);
    }
}
