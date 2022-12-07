package se.phew.aoc.days.twenty22;

import se.phew.aoc.days.Challenge;

import java.util.*;
import java.util.stream.Collectors;

public class Day03 extends Challenge {

    public Day03() {
        super(false);

        int part1 = 0;
        int part2 = 0;

        int i = 0;
        Map<Character, Integer> map = new HashMap<>();

        for (String line : lines) {
            Set<Character> first = line.substring(0, (line.length() / 2)).chars().mapToObj(e -> (char) e).collect(Collectors.toSet());
            Set<Character> second = line.substring((line.length() / 2)).chars().mapToObj(e -> (char) e).collect(Collectors.toSet());

            for (char c : first) {
                if (second.contains(c)) {
                    part1 += toPriority(c);
                }
                map.putIfAbsent(c, 0);
                map.put(c, map.get(c) + 1);
            }
            for (char c : second) {
                map.putIfAbsent(c, 0);
                map.put(c, map.get(c) + 1);
            }

            if (++i % 3 == 0) {
                for (char c : map.keySet()) {
                    if (map.get(c) == 3) {
                        part2 += toPriority(c);
                    }
                }
                map = new HashMap<>();
            }
        }

        printAnswer(1, part1);
        printAnswer(2, part2);
    }

    private static int toPriority(char c) {
        return (int) c + (Character.isLowerCase(c) ? 1 - (int) 'a' : 27 - (int) 'A');
    }
}
