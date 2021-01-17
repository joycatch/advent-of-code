package se.phew.aoc.days.twenty20;

import se.phew.aoc.days.Challenge;

import java.util.HashMap;

public class Day15 extends Challenge {

    public Day15() {
        super();

        HashMap<Integer, Memory> map = new HashMap<>();

        String[] split = lines.get(0).split(",");

        int lastNumberSpoken;

        for (int i = 0; i < split.length - 1; i++) {
            lastNumberSpoken = Integer.parseInt(split[i]);
            map.put(lastNumberSpoken, new Memory(i + 1));
        }

        lastNumberSpoken = Integer.parseInt(split[split.length - 1]);

        int part1 = 0;
        int iterations = 30000000;
        for (int i = split.length; i < iterations; i++) {
            if (!map.containsKey(lastNumberSpoken))    {
                map.put(lastNumberSpoken, new Memory(i));
                lastNumberSpoken = 0;
            } else {
                Memory memory = map.get(lastNumberSpoken);
                lastNumberSpoken = memory.setNewPosition(i);
            }
            if (i == 2019) {
                part1 = lastNumberSpoken;
            }
        }

        printAnswer(1, part1);
        printAnswer(2, lastNumberSpoken);
    }

    static class Memory {

        private int position;

        public Memory(int position) {
            this.position = position;
        }

        public int setNewPosition(int position) {
            int result = position - this.position;
            this.position = position;
            return result;
        }
    }
}
