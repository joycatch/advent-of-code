package se.phew.aoc;

import se.phew.aoc.days.Challenge;

public class MapChallenge extends Challenge {

    protected static int[][] offsets = new int[][]{{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    protected char[][] map;
    protected int rows, columns;

    public MapChallenge(boolean isTest) {
        super(isTest);

        rows = lines.size();
        columns = lines.get(0).length();
        map = new char[rows][columns];

        for (int y = 0; y < rows; y++) {
            map[y] = lines.get(y).toCharArray();
        }
    }

    protected void printMap() {
        printMap(map);
    }

    protected void printMap(char[][] map) {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                System.out.print(map[y][x]);
            }
            System.out.println();
        }
        System.out.println();
    }
}