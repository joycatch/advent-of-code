package se.phew.aoc.days.utils;

public class MapUtil {

    public static void printMap(int[][][] map) {
        for (int z = 0; z < map.length; z++) {
            System.out.println("z=" + z);
            for (int y = 0; y < map[0].length; y++) {
                for (int x = 0; x < map[0][0].length; x++) {
                    if (map[z][y][x] > 0) {
                        System.out.print(map[z][y][x]);
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
