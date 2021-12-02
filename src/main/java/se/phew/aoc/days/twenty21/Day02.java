package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

public class Day02 extends Challenge {

    public Day02() {
        super();

        Submarine submarine = new Submarine(false);
        followInstructions(submarine);
        printAnswer(1, submarine.position());

        submarine = new Submarine(true);
        followInstructions(submarine);
        printAnswer(2, submarine.position());
    }

    private void followInstructions(Submarine submarine) {
        for (String line : lines) {
            String[] split = line.split(" ");
            submarine.move(split[0], Integer.parseInt(split[1]));
        }
    }

    static class Submarine {
        int x = 0;
        int y = 0;
        int aim = 0;
        boolean useAim;

        public Submarine(boolean useAim) {
            this.useAim = useAim;
        }

        public void move(String instruction, int steps) {
            switch (instruction) {
                case "forward":
                    x += steps;
                    if (useAim) {
                        y += aim * steps;
                    }
                    break;
                case "down":
                    if (!useAim) {
                        y += steps;
                    } else {
                        aim += steps;
                    }
                    break;
                case "up":
                    if (!useAim) {
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
