package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

public class Day17 extends Challenge {

    static int minX, minY, maxX, maxY;

    public Day17() {
        super(false);

        String line = lines.get(0).replaceAll("target area\\: x=", "").replaceAll(" y=", "");
        String[] split = line.split(",");
        minX = Integer.parseInt(split[0].split("\\.\\.")[0]);
        maxX = Integer.parseInt(split[0].split("\\.\\.")[1]);
        minY = Integer.parseInt(split[1].split("\\.\\.")[0]);
        maxY = Integer.parseInt(split[1].split("\\.\\.")[1]);

        int highest = 0;
        int velocities = 0;

        for (int x = 0; x <= maxX; x++) {
            for (int y = -500; y <= 500; y++) {
                Probe probe = new Probe(x, y);
                while (!probe.inTargetArea() && probe.x <= maxX && probe.y >= minY) {
                    probe.step();
                    if (probe.inTargetArea()) {
                        if (probe.height > highest) {
                            highest = probe.height;
                        }
                        velocities++;
                        break;
                    }
                }
            }
        }

        printAnswer(1, highest);
        printAnswer(2, velocities);
    }

    static class Probe {
        int x = 0;
        int y = 0;
        int vx;
        int vy;
        int height = 0;

        public Probe(int x, int y) {
            this.vx = x;
            this.vy = y;
        }

        public void step() {
            if (y >= height) {
                height = y;
            }
            x += vx;
            y += vy;
            vx = Math.max(vx - 1, 0);
            vy--;
        }

        public boolean inTargetArea() {
            return x >= minX && x <= maxX && y >= minY && y <= maxY;
        }
    }
}