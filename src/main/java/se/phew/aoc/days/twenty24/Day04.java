package se.phew.aoc.days.twenty24;

import se.phew.aoc.MapChallenge;

public class Day04 extends MapChallenge {

    public Day04() {
        super(false);

        char[][] needle = new char[3][3];
        needle[0] = "M.S".toCharArray();
        needle[1] = ".A.".toCharArray();
        needle[2] = "M.S".toCharArray();

        long part1 = 0, part2 = 0;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                part1 += count("XMAS", x, y);
                for (int i = 0; i < 4; i++) {
                    part2 += existsAt(needle, x, y, '.') ? 1 : 0;
                    needle = rotate(needle);
                }
            }
        }

        printAnswer(1, part1);
        printAnswer(2, part2);
    }

    int count(String needle, int x, int y) {
        if (needle.charAt(0) != map[y][x]) {
            return 0;
        }
        int count = 0;
        for (int[] offset : offsets) {
            int ix = x;
            int iy = y;
            boolean found = true;
            for (char c : needle.substring(1).toCharArray()) {
                ix += offset[0];
                iy += offset[1];
                if (ix < 0 || ix >= columns || iy < 0 || iy >= rows) {
                    found = false;
                    break;
                }
                if (map[iy][ix] != c) {
                    found = false;
                    break;
                }
            }
            if (found) {
                count++;
            }
        }
        return count;
    }

    private boolean existsAt(char[][] needle, int startX, int startY, char ignorable) {
        for (int x = 0; x < needle.length; x++) {
            for (int y = 0; y < needle[0].length; y++) {
                int iy = startY + y;
                int ix = startX + x;
                if (iy >= rows || ix >= columns) {
                    return false;
                }
                if (needle[y][x] != ignorable) {
                    if (map[iy][ix] != needle[y][x]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    char[][] rotate(char[][] needle) {
        char[][] rotated = new char[needle[0].length][needle.length];
        for (int i = 0; i < needle.length; i++) {
            for (int j = 0; j < needle[0].length; j++) {
                rotated[j][needle[0].length - 1 - i] = needle[i][j];
            }
        }
        return rotated;
    }
}
