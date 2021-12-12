package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

public class Day11 extends Challenge {

    int[][] offsets = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    int[][] map;
    int rows, columns;

    public Day11() {
        super();

        columns = lines.size();
        rows = lines.get(0).length();
        map = new int[columns][rows];

        for (int y = 0; y < columns; y++) {
            int x = 0;
            for (char c : lines.get(y).toCharArray()) {
                map[y][x++] = Integer.parseInt(String.valueOf(c));
            }
        }

        int part1 = 0;
        int part2 = 0;
        for (int i = 1; true; i++) {
            step();
            int flashes = countFlashes();
            if (i <= 100) {
                part1 += flashes;
            }
            if (flashes == rows * columns) {
                part2 = i;
                break;
            }
        }

        printAnswer(1, part1);
        printAnswer(2, part2);
    }

    private int countFlashes() {
        int result = 0;
        for (int y = 0; y < columns; y++) {
            for (int x = 0; x < rows; x++) {
                result += map[y][x] == 0 ? 1 : 0;
            }
        }
        return result;
    }

    private void step() {
        for (int y = 0; y < columns; y++) {
            for (int x = 0; x < rows; x++) {
                map[y][x]++;
            }
        }
        for (int y = 0; y < columns; y++) {
            for (int x = 0; x < rows; x++) {
                if (map[y][x] > 9) {
                    map[y][x] = 0;
                    increaseNeighbours(y, x);
                }
            }
        }
    }

    private void increaseNeighbours(int y, int x) {
        for (int[] offset : offsets) {
            int ix = x + offset[0];
            int iy = y + offset[1];
            if (ix < 0 || ix >= rows || iy < 0 || iy >= columns) {
                continue;
            }
            if (map[iy][ix] >= 9) {
                map[iy][ix] = 0;
                increaseNeighbours(iy, ix);
            }
            if (map[iy][ix] != 0) {
                map[iy][ix]++;
            }
        }
    }
}
