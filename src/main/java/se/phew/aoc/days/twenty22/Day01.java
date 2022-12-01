package se.phew.aoc.days.twenty22;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01 extends Challenge {

    ArrayList<Integer> sums = new ArrayList<>();

    public Day01() {
        super();

        List<Integer> calories = new ArrayList<>();
        for (String line : lines) {
            if (!line.isEmpty()) {
                calories.add(Integer.parseInt(line));
            } else {
                addSum(calories);
                calories = new ArrayList<>();
            }
        }
        addSum(calories);

        Collections.sort(sums);

        printAnswer(1, sums.get(sums.size() - 1));
        printAnswer(2, sums.get(sums.size() - 1) + sums.get(sums.size() - 2) + sums.get(sums.size() - 3));
    }

    void addSum(List<Integer> calories) {
        int sum = calories.stream().mapToInt(Integer::intValue).sum();
        sums.add(sum);
    }
}
