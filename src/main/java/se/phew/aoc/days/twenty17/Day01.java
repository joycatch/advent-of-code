package se.phew.aoc.days.twenty17;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;

public class Day01 extends Challenge {

    public Day01() {
        super(false);

        String line = lines.get(0);
        char[] chars = line.toCharArray();
        char previous = chars[chars.length - 1];

        ArrayList<Integer> list = new ArrayList<>();
        for (char c : chars) {
            if (c == previous) {
                list.add(Integer.parseInt("" + c));
            }
            previous = c;
        }

        printAnswer(1, list.stream().mapToInt(Integer::intValue).sum());

        list = new ArrayList<>();
        int halfwayAround = chars.length / 2;
        for (int i = 0; i < chars.length; i++) {
            int nextIndex = (i + halfwayAround) % chars.length;
            if (chars[i] == chars[nextIndex]) {
                list.add(Integer.parseInt("" + chars[i]));
            }
        }

        printAnswer(2, list.stream().mapToInt(Integer::intValue).sum());
    }
}
