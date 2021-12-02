package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

public class Day02 extends Challenge {

    public Day02() {
        super();

        Submarine submarine = new Submarine();
        followInstructions(submarine, true);
        printAnswer(1, submarine.position());

        submarine = new Submarine();
        followInstructions(submarine, false);
        printAnswer(2, submarine.position());
    }

    private void followInstructions(Submarine submarine, boolean part1) {
        for (String line : lines) {
            String[] split = line.split(" ");
            submarine.move(split[0], Integer.parseInt(split[1]), part1);
        }
    }

    static class Submarine {
        int x = 0;
        int y = 0;
        int aim = 0;

        public void move(String instruction, int steps, boolean part1) {
            switch (instruction) {
                case "forward":
                    x += steps;
                    if (!part1) {
                        y += aim * steps;
                    }
                    break;
                case "down":
                    if (part1) {
                        y += steps;
                    } else {
                        aim += steps;
                    }
                    break;
                case "up":
                    if (part1) {
                        y -= steps;
                    } else {
                        aim -= steps;
                    }
                    break;
            }
        }

        public int position() {
            return x * y;
        }
    }
}
