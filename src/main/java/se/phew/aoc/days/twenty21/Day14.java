package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Day14 extends Challenge {

    String template;
    Map<String, String> rules;

    public Day14() {
        super();

        template = lines.get(0);
        rules = lines.subList(2, lines.size())
                .stream()
                .map(line -> line.split(" -> "))
                .collect(Collectors.toMap(s -> s[0], s -> s[1]));

        HashMap<String, Long> pairs = new HashMap<>();
        for (int i = 0; i < template.length() - 1; i++) {
            pairs.merge(template.substring(i, i + 2), 1L, Long::sum);
        }

        printAnswer(1, getQuantityDiff(pairs, 10));
        printAnswer(2, getQuantityDiff(pairs, 40));
    }

    private long getQuantityDiff(HashMap<String, Long> pairs, int steps) {
        for (int i = 0; i < steps; i++) {
            HashMap<String, Long> newPairs = new HashMap<>();
            for (String pair : pairs.keySet()) {
                if (rules.containsKey(pair)) {
                    newPairs.merge(pair.charAt(0) + rules.get(pair), pairs.get(pair), Long::sum);
                    newPairs.merge(rules.get(pair) + pair.charAt(1), pairs.get(pair), Long::sum);
                }
            }
            pairs = newPairs;
        }

        HashMap<Character, Long> charCount = new HashMap<>();
        for (String key : rules.keySet()) {
            charCount.put(key.charAt(0), 1L);
            charCount.put(key.charAt(1), 1L);
            charCount.put(rules.get(key).charAt(0), 1L);
        }
        for (String key : pairs.keySet()) {
            charCount.merge(key.charAt(0), pairs.get(key), Long::sum);
        }
        charCount.merge(template.charAt(template.length() - 1), 1L, Long::sum);
        long max = charCount.values().stream().mapToLong(s -> s).max().getAsLong();
        long min = charCount.values().stream().mapToLong(s -> s).min().getAsLong();
        return max - min;
    }
}
