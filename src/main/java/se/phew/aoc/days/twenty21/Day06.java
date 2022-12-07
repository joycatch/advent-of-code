package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

import java.util.HashMap;

public class Day06 extends Challenge {

    HashMap<Integer, Long> map = new HashMap<>();

    public Day06() {
        super(false);

        for (int i = 0; i < 9; i++) {
            map.put(i, (long) 0);
        }
        for (String split : lines.get(0).split(",")) {
            int fish = Integer.parseInt(split);
            map.put(fish, map.get(fish) + 1);
        }

        printAnswer(1, countFishes(80));
        printAnswer(2, countFishes(256));
    }

    private long countFishes(int onDay) {
        HashMap<Integer, Long> map = (HashMap<Integer, Long>) this.map.clone();
        for (int day = 0; day < onDay; day++) {
            HashMap<Integer, Long> newMap = new HashMap<>();
            long babies = 0;
            for (int i = 0; i < 9; i++) {
                if (i == 0) {
                    babies = map.get(0);
                } else {
                    newMap.put(i - 1, map.get(i));
                }
            }
            newMap.put(6, newMap.get(6) + babies);
            newMap.put(8, babies);
            map = newMap;
        }
        long result = 0;
        for (int i = 0; i < 9; i++) {
            result += map.get(i);
        }
        return result;
    }
}
