package se.phew.aoc.days.twenty23;

import se.phew.aoc.days.Challenge;

import java.util.*;
import java.util.function.Predicate;

public class Day11 extends Challenge {

    char[][] map;
    int rows, columns;

    HashSet<Point> points = new HashSet<>();
    HashMap<String, Boolean> cache = new HashMap<>();

    public Day11() {
        super(false);

        rows = lines.size();
        columns = lines.get(0).length();
        map = new char[rows][columns];

        for (int y = 0; y < rows; y++) {
            map[y] = lines.get(y).toCharArray();
        }

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                if (map[y][x] == '#') {
                    points.add(new Point(y, x));
                }
            }
        }

        printAnswer(1, getSumOfLengths(2));
        printAnswer(2, getSumOfLengths(1000000));
    }

    private long getSumOfLengths(int multiplier) {
        Queue<Point> queue = new ArrayDeque<>();
        queue.addAll(points);

        long sum = 0;
        while (!queue.isEmpty()) {
            Point point = queue.poll();
            List<Point> otherPoints = queue.stream().toList();
            for (Point otherPoint : otherPoints) {
                int maxY = Math.max(point.y, otherPoint.y);
                int maxX = Math.max(point.x, otherPoint.x);
                int minY = Math.min(point.y, otherPoint.y);
                int minX = Math.min(point.x, otherPoint.x);
                long length = 0;
                for (int y = minY; y < maxY; y++) {
                    int finalY = y;
                    length += isOtherPointsOn("y", y, p -> p.y == finalY) ? 1 : multiplier;
                }
                for (int x = minX; x < maxX; x++) {
                    int finalX = x;
                    length += isOtherPointsOn("x", x, p -> p.x == finalX) ? 1 : multiplier;
                }
                sum += length;
            }
        }
        return sum;
    }

    public boolean isOtherPointsOn(String axis, int value, Predicate<Point> predicate) {
        String key = axis + "=" + value;
        Boolean result = cache.get(key);
        if (result == null) {
            result = points.stream().filter(predicate).findAny().isPresent();
        }
        cache.putIfAbsent(key, result);
        return result;
    }
}

class Point {
    int y, x;

    public Point(int y, int x) {
        this.y = y;
        this.x = x;
    }
}