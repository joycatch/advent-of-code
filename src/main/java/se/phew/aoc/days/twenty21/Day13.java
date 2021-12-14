package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.List;

public class Day13 extends Challenge {

    int columns, rows;
    int[][] map;

    public Day13() {
        super(true);

        List<Dot> dots = new ArrayList<>();

        for (String line : lines) {
            if (line.contains(",")) {
                String[] split = line.split(",");
                int x = Integer.parseInt(split[0]);
                int y = Integer.parseInt(split[1]);
                dots.add(new Dot(x, y));
            }
        }
        columns = dots.stream().map(d -> d.x).mapToInt(Integer::intValue).max().getAsInt() + 1;
        rows = dots.stream().map(d -> d.y).mapToInt(Integer::intValue).max().getAsInt() + 1;
        map = new int[rows][columns];

        for (Dot dot : dots) {
            map[dot.y][dot.x] = 1;
        }

        int result = 0;
        for (String line : lines) {
            if (line.contains("fold")) {
                int along = Integer.parseInt(line.split("=")[1]);
                if (line.contains("y")) {
                    foldY(along);
                } else {
                    foldX(along);
                }
                result = result == 0 ? countDots() : result;
                // printMap();
            }
        }

        printAnswer(1, result);
        printAnswer(2, "");

        printMap();
    }

    private int countDots() {
        int result = 0;
        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                result += map[y][x] > 0 ? 1 : 0;
            }
        }
        return result;
    }

    private void foldY(int row) {
        int[][] newMap = new int[row][columns];
        for (int x = 0; x < columns; x++) {
            for (int y = 0; row + y + 1 < rows; y++) {
                newMap[row - y - 1][x] = map[row - y - 1][x] + map[row + y + 1][x];
            }
        }
        map = newMap;
        rows = row;
    }

    private void foldX(int column) {
        int[][] newMap = new int[rows][column];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; column + x + 1 < columns; x++) {
                newMap[y][column - x - 1] = map[y][column - x - 1] + map[y][column + x + 1];
            }
        }
        map = newMap;
        columns = column;
    }

    private void printMap() {
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                System.out.print(map[y][x] == 0 ? ' ' : '█');
            }
            System.out.println();
        }
        System.out.println();
    }

    static class Dot {
        int x, y;

        public Dot(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
