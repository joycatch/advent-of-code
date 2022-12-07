package se.phew.aoc.days.twenty20;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Day20 extends Challenge {

    HashMap<Long, Tile> tileMap = new HashMap<>();
    HashMap<Integer, Integer> borderIdCount = new HashMap<>();
    HashMap<Integer, ArrayList<Tile>> borderIdToTileMap = new HashMap<>();
    Tile[][] imageTiles;
    char[][] image;
    char[][] imageWithoutBorders;
    int gridSize;

    public Day20() {
        super(false);

        readInputAndPopulateTiles();

        // Part 1
        List<Tile> corners = getCorners();
        long answer = 1;
        for (Tile tile : corners) {
            answer *= tile.id;
        }
        printAnswer(1, answer);

        // Part 2
        buildImage(corners.get(0));

        char[][] needle = new char[3][21];
        needle[0] = "                  # ".toCharArray();
        needle[1] = "#    ##    ##    ###".toCharArray();
        needle[2] = " #  #  #  #  #  #   ".toCharArray();

        outerloop:
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                int monsters = findMonsters(needle);
                if (monsters > 0) {
                    answer = calculateWaterRoughness(imageWithoutBorders, monsters * 15);
                    break outerloop;
                }
                rotate(imageWithoutBorders);
            }
            flip(imageWithoutBorders);
        }

        printAnswer(2, answer);
    }

    private int calculateWaterRoughness(char[][] image, int removeFromCount) {
        int count = 0;
        for (int y = 0; y < image.length; y++) {
            for (int x = 0; x < image[0].length; x++) {
                if (image[y][x] == '1') {
                    count++;
                }
            }
        }
        return count - removeFromCount;
    }

    private int findMonsters(char[][] needle) {
        int count = 0;
        for (int y = 0; y < imageWithoutBorders.length - needle.length; y++) {
            for (int x = 0; x < imageWithoutBorders[0].length - needle[0].length; x++) {
                boolean foundMonster = true;
                for (int iy = 0; iy < needle.length; iy++) {
                    for (int ix = 0; ix < needle[iy].length; ix++) {
                        if (needle[iy][ix] == '#' && imageWithoutBorders[y + iy][x + ix] != '1') {
                            foundMonster = false;
                        }
                    }
                }
                if (foundMonster) {
                    count++;
                    // System.out.println("Found a monster at: " + x + ", " + y + " ");
                }
            }
        }
        return count;
    }

    private void buildImage(Tile tile) {
        imageTiles[0][0] = tile;
        adjustRotation(tile, 1, 2, 2, 1);

        // Arrange first column
        for (int y = 1; y < gridSize; y++) {
            Tile previous = imageTiles[y - 1][0];
            Tile another = getMatchingTile(previous.bottom(), previous.id);
            alignTop(another, previous.bottom());
            imageTiles[y][0] = another;
        }

        // Arrange from left to right
        for (int x = 1; x < gridSize; x++) {
            for (int y = 0; y < gridSize; y++) {
                Tile previous = imageTiles[y][x - 1];
                Tile another = getMatchingTile(previous.right(), previous.id);
                alignRight(another, previous.right());
                imageTiles[y][x] = another;
            }
        }

        for (int y = 0; y < gridSize; y++) {
            for (int x = 0; x < gridSize; x++) {
                for (int iy = 0; iy < Tile.SIZE; iy++) {
                    for (int ix = 0; ix < Tile.SIZE; ix++) {
                        image[y * Tile.SIZE + iy][x * Tile.SIZE + ix] = imageTiles[y][x].map[iy][ix];
                    }
                }
                for (int iy = 0; iy < Tile.SIZE - 2; iy++) {
                    for (int ix = 0; ix < Tile.SIZE - 2; ix++) {
                        imageWithoutBorders[y * (Tile.SIZE - 2) + iy][x * (Tile.SIZE - 2) + ix] = imageTiles[y][x].map[iy + 1][ix + 1];
                    }
                }
            }
        }

        // printImage(image, 10);
        // printImage(imageWithoutBorders, 8);
    }

    private void printImage(char[][] image, int divider) {
        for (int y = 0; y < image.length; y++) {
            if (y % divider == 0) {
                System.out.println();
            }
            for (int x = 0; x < image.length; x++) {
                if (x % divider == 0) {
                    System.out.print(" ");
                }
                char c = image[y][x];
                System.out.print(c == '1' ? "#" : ".");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void alignRight(Tile tile, int borderId) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (tile.left() == borderId) {
                    return;
                }
                tile.rotate();
            }
            tile.flip();
        }
    }

    private void alignTop(Tile tile, int borderId) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                if (tile.top() == borderId) {
                    return;
                }
                tile.rotate();
            }
            tile.flip();
        }
    }

    private List<Tile> getCorners() {
        ArrayList<Tile> result = new ArrayList<>();
        HashMap<Long, Integer> tileCount = new HashMap<>();

        for (Tile tile : tileMap.values()) {
            HashSet<Integer> borderIds = tile.borderIds;
            for (int borderId : borderIds) {
                if (borderIdCount.containsKey(borderId)) {
                    borderIdCount.put(borderId, borderIdCount.get(borderId) + 1);
                } else {
                    borderIdCount.put(borderId, 1);
                }
            }
        }

        for (int borderId : borderIdCount.keySet()) {
            if (borderIdCount.get(borderId) == 1) {
                for (Tile tile : borderIdToTileMap.get(borderId)) {
                    if (tileCount.containsKey(tile.id)) {
                        tileCount.put(tile.id, tileCount.get(tile.id) + 1);
                    } else {
                        tileCount.put(tile.id, 1);
                    }
                }
            }
        }

        for (long tileId : tileCount.keySet()) {
            if (tileCount.get(tileId) == 4) {
                result.add(tileMap.get(tileId));
            }
        }
        return result;
    }

    private Tile getMatchingTile(int borderId, long notThisTileId) {
        return borderIdToTileMap.get(borderId).stream().filter(a -> a.id != notThisTileId).findAny().get();
    }

    private void adjustRotation(Tile tile, int top, int right, int bottom, int left) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 4; j++) {
                tile.rotate();
                if (borderIdToTileMap.get(tile.top()).size() == top &&
                        borderIdToTileMap.get(tile.right()).size() == right &&
                        borderIdToTileMap.get(tile.bottom()).size() == bottom &&
                        borderIdToTileMap.get(tile.left()).size() == left) {
                    return;
                }
            }
            tile.flip();
        }
    }

    private void readInputAndPopulateTiles() {
        Tile tile = null;
        int y = 0;
        for (String line : lines) {
            if (line.startsWith("Tile ")) {
                String id = line.replace("Tile ", "").replace(":", "");
                tile = new Tile(Long.parseLong(id));
                y = 0;
            } else if (line.isEmpty()) {
                tile.computeBorderIds();
                tileMap.put(tile.id, tile);
            } else {
                tile.add(y++, line);
            }
        }
        tile.computeBorderIds();
        tileMap.put(tile.id, tile);
        gridSize = (int) Math.sqrt(tileMap.size());
        imageTiles = new Tile[gridSize][gridSize];
        image = new char[gridSize * Tile.SIZE][gridSize * Tile.SIZE];
        imageWithoutBorders = new char[gridSize * (Tile.SIZE - 2)][gridSize * (Tile.SIZE - 2)];
    }

    static void flip(char[][] map) {
        for (int y = 0; y < map.length; y++) {
            char[] temp = map[y].clone();
            for (int x = 0; x < map.length; x++) {
                map[y][x] = temp[map.length - 1 - x];
            }
        }
    }

    static void rotate(char[][] map) {
        for (int i = 0; i < map.length / 2; i++) {
            for (int j = i; j < map.length - i - 1; j++) {
                char temp = map[i][j];
                map[i][j] = map[map.length - 1 - j][i];
                map[map.length - 1 - j][i] = map[map.length - 1 - i][map.length - 1 - j];
                map[map.length - 1 - i][map.length - 1 - j] = map[j][map.length - 1 - i];
                map[j][map.length - 1 - i] = temp;
            }
        }
    }

    public class Tile {
        public static final int SIZE = 10;
        long id;
        char[][] map = new char[SIZE][SIZE];
        HashSet<Integer> borderIds = new HashSet<>();

        public Tile(long id) {
            this.id = id;
        }

        void flip() {
            Day20.flip(map);
        }

        void rotate() {
            Day20.rotate(map);
        }

        void print() {
            System.out.println("\nTile: " + id);
            for (int y = 0; y < SIZE; y++) {
                System.out.println(getRow(y).replaceAll("0", ".").replaceAll("1", "#"));
            }
            System.out.println();
        }

        int top() {
            return Integer.parseInt(new String(map[0]), 2);
        }

        int bottom() {
            return Integer.parseInt(new String(map[SIZE - 1]), 2);
        }

        int left() {
            String binary = "";
            for (int y = 0; y < SIZE; y++) {
                binary += map[y][0];
            }
            return Integer.parseInt(binary, 2);
        }

        int right() {
            String binary = "";
            for (int y = 0; y < SIZE; y++) {
                binary += map[y][SIZE - 1];
            }
            return Integer.parseInt(binary, 2);
        }

        void computeBorderIds() {
            for (int i = 0; i < 2; i++) {
                addBorderId(top());
                addBorderId(right());
                addBorderId(bottom());
                addBorderId(left());
                rotate();
                rotate();
            }
        }

        private void addBorderId(int value) {
            borderIds.add(value);
            ArrayList<Tile> list;
            if (borderIdToTileMap.get(value) == null) {
                list = new ArrayList<>();
            } else {
                list = borderIdToTileMap.get(value);
            }
            list.add(this);
            borderIdToTileMap.put(value, list);
        }

        public void add(int y, String line) {
            line = line.replaceAll("\\.", "0");
            line = line.replaceAll("#", "1");
            map[y] = line.toCharArray();
        }

        public String getRow(int y) {
            return new String(map[y]);
        }
    }
}
