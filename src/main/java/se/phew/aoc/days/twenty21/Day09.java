package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Day09 extends Challenge {

    int[][] offsets = new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
    int[][] map;
    int yMax, xMax;

    public Day09() {
        super();

        yMax = lines.size();
        xMax = lines.get(0).length();
        map = new int[yMax][xMax];

        for (int y = 0; y < yMax; y++) {
            int x = 0;
            for (char c : lines.get(y).toCharArray()) {
                map[y][x++] = Integer.parseInt(String.valueOf(c));
            }
        }

        ArrayList<Integer> list = new ArrayList<>();

        int part1 = 0;
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                if (isLocalMinimum(x, y)) {
                    part1 += map[y][x] + 1;
                    list.add(calculateBasinSize(x, y, Arrays.stream(map).map(int[]::clone).toArray(int[][]::new), map[y][x]));
                }
            }
        }

        Collections.sort(list);

        printAnswer(1, part1);
        printAnswer(2, list.get(list.size() - 1) * list.get(list.size() - 2) * list.get(list.size() - 3));
    }

    private int calculateBasinSize(int x, int y, int[][] map, int compareTo) {
        int result = 1;
        for (int[] offset : offsets) {
            int ix = x + offset[0];
            int iy = y + offset[1];
            if (ix < 0 || ix >= xMax || iy < 0 || iy >= yMax) {
                continue;
            }
            if (map[iy][ix] == 9) {
                continue;
            }
            if (compareTo < map[iy][ix]) {
                map[iy][ix] = 9;
                result += calculateBasinSize(ix, iy, map, compareTo + 1);
            }
        }
        return result;
    }

    private boolean isLocalMinimum(int x, int y) {
        int compareTo = 4 - ((x == 0 || x == xMax - 1) ? 1 : 0) - ((y == 0 || y == yMax - 1) ? 1 : 0);
        int count = 0;
        for (int[] offset : offsets) {
            int ix = x + offset[0];
            int iy = y + offset[1];
            if (ix < 0 || ix >= xMax || iy < 0 || iy >= yMax) {
                continue;
            }
            count += map[y][x] < map[iy][ix] ? 1 : 0;
        }
        return count >= compareTo;
    }
}
