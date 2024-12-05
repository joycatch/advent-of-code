package se.phew.aoc.days.twenty24;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day03 extends Challenge {

    public Day03() {
        super(false);

        Pattern pattern = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)|do\\(\\)|don't\\(\\)");

        long part1 = 0, part2 = 0;
        boolean enabled = true;
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                String group = matcher.group(0);
                switch (group) {
                    case "do()": enabled = true; break;
                    case "don't()": enabled = false; break;
                    default:
                        long value = Long.parseLong(matcher.group(1)) * Long.parseLong(matcher.group(2));
                        part1 += value;
                        part2 += enabled ? value : 0;
                        break;
                }
            }
        }

        printAnswer(1, part1);
        printAnswer(2, part2);
    }
}
