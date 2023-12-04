package se.phew.aoc.days.twenty23;

import se.phew.aoc.days.Challenge;

import java.util.*;

public class Day03 extends Challenge {

    public static final char EMPTY_CHAR = '.';
    private static int[][] offsets = new int[][] { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1} };
    char[][] map;
    int rows, columns;
    List<Integer> list = new ArrayList<>();
    HashMap<Symbol, List<Integer>> symbolToNumberMap = new HashMap<>();

    public Day03() {
        super(false);

        rows = lines.size();
        columns = lines.get(0).length();
        map = new char[rows][columns];

        for (int y = 0; y < rows; y++) {
            map[y] = lines.get(y).toCharArray();
        }

        for (int y = 0; y < rows; y++) {
            int startX = -1, endX = -1;
            for (int x = 0; x < columns; x++) {
                if (Character.isDigit(map[y][x])) {
                    startX = x;
                    while (x < columns && Character.isDigit(map[y][x])) {
                        endX = x++;
                    }
                    addNumberAndSymbol(y, startX, endX);
                }
            }
        }

        int part2 = 0;
        for (Symbol symbol : symbolToNumberMap.keySet()) {
            if (symbol.c == '*' && symbolToNumberMap.get(symbol).size() > 1) {
                part2 += symbolToNumberMap.get(symbol).stream().reduce(1, (a, b) -> a * b);
            }
        }

        printAnswer(1, list.stream().mapToInt(Integer::intValue).sum());
        printAnswer(2, part2);
    }

    private boolean isSymbol(int y, int x) {
        return map[y][x] != EMPTY_CHAR && !Character.isDigit(map[y][x]);
    }

    private void addNumberAndSymbol(int y, int startX, int endX) {
        Set<Symbol> adjacentSymbols = getAdjacentSymbols(y, startX, endX);
        if (!adjacentSymbols.isEmpty()) {
            int number = 0;
            for (int ix = 0; ix <= endX - startX; ix++) {
                number += Character.getNumericValue(map[y][startX + ix]) * (int) Math.pow(10, (endX - startX) - ix);
            }
            list.add(number);
            for (Symbol symbol : adjacentSymbols) {
                symbolToNumberMap.putIfAbsent(symbol, new ArrayList<>());
                symbolToNumberMap.get(symbol).add(number);
            }
        }
    }

    private Set<Symbol> getAdjacentSymbols(int y, int startX, int endX) {
        Set<Symbol> symbols = new HashSet<>();
        for (int x = startX; x <= endX; x++) {
            symbols.addAll(getAdjacentSymbols(y, x));
        }
        return symbols;
    }

    private Set<Symbol> getAdjacentSymbols(int y, int x) {
        Set<Symbol> set = new HashSet<>();
        for (int[] offset : offsets) {
            int ix = x + offset[0];
            int iy = y + offset[1];
            if (ix < 0 || ix >= rows || iy < 0 || iy >= columns) {
                continue;
            }
            if (map[iy][ix] != EMPTY_CHAR && !Character.isDigit(map[iy][ix])) {
                set.add(new Symbol(map[iy][ix], iy, ix));
            }
        }
        return set;
    }
}

class Symbol {
    Character c;
    int y, x;

    public Symbol(Character c, int y, int x) {
        this.c = c;
        this.y = y;
        this.x = x;
    }

    @Override
    public boolean equals(Object o) {
        Symbol symbol = (Symbol) o;
        return y == symbol.y && x == symbol.x && Objects.equals(c, symbol.c);
    }

    @Override
    public int hashCode() {
        return Objects.hash(c, y, x);
    }
}