package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

public class Day25 extends Challenge {

    char[][] map;
    int columns, rows;

    public Day25() {
        super();

        rows = lines.size();
        columns = lines.get(0).length();
        map = new char[rows][columns];

        for (int y = 0; y < rows; y++) {
            int x = 0;
            for (char c : lines.get(y).toCharArray()) {
                map[y][x++] = c;
            }
        }

        printMap();

        int steps = 0;
        boolean movements = true;
        while (movements) {
            movements = false;
            char[][] newMap = new char[rows][columns];
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < columns; x++) {
                    if (map[y][x] == '>' && isFree(y, nextRight(x))) {
                        newMap[y][nextRight(x)] = '>';
                        newMap[y][x] = '.';
                        movements = true;
                    } else {
                        if (newMap[y][x] != '>') {
                            newMap[y][x] = map[y][x];
                        }
                    }
                }
            }
            map = newMap;
            newMap = new char[rows][columns];
            for (int y = 0; y < rows; y++) {
                for (int x = 0; x < columns; x++) {
                    if (map[y][x] == 'v' && isFree(nextDown(y), x)) {
                        newMap[nextDown(y)][x] = 'v';
                        newMap[y][x] = '.';
                        movements = true;
                    } else {
                        if (newMap[y][x] != 'v') {
                            newMap[y][x] = map[y][x];
                        }
                    }
                }
            }
            map = newMap;
            steps++;
        }

        printAnswer(1, steps);
        printAnswer(2, "");
    }

    private void printMap() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                System.out.print(map[y][x]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean isFree(int y, int x) {
        return map[y][x] == '.';
    }

    public int nextRight(int x) {
        return x + 1 == columns ? 0 : x + 1;
    }

    public int nextDown(int y) {
        return y + 1 == rows ? 0 : y + 1;
    }
}
