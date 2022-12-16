package se.phew.aoc.days.twenty22;

import se.phew.aoc.days.Challenge;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Day15 extends Challenge {

    public Day15() {
        super(false);

        // Sensor at x=2, y=18: closest beacon is at x=-2, y=15

        Set<Long> part1 = new HashSet<>();
        for (String line : lines) {
            line = line.replace("Sensor at x=", "");
            line = line.replaceAll(", y=", ",");
            line = line.replaceAll(": closest beacon is at x=", ",");
            String[] split = line.split(",");
            Sensor sensor = new Sensor(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            sensor.beacon = new Beacon(Integer.parseInt(split[2]), Integer.parseInt(split[3]));

            part1.addAll(sensor.occupiedAt(isTest ? 10 : 2000000));
        }

        printAnswer(1, part1.size());
    }

    class Sensor {
        int x, y;
        Beacon beacon;

        public Sensor(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int manhattanDistance() {
            return Math.abs(beacon.x - this.x) + Math.abs(beacon.y - this.y);
        }

        public Set<Long> occupiedAt(int y) {
            int manhattan = manhattanDistance();
            int distanceFromY = Math.abs(y - this.y);
            int steps = manhattan - distanceFromY;
            if (steps > 0) {
                return LongStream.range(this.x - steps, this.x + steps).boxed().collect(Collectors.toSet());
            }
            return Collections.emptySet();
        }
    }

    class Beacon {
        int x, y;

        public Beacon(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
