package se.phew.aoc.days.twenty23;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.List;

public class Day05 extends Challenge {

    List<Mapper> mappers = new ArrayList<>();
    List<Long> locationNumbers = new ArrayList<>();

    public Day05() {
        super(false);

        Mapper mapper = new Mapper("", "");
        for (String line : lines) {
            if (line.isEmpty()) {
                mapper = null;
            }
            if (line.contains("-to-")) {
                String[] split = line.split("-to-");
                mapper = new Mapper(split[0], split[1].replace(" map:", ""));
                mappers.add(mapper);
            } else if (!line.contains("seeds:") && mapper != null) {
                mapper.addRange(line);
            }
        }

        for (String seed : lines.get(0).replace("seeds: ", "").split(" ")) {
            convertFromSeedToLocation(Long.parseLong(seed));
        }

        printAnswer(1, locationNumbers.stream().mapToLong(Long::longValue).min().getAsLong());
    }

    private void convertFromSeedToLocation(long value) {
        String from = "seed";
        while (!from.equals("location")) {
            for (Mapper m : mappers) {
                if (m.from.equals(from)) {
                    value = m.getDestination(value);
                    from = m.to;
                }
            }
        }
        locationNumbers.add(value);
    }
}

class Mapper {
    String from, to;
    List<Range> rangeList = new ArrayList<>();

    public Mapper(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public void addRange(String line) {
        String[] s = line.split(" ");
        rangeList.add(new Range(Long.parseLong(s[0]), Long.parseLong(s[1]), Long.parseLong(s[2])));
    }

    public long getDestination(long value) {
        for (Range range : rangeList) {
            if (range.isInRange(value)) {
                return range.getDestination(value);
            }
        }
        return value;
    }

    public List<Long> getDestinations(long min, long max) {
        List<Long> destinations = new ArrayList<>();
        for (Range range : rangeList) {
            if (range.isOverlapping(min, max)) {

            }
        }
        return destinations;
    }
}

class Range {
    long destinationStart;
    long min, max;

    public Range(long destinationStart, long min, long length) {
        this.destinationStart = destinationStart;
        this.min = min;
        this.max = min + length;
    }

    public boolean isInRange(long value) {
        return value >= min && value <= max;
    }

    public long getDestination(long value) {
        if (isInRange(value)) {
            return destinationStart + (value - min);
        }
        return -1;
    }

    public boolean isOverlapping(long min, long max) {
        return this.min <= max && this.max >= min;
    }
}