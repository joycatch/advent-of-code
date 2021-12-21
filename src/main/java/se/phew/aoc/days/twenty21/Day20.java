package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

public class Day20 extends Challenge {

    int[][] offsets = new int[][] { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 0 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1} };
    int[][] map;
    char[] lookup;
    int columns, rows, padding = 200;

    public Day20() {
        super();

        lookup = lines.get(0).toCharArray();

        columns = lines.get(2).length();
        rows = lines.size() - 2;
        map = new int[rows][columns];

        for (int y = 2; y < lines.size(); y++) {
            int x = 0;
            for (Character c : lines.get(y).toCharArray()) {
                map[y - 2][x++] = c == '#' ? 1 : 0;
            }
        }

        increaseSize(padding);
        for (int i = 0; i < 50; i++) {
            enhance();
            increaseSize(2);
            if (i == 1) {
                printAnswer(1, countLit());
            }
        }
        printAnswer(2, countLit());
    }

    private void enhance() {
        int[][] newMap = new int[rows][columns];
        for (int x = 1; x < columns - 1; x++) {
            for (int y = 1; y < rows - 1; y++) {
                newMap[y][x] = newValue(y, x);
            }
        }
        map = newMap;
    }

    private int countLit() {
        int count = 0;
        for (int x = padding / 2; x < columns - padding / 2; x++) {
            for (int y = padding / 2; y < rows - padding / 2; y++) {
                count += map[y][x];
            }
        }
        return count;
    }

    private int newValue(int y, int x) {
        String binary = "";
        for (int[] offset : offsets) {
            int iy = y + offset[0];
            int ix = x + offset[1];
            binary += map[iy][ix];
        }
        int i = Integer.parseInt(binary, 2);
        return lookup[i] == '#' ? 1 : 0;
    }

    private void increaseSize(int size) {
        int[][] newMap = new int[rows + size][columns + size];
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                newMap[y + (size/2)][x + (size/2)] = map[y][x];
            }
        }
        map = newMap;
        rows += size;
        columns += size;
    }

    private void printMap() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                System.out.print(map[y][x] == 0 ? '.' : '#');
            }
            System.out.println();
        }
        System.out.println();
    }
}
