package se.phew.aoc.days.twenty19;

import se.phew.aoc.days.Challenge;

public class Day01 extends Challenge {

    public Day01() {
        super();

        long part1 = 0;
        long part2 = 0;
        for (String line : lines) {
            int mass = Integer.parseInt("" + line);
            int fuel = (int) (mass/3 - 2);
            part1 += fuel;
            part2 += fuel + fuelFor(fuel);
        }

        printAnswer(1, part1);
        printAnswer(2, part2);
    }

    private long fuelFor(int mass) {
        int result = (int) (mass/3 - 2);
        if (result < 0) {
            return 0;
        } else {
            return result + fuelFor(result);
        }
    }
}
