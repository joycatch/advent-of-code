package se.phew.aoc.days.twenty17;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;

public class Day02 extends Challenge {

    public Day02() {
        super();

        int part1 = 0;
        int part2 = 0;
        for (String line : lines) {
            ArrayList<Integer> list = new ArrayList<>();

            for (String number : line.split("\\s+")) {
                list.add(Integer.parseInt(number));
            }
            int min = list.stream().mapToInt(Integer::intValue).min().getAsInt();
            int max = list.stream().mapToInt(Integer::intValue).max().getAsInt();
            part1 += (max - min);

            part2 += divideAndFindResult(list);
        }

        printAnswer(1, part1);
        printAnswer(2, part2);
    }

    private int divideAndFindResult(ArrayList<Integer> list) {
        for (int i : list) {
            for (int j : list) {
                if (i != j) {
                    if (i % j == 0) {
                        return i / j;
                    }
                    if (j % i == 0) {
                        return j / i;
                    }
                }
            }
        }
        print("Error! Could not find two numbers in this row where one evenly divides the other!");
        return -1;
    }

}
