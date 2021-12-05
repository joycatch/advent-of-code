package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

public class Day05 extends Challenge {

    int MAP_SIZE;
    int[][] map;

    public Day05() {
        super();

        MAP_SIZE = isTest ? 10 : 1000;
        map = new int[MAP_SIZE][MAP_SIZE];

        drawLines(true);
        printAnswer(1, countPoints());

        drawLines(false);
        printAnswer(2, countPoints());
    }

    private void drawLines(boolean part1) {
        for (String line : lines) {
            line = line.replaceAll(" \\-\\> ", ",");
            String[] split = line.split(",");
            int x1 = Integer.parseInt(split[0]);
            int y1 = Integer.parseInt(split[1]);
            int x2 = Integer.parseInt(split[2]);
            int y2 = Integer.parseInt(split[3]);
            int xDiff = Math.max(x1, x2) - Math.min(x1, x2);
            int yDiff = Math.max(y1, y2) - Math.min(y1, y2);

            if (part1) {
                if (x1 == x2) {
                    for (int i = Math.min(y1, y2); i <= Math.max(y1, y2); i++) {
                        map[i][x1]++;
                    }
                }
                if (y1 == y2) {
                    for (int i = Math.min(x1, x2); i <= Math.max(x1, x2); i++) {
                        map[y1][i]++;
                    }
                }
            } else {
                if (yDiff == xDiff && xDiff > 0) {
                    boolean down = y2 > y1;
                    boolean right = x2 > x1;
                    for (int i = 0; i <= yDiff; i++) {
                        map[y1 + (down ? i : -i)][x1 + (right ? i : -i)]++;
                    }
                }
            }
        }
    }

    private int countPoints() {
        int result = 0;
        for (int y = 0; y < MAP_SIZE; y++) {
            for (int x = 0; x < MAP_SIZE; x++) {
                if (map[y][x] > 1) {
                    result++;
                }
            }
        }
        return result;
    }
}
