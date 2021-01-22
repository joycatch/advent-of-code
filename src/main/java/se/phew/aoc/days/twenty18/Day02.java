package se.phew.aoc.days.twenty18;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Day02 extends Challenge {

    public Day02() {
        super();

        // Part 1
        ArrayList<Character> two = new ArrayList<>();
        ArrayList<Character> three = new ArrayList<>();
        for (String line : lines) {
            boolean foundTwo = false, foundThree = false;
            for (char c : line.toCharArray()) {
                long count = line.chars().filter(ch -> ch == c).count();
                if (count == 2 && !foundTwo) {
                    two.add(c);
                    foundTwo = true;
                }
                if (count == 3 && !foundThree) {
                    three.add(c);
                    foundThree = true;
                }
                if (foundTwo && foundThree) {
                    break;
                }
            }
        }

        printAnswer(1, two.size() * three.size());

        // Part 2
        String result = "";
        outerloop:
        for (String line : lines) {
            for (String line2 : lines) {
                int errorCount = 0;
                int charPos = -1;
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) != line2.charAt(i)) {
                        errorCount++;
                        charPos = i;
                    }
                }
                if (errorCount == 1) {
                    result = line.substring(0, charPos) + line.substring(charPos + 1);
                    break outerloop;
                }
            }
        }

        printAnswer(2, result);
    }
}
