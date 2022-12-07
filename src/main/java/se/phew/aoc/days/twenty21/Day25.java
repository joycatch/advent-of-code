package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

public class Day25 extends Challenge {

    char[][] map;
    int columns, rows;
    boolean movement = true;

    public Day25() {
        super(false);

        rows = lines.size();
        columns = lines.get(0).length();
        map = new char[rows][columns];

        for (int y = 0; y < rows; y++) {
            int x = 0;
            for (char c : lines.get(y).toCharArray()) {
                map[y][x++] = c;
            }
        }

        int steps = 0;
        while (movement) {
            movement = false;
            moveCharacter('>');
            moveCharacter('v');
            steps++;
        }

        printAnswer(1, steps);
    }

    private void moveCharacter(char c) {
        char[][] newMap = new char[rows][columns];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                if (map[y][x] == c && c=='v' && isFree(nextDown(y), x)) {
                    newMap[nextDown(y)][x] = c;
                    newMap[y][x] = '.';
                    movement = true;
                } else if (map[y][x] == c && c=='>' && isFree(y, nextRight(x))) {
                    newMap[y][nextRight(x)] = c;
                    newMap[y][x] = '.';
                    movement = true;
                } else {
                    if (newMap[y][x] != c) {
                        newMap[y][x] = map[y][x];
                    }
                }
            }
        }
        map = newMap;
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
