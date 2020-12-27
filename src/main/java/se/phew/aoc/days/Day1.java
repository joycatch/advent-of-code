package se.phew.aoc.days;

import java.util.HashSet;

public class Day1 extends Challenge {

    public Day1() {
        super();

        HashSet<Integer> set = lines.stream().mapToInt(x -> Integer.parseInt(x)).collect(HashSet::new, HashSet::add, HashSet::addAll);

        for (int i : set) {
            int pair = 2020 - i;
            if (set.contains(pair)) {
                printAnswer(1, i * pair);
                //  "(" + i + " * " + pair + ")"
                break;
            }
        }

        loop:
        for (int i : set) {
            int remaining = 2020 - i;
            for (int j : set) {
                int temp = remaining - j;
                if (set.contains(temp)) {
                    printAnswer(2, i * j * temp);
                    // "(" + i + " * " + j + " * " + temp + ")"
                    break loop;
                }
            }
        }
    }
}
