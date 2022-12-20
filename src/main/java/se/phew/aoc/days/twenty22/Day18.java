package se.phew.aoc.days.twenty22;

import se.phew.aoc.days.Challenge;
import se.phew.aoc.days.utils.MapUtil;

public class Day18 extends Challenge {

    int xSize = Integer.MIN_VALUE;
    int ySize = Integer.MIN_VALUE;
    int zSize = Integer.MIN_VALUE;
    int[][][] world;
    int[][][] sides;
    boolean[][][] fill;

    public Day18() {
        super(false);

        for (String line : lines) {
            String[] split = line.split(",");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            int z = Integer.parseInt(split[2]);
            if (x > xSize) { xSize = x; }
            if (y > ySize) { ySize = y; }
            if (z > zSize) { zSize = z; }
        }

        xSize++;
        ySize++;
        zSize++;

        populateWorld();

        int part1 = 0;
        for (int z = 0; z < zSize; z++) {
            for (int y = 0; y < ySize; y++) {
                for (int x = 0; x < xSize; x++) {
                    sides[z][y][x] -= neighbourCount(z, y, x);
                    part1 += sides[z][y][x];
                }
            }
        }
        printAnswer(1, part1);

        populateWorld();
        fill = new boolean[zSize][ySize][xSize];
        floodFill(world, fill, 0, 0, 0);
        replace(0, 6);
        replace(9, 0);

        int part2 = 0;
        for (int z = 0; z < zSize; z++) {
            for (int y = 0; y < ySize; y++) {
                for (int x = 0; x < xSize; x++) {
                    sides[z][y][x] -= neighbourCount(z, y, x);
                    part2 += sides[z][y][x];
                }
            }
        }
        printAnswer(2, part2);
    }

    private void populateWorld() {
        world = new int[zSize][ySize][xSize];
        sides = new int[zSize][ySize][xSize];
        for (String line : lines) {
            String[] split = line.split(",");
            int x = Integer.parseInt(split[0]);
            int y = Integer.parseInt(split[1]);
            int z = Integer.parseInt(split[2]);
            world[z][y][x] = 6;
            sides[z][y][x] = 6;
        }
    }

    private int neighbourCount(int zTest, int yTest, int xTest) {
        if (world[zTest][yTest][xTest] == 0) {
            return 0;
        }
        int count = 0;
        if (zTest - 1 >= 0) {       count += world[zTest - 1][yTest][xTest] > 0 ? 1 : 0; }
        if (yTest - 1 >= 0) {       count += world[zTest][yTest - 1][xTest] > 0 ? 1 : 0; }
        if (xTest - 1 >= 0) {       count += world[zTest][yTest][xTest - 1] > 0 ? 1 : 0; }
        if (zTest + 1 < zSize) {    count += world[zTest + 1][yTest][xTest] > 0 ? 1 : 0; }
        if (yTest + 1 < ySize) {    count += world[zTest][yTest + 1][xTest] > 0 ? 1 : 0; }
        if (xTest + 1 < xSize) {    count += world[zTest][yTest][xTest + 1] > 0 ? 1 : 0; }
        return count;
    }

    void floodFill(int[][][] map, boolean[][][] visited, int z, int y, int x) {
        if (z < 0 || z >= map.length || y < 0 || y >= map[0].length || x < 0 || x >= map[0][0].length) return;
        if (visited[z][y][x]) return;
        visited[z][y][x] = true;
        if (map[z][y][x] == 6) return;
        if (map[z][y][x] == 0) map[z][y][x] = 9;
        floodFill(map, visited,z + 1, y, x);
        floodFill(map, visited,z - 1, y, x);
        floodFill(map, visited, z,y + 1, x);
        floodFill(map, visited, z,y - 1, x);
        floodFill(map, visited, z, y, x + 1);
        floodFill(map, visited, z, y, x - 1);
    }

    private void replace(int value, int replacement) {
        for (int z = 0; z < zSize; z++) {
            for (int y = 0; y < ySize; y++) {
                for (int x = 0; x < xSize; x++) {
                    if (world[z][y][x] == value) {
                        world[z][y][x] = replacement;
                        sides[z][y][x] = replacement;
                    }
                }
            }
        }
    }
}
