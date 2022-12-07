package se.phew.aoc.days.twenty20;

import se.phew.aoc.days.Challenge;

import java.util.*;

public class Day24 extends Challenge {

    public Day24() {
        super(false);

        HashMap<Position, Boolean> map = new HashMap<>();

        // Part 1
        for (String line : lines) {
            Position p = new Position(0, 0);
            for (int pos = 0; pos < line.length(); pos++) {
                String key = "";
                switch (line.charAt(pos)) {
                    case 's': key += ("" + 's') + ("" + line.charAt(++pos)); break;
                    case 'n': key += ("" + 'n') + ("" + line.charAt(++pos)); break;
                    case 'e': key += 'e'; break;
                    case 'w': key += 'w'; break;
                }
                p.move(key);
            }
            if (map.containsKey(p)) {
                flip(p, map);
            } else {
                map.put(p, true);
            }
        }

        printAnswer(1, countBlackTiles(map));

        // Part 2
        for (int i = 0; i < 100; i++) {
            HashMap<Position, Boolean> newMap = (HashMap<Position, Boolean>) map.clone();
            for (Position position : newMap.keySet()) {
                addAdjacentWhiteTiles(position, map);
            }
            for (Position p : map.keySet()) {
                List<Position> adjacentTiles = getAdjacentTiles(p);
                int adjacentCount = countBlackAdjacentTiles(adjacentTiles, map);
                if (map.get(p) && (adjacentCount==0 || adjacentCount > 2)) {
                    newMap.put(p, false);
                } else if (!map.get(p) && adjacentCount == 2) {
                    newMap.put(p, true);
                }
            }
            map = newMap;
        }

        printAnswer(2, countBlackTiles(map));
    }

    private void addAdjacentWhiteTiles(Position p, HashMap<Position, Boolean> map) {
        List<Position> adjacentTiles = getAdjacentTiles(p);
        for (Position position : adjacentTiles) {
            if (!map.containsKey(position)) {
                map.put(position, false);
            }
        }
    }

    private long countBlackTiles(HashMap<Position, Boolean> map) {
        return map.values().stream().filter(a -> a).count();
    }

    public int countBlackAdjacentTiles(List<Position> adjacentTiles, HashMap<Position, Boolean> map) {
        int result = 0;
        for (Position position : adjacentTiles) {
            if (map.containsKey(position) && map.get(position)) {
                result++;
            }
        }
        return result;
    }

    public List<Position> getAdjacentTiles(Position position) {
        ArrayList<Position> result = new ArrayList<>();
        String[] directions = {"w", "nw", "ne", "e", "se", "sw"};
        for (String key : directions) {
            Position p = new Position(position.x, position.y);
            p.move(key);
            result.add(p);
        }
        return result;
    }

    public void flip(Position p, HashMap<Position, Boolean> map) {
        map.put(p, !map.get(p));
    }

    public class Position {
        int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "[" + x + "," + y + "]";
        }

        public void move(String direction) {
            switch (direction) {
                case "w": this.x--; break;
                case "nw": this.y--; break;
                case "ne": this.x++; this.y--; break;
                case "e": this.x++; break;
                case "se": this.y++; break;
                case "sw": this.x--; this.y++; break;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o == null) return false;
            return o instanceof Position && ((Position) o).x == x && ((Position) o).y == y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
