package se.phew.aoc.days.twenty23;

import se.phew.aoc.days.Challenge;

import java.util.HashMap;
import java.util.Map;

public class Day01 extends Challenge {

    static Map<String, Integer> digits = new HashMap<>();
    static {
        digits.put("one", 1);
        digits.put("two", 2);
        digits.put("three", 3);
        digits.put("four", 4);
        digits.put("five", 5);
        digits.put("six", 6);
        digits.put("seven", 7);
        digits.put("eight", 8);
        digits.put("nine", 9);
    }

    public Day01() {
        super(false);

        printAnswer(1, lines.stream().mapToInt(s -> getDigit(s, true, false) * 10 + getDigit(s, false, false)).sum());
        printAnswer(2, lines.stream().mapToInt(s -> getDigit(s, true, true) * 10 + getDigit(s, false, true)).sum());
    }

    private int getDigit(String line, boolean first, boolean part2) {
        for (int i = first ? 0 : line.length() - 1; first ? i < line.length() : i >= 0; i = first ? i + 1 : i - 1) {
            if (Character.isDigit(line.charAt(i))) {
                return Character.getNumericValue(line.charAt(i));
            }
            if (part2) {
                int value = getSpelledDigitFrom(line.substring(i));
                if (value > 0) {
                    return value;
                }
            }
        }
        return -1;
    }

    private int getSpelledDigitFrom(String line) {
        return digits.keySet().stream().filter(key -> line.startsWith(key)).mapToInt(key -> digits.get(key)).sum();
    }
}
