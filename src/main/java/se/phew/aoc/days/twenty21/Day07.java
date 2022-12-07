package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;

public class Day07 extends Challenge {

    public Day07() {
        super(false);

        ArrayList<Integer> positions = new ArrayList<>();
        for (String split : lines.get(0).split(",")) {
            positions.add(Integer.parseInt(split));
        }

        printAnswer(1, countFuel(positions, true));
        printAnswer(2, countFuel(positions, false));
    }

    private int countFuel(ArrayList<Integer> positions, boolean part1) {
        int min = Integer.MAX_VALUE;
        int start = positions.stream().mapToInt(s -> s).min().getAsInt();
        int end = positions.stream().mapToInt(s -> s).max().getAsInt();
        for (int i = start; i <= end; i++) {
            int result = 0;
            for (Integer submarine : positions) {
                int cost;
                cost = Math.abs(submarine - i);
                if (!part1) {
                    cost = cost * (cost + 1) / 2;
                }
                result += cost;
            }
            if (result < min) {
                min = result;
            }
        }
        return min;
    }
}
