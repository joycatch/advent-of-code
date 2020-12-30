package se.phew.aoc.days;

public class Day17 extends Challenge {

    int xSize;
    int ySize;
    int zSize;
    int wSize;

    public Day17() {
        super();

        xSize = lines.get(0).length() + 20;
        ySize = lines.size() + 20;
        zSize = 13;
        wSize = 13;

        int cycles = 6;

        int[][][][] world = populateWorld();

        printWorld(6, world);

        for (int c = 0; c < cycles; c++) {
            System.out.println("After " + (c + 1) + " cycle:\n");
            int[][][][] currentState = copy(world);
            runCycleIn3d(6, currentState, world);
            printWorld(6, world);
        }

        printAnswer(1, countActives(world));

        world = populateWorld();
        for (int c = 0; c < cycles; c++) {
            int[][][][] currentState = copy(world);
            runCycle(currentState, world);
        }

        printAnswer(2, countActives(world));
    }

    private int[][][][] populateWorld() {
        int[][][][] world = new int[wSize][zSize][ySize][xSize];
        for (int y = 0; y < lines.size(); y++) {
            for (int x = 0; x < lines.get(0).length(); x++) {
                if (lines.get(y).charAt(x) == '#') {
                    world[6][6][y + 10][x + 10] = 1;
                }
            }
        }
        return world;
    }

    private int[][][][] copy(int[][][][] world) {
        int[][][][] result = new int[wSize][zSize][ySize][xSize];
        for (int w = 0; w < wSize; w++) {
            for (int z = 0; z < zSize; z++) {
                for (int y = 0; y < ySize; y++) {
                    for (int x = 0; x < xSize; x++) {
                        if (active(w, z, y, x, world)) {
                            result[w][z][y][x] = 1;
                        }
                    }
                }
            }
        }
        return result;
    }

    public boolean isEmptyLayer(int w, int z, int[][][][] world) {
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                if (active(w, z, y, x, world)) {
                    return false;
                }
            }
        }
        return true;
    }

    private int countActives(int[][][][] world) {
        int count = 0;
        for (int w = 0; w < wSize; w++) {
            for (int z = 0; z < zSize; z++) {
                for (int y = 0; y < ySize; y++) {
                    for (int x = 0; x < xSize; x++) {
                        if (active(w, z, y, x, world)) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    private void runCycle(int[][][][] currentState, int[][][][] world) {
        for (int w = 0; w < wSize; w++) {
            runCycleIn3d(w, currentState, world);
        }
    }

    private void runCycleIn3d(int w, int[][][][] currentState, int[][][][] world) {
        for (int z = 0; z < zSize; z++) {
            for (int y = 0; y < ySize; y++) {
                for (int x = 0; x < xSize; x++) {
                    int count = neighbourCount(w, z, y, x, currentState);
                    if (active(w, z, y, x, currentState)) {
                        if (count == 2 || count == 3) {
                            world[w][z][y][x] = 1;
                        } else {
                            world[w][z][y][x] = 0;
                        }
                    } else {
                        if (count == 3) {
                            world[w][z][y][x] = 1;
                        } else {
                            world[w][z][y][x] = 0;
                        }
                    }
                }
            }
        }
    }

    private int neighbourCount(int wTest, int zTest, int yTest, int xTest, int[][][][] currentState) {
        int xStart = Math.max(xTest - 1, 0);
        int yStart = Math.max(yTest - 1, 0);
        int zStart = Math.max(zTest - 1, 0);
        int wStart = Math.max(wTest - 1, 0);

        int xStop = Math.min(xTest + 1, xSize - 1);
        int yStop = Math.min(yTest + 1, ySize - 1);
        int zStop = Math.min(zTest + 1, zSize - 1);
        int wStop = Math.min(wTest + 1, wSize - 1);
        int count = 0;

        for (int w = wStart; w <= wStop; w++) {
            for (int z = zStart; z <= zStop; z++) {
                for (int x = xStart; x <= xStop; x++) {
                    for (int y = yStart; y <= yStop; y++) {
                        // if (!(x == xTest && y == yTest && z == zTest)) {
                        if (active(w, z, y, x, currentState)) {
                            count++;
                        }
                        // }
                    }
                }
            }
        }
        if (active(wTest, zTest, yTest, xTest, currentState) && count > 0) {
            count--;
        }
        return count;
    }

    private boolean active(int w, int z, int y, int x, int[][][][] world) {
        return world[w][z][y][x] == 1;
    }

    private void printWorld(int w, int[][][][] world) {
        for (int z = 0; z < zSize; z++) {
            if (!isEmptyLayer(w, z, world)) {
                System.out.println("z=" + (z - 6));
                for (int y = 0; y < ySize; y++) {
                    for (int x = 0; x < xSize; x++) {
                        if (world[w][z][y][x] == 1) {
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
