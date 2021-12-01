package se.phew.aoc.days.twenty21;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import se.phew.aoc.days.Challenge;

public class Day01 extends Challenge {

    public Day01() {
        super();

        int previous = Integer.MIN_VALUE;
        int previousSum = Integer.MIN_VALUE;
        int part1 = -1;
        int part2 = -1;
        CircularFifoQueue<Integer> slidingWindow = new CircularFifoQueue<>(3);

        for (String line : lines) {
            int current = Integer.parseInt(line);
            slidingWindow.add(current);
            int sum = slidingWindow.stream().mapToInt(Integer::intValue).sum();
            part1 += (current > previous) ? 1 : 0;
            part2 += (slidingWindow.isAtFullCapacity() && sum > previousSum) ? 1 : 0;
            previous = current;
            previousSum = sum;
        }

        printAnswer(1, part1);
        printAnswer(2, part2);
    }
}
