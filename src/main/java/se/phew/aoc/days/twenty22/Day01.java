package se.phew.aoc.days.twenty22;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01 extends Challenge {

    public Day01() {
        super();

        List<Reindeer> reindeers = new ArrayList<>();
        Reindeer reindeer = new Reindeer();

        for (String line : lines) {
            if (!line.isEmpty()) {
                reindeer.list.add(Integer.parseInt(line));
            } else {
                reindeers.add(reindeer);
                reindeer = new Reindeer();
            }
        }
        reindeers.add(reindeer);

        int part1 = -1;
        ArrayList<Integer> sums = new ArrayList<>();
        for (Reindeer r : reindeers) {
            int sum = r.list.stream().mapToInt(Integer::intValue).sum();
            sums.add(sum);
            if (sum > part1) {
                part1 = sum;
            }
        }

        Collections.sort(sums);
        int part2 = sums.get(sums.size() - 1) + sums.get(sums.size() - 2) + sums.get(sums.size() - 3);

        printAnswer(1, part1);
        printAnswer(2, part2);
    }

    class Reindeer {
        List<Integer> list = new ArrayList<>();
    }
}
