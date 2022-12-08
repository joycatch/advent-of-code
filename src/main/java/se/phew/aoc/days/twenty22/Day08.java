package se.phew.aoc.days.twenty22;

import se.phew.aoc.days.Challenge;

public class Day08 extends Challenge {

    int[][] map;

    public Day08() {
        super(false);

        int size = lines.get(0).length();
        map = new int[size][size];

        for (int y = 0; y < size; y++) {
            int x = 0;
            for (char c : lines.get(y).toCharArray()) {
                map[y][x++] = Integer.parseInt(String.valueOf(c));
            }
        }

        int maxScenicScore = 0;
        int visible = map.length * 4 - 4;;
        for (int x = 1; x < size - 1; x++) {
            for (int y = 1; y < size - 1; y++) {
                visible += isVisible(x, y) ? 1 : 0;
                int scenicScore = calculateScenicScore(x, y);
                if (scenicScore > maxScenicScore) {
                    maxScenicScore = scenicScore;
                }
            }
        }

        printAnswer(1, visible);
        printAnswer(2, maxScenicScore);
    }

    private boolean isVisible(int x, int y) {
        int value = map[y][x];
        int visible = 4;
        for (int ix = x + 1; ix < map.length; ix++) {
            if (map[y][ix] >= value) {
                visible--;
                break;
            }
        }
        for (int ix = x - 1; ix >= 0; ix--) {
            if (map[y][ix] >= value) {
                visible--;
                break;
            }
        }
        for (int iy = y + 1; iy < map.length; iy++) {
            if (map[iy][x] >= value) {
                visible--;
                break;
            }
        }
        for (int iy = y - 1; iy >= 0; iy--) {
            if (map[iy][x] >= value) {
                visible--;
                break;
            }
        }
        return visible > 0;
    }

    private int calculateScenicScore(int x, int y) {
        int value = map[y][x];
        int score = 0;
        int result = 1;
        for (int ix = x + 1; ix < map.length; ix++) {
            score++;
            if (map[y][ix] >= value) {
                break;
            }
        }
        result *= score;
        score = 0;
        for (int ix = x - 1; ix >= 0; ix--) {
            score++;
            if (map[y][ix] >= value) {
                break;
            }
        }
        result *= score;
        score = 0;
        for (int iy = y + 1; iy < map.length; iy++) {
            score++;
            if (map[iy][x] >= value) {
                break;
            }
        }
        result *= score;
        score = 0;
        for (int iy = y - 1; iy >= 0; iy--) {
            score++;
            if (map[iy][x] >= value) {
                break;
            }
        }
        return result * score;
    }
}
