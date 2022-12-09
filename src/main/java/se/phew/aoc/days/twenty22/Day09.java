package se.phew.aoc.days.twenty22;

import se.phew.aoc.days.Challenge;

import java.util.*;

public class Day09 extends Challenge {

    public Day09() {
        super(false);

        Rope ropePart1 = new Rope(1);
        Rope ropePart2 = new Rope(10);

        for (String line : lines) {
            String[] s = line.split(" ");

            for (int i = 0; i < Integer.parseInt(s[1]); i++) {
                ropePart1.move(s[0]);
                ropePart2.move(s[0]);
                //printTest(ropePart2);
            }
        }

        printAnswer(1, ropePart1.tail().countLocations());
        printAnswer(2, ropePart2.tail().countLocations());
    }

    private void printTest(Rope ropePart2) {
        int max = 5;
        for (int y = max; y > -max; y--) {
            for (int x = -max; x < max; x++) {
                boolean found = false;
                if (ropePart2.headX == x && ropePart2.headY == y) {
                    System.out.print("H");
                    found = true;
                } else {
                    for (Part part : ropePart2.parts) {
                        if (part.x == x && part.y == y) {
                            System.out.print(part.name);
                            found = true;
                            break;
                        }
                    }
                }
                if (!found) {
                    System.out.print(".");
                }
            }
            System.out.println("");
        }

        System.out.println();
        System.out.println();
    }

    class Rope {
        int headX, headY;
        List<Part> parts = new ArrayList<>();

        public Rope(int length) {
            for (int i = 1; i < length + 1; i++) {
                parts.add(new Part(String.valueOf(i)));
            }
        }

        public void move(String direction) {
            switch (direction) {
                case "R": headX++; break;
                case "U": headY++; break;
                case "D": headY--; break;
                case "L": headX--; break;
            }
            int x = headX;
            int y = headY;
            for (Part part : parts) {
                part.move(direction, x, y);
                x = part.x;
                y = part.y;
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

        public void move(String direction, int previousX, int previousY) {
            int diffX = Math.abs(previousX - x);
            int diffY = Math.abs(previousY - y);
            int diff = diffX + diffY;
            switch (diff) {
                case 1:
                    break;
                case 2:
                    if (diffX == 0 || diffY == 0) {
                        switch (direction) {
                            case "R": x++; break;
                            case "U": y++; break;
                            case "D": y--; break;
                            case "L": x--; break;
                        }
                    }
                    break;
                case 3:
                    x = previousX;
                    y = previousY;
                    switch (direction) {
                        case "R": x--; break;
                        case "U": y--; break;
                        case "D": y++; break;
                        case "L": x++; break;
                    }
                    break;
                case 4:
                    switch (direction) {
                        case "R": x++; break;
                        case "U": y++; break;
                        case "D": y--; break;
                        case "L": x--; break;
                    }
                    break;
            }
            String location = x + "," + y;
            // System.out.println(location);
            locations.add(location);
        }
    }
}
