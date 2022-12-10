package se.phew.aoc.days.twenty22;

import se.phew.aoc.days.Challenge;

import java.util.*;

public class Day09 extends Challenge {

    public Day09() {
        super(false);

        Rope rope1 = new Rope(2);
        Rope rope2 = new Rope(10);

        for (String line : lines) {
            String[] s = line.split(" ");
            for (int i = 0; i < Integer.parseInt(s[1]); i++) {
                rope1.move(s[0]);
                rope2.move(s[0]);
            }
        }

        printAnswer(1, rope1.tail().countLocations());
        printAnswer(2, rope2.tail().countLocations());
    }

    class Rope {
        List<Part> parts = new ArrayList<>();

        public Rope(int length) {
            for (int i = 0; i < length; i++) {
                parts.add(new Part(i == 0 ? "H" : String.valueOf(i)));
            }
        }

        public void move(String direction) {
            Part previous = null;
            for (Part part : parts) {
                part.move(direction, previous);
                previous = part;
            }
        }

        public Part tail() {
            return parts.get(parts.size() - 1);
        }
    }

    class Part {
        String name;
        int x = 0, y = 0;
        Set<String> locations = new HashSet<>();

        public Part(String name) {
            this.name = name;
            locations.add("0,0");
        }

        public int countLocations() {
            return locations.size();
        }

        public void move(String direction, Part previous) {
            if (previous == null) {
                switch (direction) {
                    case "R": x++; break;
                    case "U": y++; break;
                    case "D": y--; break;
                    case "L": x--; break;
                }
                return;
            }
            int diffX = (previous.x - x);
            int diffY = (previous.y - y);

            if (Math.abs(diffX) <= 1 && Math.abs(diffY) <= 1) {
                return;
            }

            y += Integer.signum(diffY);
            x += Integer.signum(diffX);

            locations.add(x + "," + y);
        }
    }
}
