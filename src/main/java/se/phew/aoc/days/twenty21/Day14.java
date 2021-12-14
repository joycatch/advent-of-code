package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

import java.util.HashMap;

public class Day14 extends Challenge {

    public Day14() {
        super(true);

        String sequence = lines.get(0);
        HashMap<String, String> rules = new HashMap<>();

        for (String line : lines.subList(2, lines.size())) {
            String[] split = line.split(" -> ");
            rules.put(split[0], split[0].charAt(0) + split[1] + split[0].charAt(1));
        }

        HashMap<String, Long> pairCount = new HashMap<>();
        for (int pos = 0; pos < sequence.length() - 1; pos++) {
            String pair = sequence.substring(pos, pos + 2);
            pairCount.put(pair, 1L);
        }

        for (int i = 0; i < 10; i++) {
            HashMap<String, Long> newMap = (HashMap<String, Long>) pairCount.clone();
            for (String pair : pairCount.keySet()) {
                if (rules.get(pair) != null) {
                    if (newMap.get(pair) == 1) {
                        newMap.remove(pair);
                    } else {
                        newMap.put(pair, newMap.get(pair) - 1);
                    }
                    String first = rules.get(pair).substring(0, 2);
                    String second = rules.get(pair).substring(1, 3);
                    newMap.put(first, newMap.get(first) == null ? 1 : newMap.get(first) + 1);
                    newMap.put(second, newMap.get(second) == null ? 1 : newMap.get(second) + 1);
                }
            }
            pairCount = newMap;
        }

        HashMap<Character, Long> countMap = new HashMap<>();
        for (String pair : pairCount.keySet()) {
            long multiplier = pairCount.get(pair);
            countMap.put(pair.charAt(0), multiplier + (countMap.get(pair.charAt(0)) == null ? 0 : countMap.get(pair.charAt(0))));
            countMap.put(pair.charAt(1), multiplier + (countMap.get(pair.charAt(1)) == null ? 0 : countMap.get(pair.charAt(1))));
        }

        long max = countMap.values().stream().mapToLong(Long::longValue).max().getAsLong();
        long min = countMap.values().stream().mapToLong(Long::longValue).min().getAsLong();

        printAnswer(2, max + "-" + min);
    }
}
