package se.phew.aoc.days.twenty24;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day01 extends Challenge {

    public Day01() {
        super(false);

        List<Integer> left = new ArrayList<>();
        List<Integer> right = new ArrayList<>();
        lines.stream().forEach(s -> {
            String[] split = s.split("   ");
            left.add(Integer.parseInt(split[0]));
            right.add(Integer.parseInt(split[1]));
        });
        Collections.sort(left);
        Collections.sort(right);

        int part1 = 0;
        long part2 = 0;
        for (int i = 0; i < left.size(); i++) {
            long value = (long) left.get(i);
            part1 += Math.abs(left.get(i) - right.get(i));
            part2 += value * right.stream().filter(v -> v == value).count();
        }
        printAnswer(1, part1);
        printAnswer(2, part2);
    }
}
