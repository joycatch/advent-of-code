package se.phew.aoc.days;

public class Day17 extends Challenge {

    int xSize;
    int ySize;
    int zSize;

    public Day17() {
        super();

        xSize = lines.get(0).length() + 20;
        ySize = lines.size() + 20;
        zSize = 13;

        int cycles = 6;

        int[][][] world = new int[xSize][ySize][zSize];

        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                if (lines.get(y).charAt(x) == '#') {
                    world[x + 10][y + 10][6] = 1;
                }
            }
        }

        printWorld(world);

        for (int c = 0; c < cycles; c++) {
            System.out.println("After " + (c + 1) + " cycle:\n");
            int[][][] currentState = copy(world);
            runCycle(currentState, world);
            printWorld(world);
        }

        int count = countActives(world);

        printAnswer(1, count);

        // TBD

        printAnswer(2, "?");
    }

    private int[][][] copy(int[][][] world) {
        int[][][] result = new int[xSize][ySize][zSize];
        for (int z = 0; z < zSize; z++) {
            for (int y = 0; y < ySize; y++) {
                for (int x = 0; x < xSize; x++) {
                    if (active(x, y, z, world)) {
                        result[x][y][z] = 1;
                    }
                }
            }
        }
        return result;
    }

    public boolean isEmptyLayer(int z, int[][][] world) {
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                if (active(x, y, z, world)) {
                    return false;
                }
            }
        }
        return true;
    }

    private int countActives(int[][][] world) {
        int count = 0;
        for (int z = 0; z < zSize; z++) {
            for (int y = 0; y < ySize; y++) {
                for (int x = 0; x < xSize; x++) {
                    if (active(x, y, z, world)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    private void runCycle(int[][][] currentState, int[][][] world) {
        for (int z = 0; z < zSize; z++) {
            for (int y = 0; y < ySize; y++) {
                for (int x = 0; x < xSize; x++) {
                    int count = neighbourCount(x, y, z, currentState);
                    if (active(x, y, z, currentState)) {
                        if (count == 2 || count == 3) {
                            world[x][y][z] = 1;
                        } else {
                            world[x][y][z] = 0;
                        }
                    } else {
                        if (count == 3) {
                            world[x][y][z] = 1;
                        } else {
                            world[x][y][z] = 0;
                        }
                    }
                }
            }
        }
    }

    private int neighbourCount(int xTest, int yTest, int zTest, int[][][] currentState) {
        int xStart = Math.max(xTest - 1, 0);
        int yStart = Math.max(yTest - 1, 0);
        int zStart = Math.max(zTest - 1, 0);

        int xStop = Math.min(xTest + 1, xSize - 1);
        int yStop = Math.min(yTest + 1, ySize - 1);
        int zStop = Math.min(zTest + 1, zSize - 1);
        int count = 0;

        for (int z = zStart; z <= zStop; z++) {
            for (int x = xStart; x <= xStop; x++) {
                for (int y = yStart; y <= yStop; y++) {
                    // if (!(x == xTest && y == yTest && z == zTest)) {
                        if (active(x, y, z, currentState)) {
                            count++;
                        }
                    // }
                }
            }
        }
        if (active(xTest, yTest, zTest, currentState) && count > 0) {
            count--;
        }
        return count;
    }

    private boolean active(int x, int y, int z, int[][][] world) {
        return world[x][y][z] == 1;
    }

    private void printWorld(int[][][] world) {
        for (int z = 0; z < zSize; z++) {
            if (!isEmptyLayer(z, world)) {
                System.out.println("z=" + (z - 6));
                for (int y = 0; y < ySize; y++) {
                    for (int x = 0; x < xSize; x++) {
                        if (world[x][y][z] == 1) {
                            System.out.print("#");
                        } else {
                            System.out.print(".");
                        }
                    }
                    System.out.println();
                }
                System.out.println("\n");
            }
        }
    }
}
