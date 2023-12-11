package se.phew.aoc.days.twenty23;

import se.phew.aoc.days.Challenge;


import java.util.*;
import java.util.stream.Collectors;

public class Day09 extends Challenge {

    public Day09() {
        super(false);

        int part1 = 0, part2 = 0;
        for (String line : lines) {
            List<Integer> list = Arrays.stream(line.trim().replaceAll(" +", " ").split(" ")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
            part1 += getPrediction(null, list);
            Collections.reverse(list);
            part2 += getPrediction(null, list);
        }

        printAnswer(1, part1);
        printAnswer(2, part2);
    }

    private int getPrediction(List<Integer> previous, List<Integer> list) {
        if (isAllZeros(list)) {
            return previous.get(previous.size() - 1);
        }
        List<Integer> listOfDiffs = getListOfDiffs(list);
        return getPrediction(list, listOfDiffs) + (previous != null ? previous.get(previous.size() - 1) : 0);
    }

    private static List<Integer> getListOfDiffs(List<Integer> list) {
        List<Integer> newList = new ArrayList<>();
        for (int i = 1; i < list.size(); i++) {
            newList.add(list.get(i) - list.get(i - 1));
        }
        return newList;
    }

    private boolean isAllZeros(List<Integer> list) {
        HashSet<Integer> uniqueValues = new HashSet<>(list);
        return uniqueValues.size() == 1 && uniqueValues.contains(0);
    }
}
