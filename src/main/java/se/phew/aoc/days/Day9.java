package se.phew.aoc.days;

import java.util.*;

public class Day9 extends Challenge {

    public Day9() {
        super();

        long[] values = new long[lines.size()];
        for (int i = 0; i < lines.size(); i++) {
            values[i] = Long.parseLong(lines.get(i));
        }

        int preamble = (isTest ? 5 : 25);
        long part1 = 0;

        for (int pos = preamble; pos < values.length - 1; pos++) {
            long[] memory = new long[preamble];
            for (int i = pos - preamble, j = 0; i < pos; i++, j++) {
                memory[j] = values[i];
            }
            if (!sumsTo(memory, values[pos])) {
                part1 = values[pos];
                printAnswer(1, part1);
                break;
            }
        }

        for (int memoryLength = 2; memoryLength < values.length; memoryLength++) {
            for (int valuePos = memoryLength; valuePos < values.length; valuePos++) {
                long[] memory = new long[memoryLength];
                for (int i = valuePos - memoryLength, j = 0; i < valuePos; i++, j++) {
                    memory[j] = values[i];
                }
                if (Arrays.stream(memory).sum() == part1) {
                    long answer = Arrays.stream(memory).min().getAsLong() + Arrays.stream(memory).max().getAsLong();
                    printAnswer(2, answer);
                    memoryLength = values.length;
                    break;
                }
            }
        }
    }

    boolean sumsTo(long[] list, long sum) {
        HashSet<Long> values = new HashSet<>();
        for (long candidate : list) {
            if (values.contains(sum - candidate)) {
                return true;
            }
            values.add(candidate);
        }
        return false;
    }
}