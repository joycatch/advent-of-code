package se.phew.aoc.days.twenty18;

import se.phew.aoc.days.Challenge;

import java.util.HashSet;

public class Day01 extends Challenge {

    public Day01() {
        super(false);

        int sum = 0;
        for (String line : lines) {
            sum += Integer.parseInt(line.replaceAll("\\+", ""));
        }

        printAnswer(1, sum);

        HashSet<Integer> sums = new HashSet<>();
        int part2 = -1;
        sum = 0;

        outerloop:
        while (part2 == -1) {
            for (String line : lines) {
                sum += Integer.parseInt(line.replaceAll("\\+", ""));
                if (part2 == -1 && sums.contains(sum)) {
                    part2 = sum;
                    break outerloop;
                }
                sums.add(sum);
            }
        }

        printAnswer(2, part2);
    }
}
