package se.phew.aoc.days.twenty22;

import se.phew.aoc.days.Challenge;

public class Day18 extends Challenge {

    int xSize = Integer.MIN_VALUE;
    int ySize = Integer.MIN_VALUE;
    int zSize = Integer.MIN_VALUE;
    int[][][] world;
    int[][][] sides;

    public Day18() {
        super(true);

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
                    int i = neighbourCount(z, y, x);
                    sides[z][y][x] -= i;
                    part1 += sides[z][y][x];
                }
            }
        }

        printAnswer(1, part1);

        populateWorld();

        printSides();
        fillAirBubbles();
        printSides();

        int part2 = 0;
        for (int z = 0; z < zSize; z++) {
            for (int y = 0; y < ySize; y++) {
                for (int x = 0; x < xSize; x++) {
                    sides[z][y][x] -= neighbourCount(z, y, x);
                    if (sides[z][y][x] < 0) {
                        sides[z][y][x] = 0;
                    }
                    part2 += sides[z][y][x];
                }
            }
        }

        printAnswer(2, part2);
    }

    private void fillAirBubbles() {
        for (int z = 0; z < zSize; z++) {
            for (int y = 0; y < ySize; y++) {
                for (int x = 0; x < xSize; x++) {
                    if (isTrapped(z,y,x)) {
                        world[z][y][x] = 6;
                        sides[z][y][x] = 6;
                    }
                }
            }
        }
    }

    private boolean isTrapped(int zTest, int yTest, int xTest) {
        /* if (world[zTest][yTest][xTest] != 0) {
            return false;
        }*/
        int trapped = 0;
        for (int z = zTest - 1; z >= 0; z--) {
            if (world[z][yTest][xTest] == 6) {
                trapped++;
                break;
            }
        }
        for (int z = zTest + 1; z < zSize; z++) {
            if (world[z][yTest][xTest] == 6) {
                trapped++;
                break;
            }
        }

        for (int y = yTest - 1; y >= 0; y--) {
            if (world[zTest][y][xTest] == 6) {
                trapped++;
                break;
            }
        }
        for (int y = yTest + 1; y < ySize; y++) {
            if (world[zTest][y][xTest] == 6) {
                trapped++;
                break;
            }
        }

        for (int x = xTest - 1; x >= 0; x--) {
            if (world[zTest][yTest][x] == 6) {
                trapped++;
                break;
            }
        }
        for (int x = xTest + 1; x < xSize; x++) {
            if (world[zTest][yTest][x] == 6) {
                trapped++;
                break;
            }
        }

        return trapped == 6;
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

    private void printWorld() {
        for (int z = 0; z < zSize; z++) {
            System.out.println("z=" + z);
            for (int y = 0; y < ySize; y++) {
                for (int x = 0; x < xSize; x++) {
                    if (world[z][y][x] > 0) {
                        System.out.print(world[z][y][x]);
                    } else {
                        System.out.print(".");
                    }
                }
                System.out.println();
            }
            System.out.println("\n");
        }
    }

    private void printSides() {
        for (int z = 0; z < zSize; z++) {
            System.out.println("z=" + z);
            for (int y = 0; y < ySize; y++) {
                for (int x = 0; x < xSize; x++) {
                    if (sides[z][y][x] > 0) {
                        System.out.print(sides[z][y][x]);
                    } else {
                        System.out.print(".");
                    }
                }
                System.out.println();
            }
            System.out.println("\n");
        }
    }

    private int neighbourCount(int zTest, int yTest, int xTest) {
        if (world[zTest][yTest][xTest] == 0) {
            return 0;
        }
        int count = 0;
        if (zTest - 1 >= 0) {
            count += world[zTest - 1][yTest][xTest] > 0 ? 1 : 0;
        }
        if (yTest - 1 >= 0) {
            count += world[zTest][yTest - 1][xTest] > 0 ? 1 : 0;
        }
        if (xTest - 1 >= 0) {
            count += world[zTest][yTest][xTest - 1] > 0 ? 1 : 0;
        }

        if (zTest + 1 < zSize) {
            count += world[zTest + 1][yTest][xTest] > 0 ? 1 : 0;
        }
        if (yTest + 1 < ySize) {
            count += world[zTest][yTest + 1][xTest] > 0 ? 1 : 0;
        }
        if (xTest + 1 < xSize) {
            count += world[zTest][yTest][xTest + 1] > 0 ? 1 : 0;
        }
        return count;
    }
}
