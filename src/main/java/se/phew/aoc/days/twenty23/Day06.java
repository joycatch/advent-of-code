package se.phew.aoc.days.twenty23;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day06 extends Challenge {

    public Day06() {
        super(false);

        String timeLine = lines.get(0).replace("Time:", "").trim().replaceAll(" +", " ");
        String distanceLine = lines.get(1).replace("Distance:", "").trim().replaceAll(" +", " ");

        List<Long> times = Arrays.stream(timeLine.split(" ")).map(Long::parseLong).toList();
        List<Long> distances = Arrays.stream(distanceLine.split(" ")).map(Long::parseLong).toList();

        printAnswer(1, getCombos(times, distances));

        times = new ArrayList<>();
        times.add(Long.parseLong(timeLine.replaceAll(" ", "")));
        distances = new ArrayList<>();
        distances.add(Long.parseLong(distanceLine.replaceAll(" ", "")));

        printAnswer(2, getCombos(times, distances));

    }

    private static long getCombos(List<Long> times, List<Long> distances) {
        List<Long> combos = new ArrayList<>();
        for (int race = 0; race < times.size(); race++) {
            long combo = 0;
            Long time = times.get(race);
            for (long hold = 0; hold < time; hold++) {
                if (hold * (time - hold) > distances.get(race)) {
                    combo++;
                }
            }
            combos.add(combo);
        }
        return combos.stream().reduce(1L, (a, b) -> a * b);
    }
}