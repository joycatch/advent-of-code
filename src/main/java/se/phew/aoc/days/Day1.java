package se.phew.aoc.days;

import java.util.HashSet;

public class Day1 extends Challenge {

    public Day1() {
        super();

        HashSet<Integer> set = new HashSet<>();
        for (String line : lines) {
            set.add(Integer.parseInt(line));
        }

        for (int i : set) {
            int pair = 2020 - i;
            if (set.contains(pair)) {
                System.out.println("Part 1 answer: " + i * pair + " (" + i + " * " + pair + ")");
                break;
            }
        }

        loop:
        for (int i : set) {
            int remaining = 2020 - i;
            for (int j : set) {
                int temp = remaining - j;
                if (set.contains(temp)) {
                    System.out.println("Part 2 answer: " + i * j * temp + " (" + i + " * " + j + " * " + temp + ")");
                    break loop;
                }
            }
        }
    }
}
