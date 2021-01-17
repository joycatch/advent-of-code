package se.phew.aoc.days.twenty15;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02 extends Challenge {

    public Day02() {
        super();

        Pattern pattern = Pattern.compile("(\\d*)x(\\d*)x(\\d*)");

        int sum = 0;
        int part2 = 0;
        for (String line : lines) {
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                ArrayList<Integer> numbers = new ArrayList<>();
                for (int i = 1; i < 4; i++) {
                    numbers.add(Integer.parseInt(m.group(i)));
                }
                sum += 2 * numbers.get(0) * numbers.get(1) +
                        2 * numbers.get(1) * numbers.get(2) +
                        2 * numbers.get(0) * numbers.get(2);
                part2 += numbers.get(0) * numbers.get(1) * numbers.get(2);

                int max = numbers.stream().reduce(Integer::max).get();
                numbers.remove((Integer) max);

                sum += numbers.stream().reduce(1, (a, b) -> a * b);
                part2 += 2 * numbers.stream().reduce(Integer::sum).get();
            }
        }
        printAnswer(1, sum);
        printAnswer(1, part2);
    }
}
