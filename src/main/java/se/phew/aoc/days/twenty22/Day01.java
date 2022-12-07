package se.phew.aoc.days.twenty22;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01 extends Challenge {

    public Day01() {
        super(false);

        ArrayList<Integer> sums = new ArrayList<>();

        List<Integer> calories = new ArrayList<>();
        for (String line : lines) {
            if (!line.isEmpty()) {
                calories.add(Integer.parseInt(line));
            } else {
                sums.add(calories.stream().mapToInt(Integer::intValue).sum());
                calories = new ArrayList<>();
            }
        }
        sums.add(calories.stream().mapToInt(Integer::intValue).sum());

        Collections.sort(sums);

        printAnswer(1, sums.get(sums.size() - 1));
        printAnswer(2, sums.get(sums.size() - 1) + sums.get(sums.size() - 2) + sums.get(sums.size() - 3));
    }
}
