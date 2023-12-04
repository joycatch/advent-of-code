package se.phew.aoc.days.twenty23;

import se.phew.aoc.days.Challenge;

import java.util.*;

public class Day03 extends Challenge {

    private static int[][] offsets = new int[][] { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1} };
    char[][] map;
    int rows, columns;
    List<Integer> list = new ArrayList<>();
    HashMap<Character, List<Cordinates>> symbols = new HashMap<>();

    public Day03() {
        super(true);

        rows = lines.size();
        columns = lines.get(0).length();
        map = new char[rows][columns];

        for (int y = 0; y < rows; y++) {
            map[y] = lines.get(y).toCharArray();
        }

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                if (isSymbol(y, x)) {
                    symbols.putIfAbsent(map[y][x], new ArrayList<>());
                    symbols.get(map[y][x]).add(new Cordinates(y, x));
                }
            }
        }

        int sum = 0;
        for (Character symbol : symbols.keySet()) {
            for (Cordinates cordinates : symbols.get(symbol)) {
                Set<Integer> numbers = findAdjacentNumbers(cordinates.y, cordinates.x);
                if (numbers.size() == 2) {
                    System.out.println("Two numbers: " + numbers);
                }
                sum += numbers.stream().mapToInt(Integer::intValue).sum();
            }
        }
        printAnswer(2, sum);



        for (int y = 0; y < rows; y++) {
            int startX = -1, endX = -1;
            for (int x = 0; x < columns; x++) {
                if (Character.isDigit(map[y][x])) {
                    startX = x;
                    while (x < columns && Character.isDigit(map[y][x])) {
                        endX = x++;
                    }
                    addToListIfisAdjacentToASymbol(y, startX, endX);
                }
            }
        }

        printAnswer(1, list.stream().mapToInt(Integer::intValue).sum());
    }

    private Set<Integer> findAdjacentNumbers(int y, int x) {
        Set<Integer> set = new HashSet<>();
        for (int[] offset : offsets) {
            int ix = x + offset[0];
            int iy = y + offset[1];
            if (ix < 0 || ix >= rows || iy < 0 || iy >= columns) {
                continue;
            }
            int fullNumber = getFullNumber(iy, ix);
            if (fullNumber > 0) {
                set.add(fullNumber);
            }
        }
        return set;
    }

    private int getFullNumber(int y, int x) {
        int startX = x, endX = x;
        while (Character.isDigit(map[y][startX]) && startX > 0) {
            startX--;
        }
        while (Character.isDigit(map[y][endX]) && endX < columns - 1) {
            endX++;
        }
        int number = 0;
        for (int ix = 0; ix <= endX - startX; ix++) {
            number += Character.getNumericValue(map[y][startX + ix]) * (int) Math.pow(10, (endX - startX) - ix);
        }
        return number;
    }

    private boolean isSymbol(int y, int x) {
        return map[y][x] != '.' && !Character.isDigit(map[y][x]);
    }

    private void addToListIfisAdjacentToASymbol(int y, int startX, int endX) {
        if (isAdjacentToASymbol(y, startX, endX)) {
            int number = 0;
            for (int ix = 0; ix <= endX - startX; ix++) {
                number += Character.getNumericValue(map[y][startX + ix]) * (int) Math.pow(10, (endX - startX) - ix);
            }
            list.add(number);
        }
    }

    private boolean isAdjacentToASymbol(int y, int startX, int endX) {
        for (int x = startX; x <= endX; x++) {
            if (isAdjacentToASymbol(y, x)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAdjacentToASymbol(int y, int x) {
        for (int[] offset : offsets) {
            int ix = x + offset[0];
            int iy = y + offset[1];
            if (ix < 0 || ix >= rows || iy < 0 || iy >= columns) {
                continue;
            }
            if (map[iy][ix] != '.' && !Character.isDigit(map[iy][ix])) {
                return true;
            }
        }
        return false;
    }
}

class Cordinates {
    int y, x;

    public Cordinates(int y, int x) {
        this.y = y;
        this.x = x;
    }

    @Override
    public boolean equals(Object o) {
        Cordinates that = (Cordinates) o;
        return y == that.y && x == that.x ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x);
    }
}