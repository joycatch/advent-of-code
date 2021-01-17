package se.phew.aoc.days.twenty20;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.List;

public class Day11 extends Challenge {

    static int columns;

    public Day11() {
        super();

        columns = lines.get(0).length();

        List<String> previousMap = lines;

        boolean stop = false;
        int iteration = 0;
        while (!stop) {
            int y = 0;
            ArrayList<String> newMap = new ArrayList<>();
            boolean allLinesAreTheSame = true;
            for (String line : previousMap) {
                StringBuilder sb = new StringBuilder(line);
                for (int i = 0; i < columns; i++) {
                    char pos = line.charAt(i);
                    if (pos == 'L') {
                        if (countOccupied(previousMap, i, y,1) == 0) {
                            sb.setCharAt(i, '#');
                        }
                    }
                    if (occupied(pos)) {
                        if (countOccupied(previousMap, i, y, 5) >= 5) {
                            sb.setCharAt(i, 'L');
                        }
                    }
                }
                String newLine = sb.toString();
                newMap.add(newLine);
                if (!newLine.equals(line)) {
                    allLinesAreTheSame = false;
                }
                // System.out.println(newLine);
                y++;
            }
            stop = allLinesAreTheSame;
            previousMap = newMap;
            // System.out.println("\nIteration (" + iteration++ + "):\n");
        }

        int count = 0;
        for (String line : previousMap) {
            for (int i = 0; i < columns; i++) {
                count += occupied(line.charAt(i)) ? 1 : 0;
            }
        }

        printAnswer(2, count);
    }

    static boolean occupied(char c) {
        return c == '#';
    }

    private static int[][] offsets = new int[][] { { -1, -1 }, { -1, 0 }, { -1, 1 }, { 0, -1 }, { 0, 1 }, { 1, -1 }, { 1, 0 }, { 1, 1} };

    int countOccupied(List<String> map, int x, int y, int max) {
        int count = 0;
        for (int[] offset : offsets) {
            int ix = x;
            int iy = y;
            while (true) {
                ix += offset[0];
                iy += offset[1];
                if (ix < 0 || ix >= columns || iy < 0 || iy >= lines.size()) {
                    break;
                }

                if (map.get(iy).charAt(ix) == 'L') {
                    break;
                }

                if (map.get(iy).charAt(ix) == '#') {
                    count++;
                    if (count >= max) {
                        return count;
                    }
                    break;
                }
            }
        }

        return count;
    }

    int countOccupied2(ArrayList<String> map, int x, int y) {
        if ((x == 0 && y == 0) || // Upper left corner
                (x == columns - 1 && y == 0) || // Upper right corner
                (x == 0 && y == lines.size() - 1) || // Lower left corner
                (x == columns - 1 && y == lines.size() - 1)) { // Lower right corner
            return 0;
        }

        int occupied = 0;
        if (x == 0) {
            occupied += occupied(map.get(y-1).charAt(x+0)) ? 1 : 0;
            occupied += occupied(map.get(y-1).charAt(x+1)) ? 1 : 0;
            occupied += occupied(map.get(y+0).charAt(x+1)) ? 1 : 0;
            occupied += occupied(map.get(y+1).charAt(x+1)) ? 1 : 0;
            occupied += occupied(map.get(y+1).charAt(x+0)) ? 1 : 0;

        } else if (x == columns - 1) {
            occupied += occupied(map.get(y-1).charAt(x-0)) ? 1 : 0;
            occupied += occupied(map.get(y-1).charAt(x-1)) ? 1 : 0;
            occupied += occupied(map.get(y+0).charAt(x-1)) ? 1 : 0;
            occupied += occupied(map.get(y+1).charAt(x-1)) ? 1 : 0;
            occupied += occupied(map.get(y+1).charAt(x-0)) ? 1 : 0;

        } else if (y == 0) {
            occupied += occupied(map.get(y).charAt(x-1)) ? 1 : 0;
            occupied += occupied(map.get(y).charAt(x+1)) ? 1 : 0;
            occupied += occupied(map.get(y+1).charAt(x-1)) ? 1 : 0;
            occupied += occupied(map.get(y+1).charAt(x-0)) ? 1 : 0;
            occupied += occupied(map.get(y+1).charAt(x+1)) ? 1 : 0;

        } else if (y == lines.size() - 1) {
            occupied += occupied(map.get(y).charAt(x-1)) ? 1 : 0;
            occupied += occupied(map.get(y).charAt(x+1)) ? 1 : 0;
            occupied += occupied(map.get(y-1).charAt(x-1)) ? 1 : 0;
            occupied += occupied(map.get(y-1).charAt(x-0)) ? 1 : 0;
            occupied += occupied(map.get(y-1).charAt(x+1)) ? 1 : 0;
        } else {

            occupied += occupied(map.get(y-1).charAt(x-1)) ? 1 : 0;
            occupied += occupied(map.get(y-1).charAt(x)) ? 1 : 0;
            occupied += occupied(map.get(y-1).charAt(x+1)) ? 1 : 0;
            occupied += occupied(map.get(y).charAt(x-1)) ? 1 : 0;
            occupied += occupied(map.get(y).charAt(x+1)) ? 1 : 0;
            occupied += occupied(map.get(y+1).charAt(x-1)) ? 1 : 0;
            occupied += occupied(map.get(y+1).charAt(x)) ? 1 : 0;
            occupied += occupied(map.get(y+1).charAt(x+1)) ? 1 : 0;
        }

        return occupied;
    }
}
