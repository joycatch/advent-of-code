package se.phew.aoc.days.twenty24;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class Day02 extends Challenge {

    public Day02() {
        super(false);

        int part1 = 0, part2 = 0;
        for (String line : lines) {
            List<Integer> report = new ArrayList<>();
            for (String split : line.split(" ")) {
                report.add(Integer.parseInt(split));
            }
            part1 += isSafe(report) ? 1 : 0;

            for (int i = 0; i < report.size(); i++) {
                List<Integer> clonedList = new ArrayList<>(report);
                clonedList.remove(i);
                if (isSafe(clonedList)) {
                    part2++;
                    break;
                }
            }
        }

        printAnswer(1, part1);
        printAnswer(2, part2);
    }

    public static boolean isSafe(List<Integer> list) {
        boolean increasing = IntStream.range(1, list.size())
                .allMatch(i -> list.get(i) > list.get(i - 1) && (list.get(i) - list.get(i - 1)) <= 3);
        boolean decreasing = IntStream.range(1, list.size())
                .allMatch(i -> list.get(i) < list.get(i - 1) && (list.get(i - 1) - list.get(i)) <= 3);
        return increasing || decreasing;
    }
}
