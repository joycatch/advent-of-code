package se.phew.aoc.days.twenty22;

import se.phew.aoc.days.Challenge;

public class Day12 extends Challenge {

    int[][] offsets = new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
    int[][] map;
    int yMax, xMax, startY, startX, goalY, goalX, currentX, currentY;

    public Day12() {
        super(true);

        yMax = lines.size();
        xMax = lines.get(0).length();
        map = new int[yMax][xMax];

        for (int y = 0; y < yMax; y++) {
            int x = 0;
            for (char c : lines.get(y).toCharArray()) {
                int value = (int) c - (int) 'a';
                if (value == -14) {
                    startY = y;
                    startX = x;
                    value = -1;
                }
                if (value == -28) {
                    goalY = y;
                    goalX = x;
                    value = 1 + (int) 'z' - (int) 'a';
                }
                map[y][x++] = value;
            }
        }

        int steps = 0;
        currentY = startY;
        currentX = startX;
        // while (!atGoal()) {

        //}

    }

    private boolean atGoal() {
        return currentX == goalX && currentY == goalY;
    }
}
