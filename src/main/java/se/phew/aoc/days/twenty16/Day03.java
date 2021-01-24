package se.phew.aoc.days.twenty16;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.Arrays;

public class Day03 extends Challenge {

    public Day03() {
        super();

        int[][] map = new int[3][lines.size()];

        // Part 1
        int possible = 0;
        int y = 0, x;
        for (String line : lines) {
            ArrayList<Integer> list = new ArrayList<>();
            line = line.trim();
            x = 0;
            for (String number : line.split("\\s+")) {
                int n = Integer.parseInt(number);
                list.add(n);
                map[x++][y] = n;
            }
            int max = list.stream().mapToInt(Integer::intValue).max().getAsInt();
            int sum = list.stream().mapToInt(Integer::intValue).sum();
            if (sum - max > max) {
                possible++;
            }
            y++;
        }

        printAnswer(1, possible);

        // Part 2
        possible = 0;
        for (x = 0; x < 3; x++) {
            for (y = 0; y < lines.size(); y += 3) {
                int[] test = Arrays.copyOfRange(map[x], y, y + 3);
                int max = Arrays.stream(test).max().getAsInt();
                int sum = Arrays.stream(test).sum();
                if (sum - max > max) {
                    possible++;
                }
            }
        }

        printAnswer(2, possible);
    }
}
