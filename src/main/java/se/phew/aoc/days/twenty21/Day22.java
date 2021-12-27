package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

public class Day22 extends Challenge {

    public static final String DOTDOT = "\\.\\.";
    List<Cuboid> cuboids = new ArrayList<>();
    List<Cuboid> intersects = new ArrayList<>();

    public Day22() {
        super(true);

        int i = 0;

        for (String line : lines) {
            String l = line.replaceAll("(x|y|z)=", "");
            String[] split = l.replaceAll("(on|off) ", "").split(",");
            int xMin = Integer.parseInt(split[0].split(DOTDOT)[0]);
            int xMax = Integer.parseInt(split[0].split(DOTDOT)[1]);
            int yMin = Integer.parseInt(split[1].split(DOTDOT)[0]);
            int yMax = Integer.parseInt(split[1].split(DOTDOT)[1]);
            int zMin = Integer.parseInt(split[2].split(DOTDOT)[0]);
            int zMax = Integer.parseInt(split[2].split(DOTDOT)[1]);
            Cuboid cuboid = new Cuboid(xMin, xMax, yMin, yMax, zMin, zMax);
            if (!(Math.max(Math.abs(cuboid.xMin), Math.abs(cuboid.xMax)) <= 50
                    && Math.max(Math.abs(cuboid.yMin), Math.abs(cuboid.yMax)) <= 50
                    && Math.max(Math.abs(cuboid.zMin), Math.abs(cuboid.zMax)) <= 50)) {
                //continue;
            }

            ArrayList<Cuboid> temp = new ArrayList<>();
            for (Cuboid other : cuboids) {
                Optional<Cuboid> inter = cuboid.intersects(other);
                if (inter.isPresent()) {
                    temp.add(inter.get());
                }
            }
            intersects.addAll(temp);
            if (line.startsWith("on")) {
                cuboids.add(cuboid);
            }
            System.out.println("Handled line: " + ++i);
        }

        long sum = 0;
        long part1 = 0;
        for (Cuboid cuboid : cuboids) {
            if (Math.max(Math.abs(cuboid.xMin), Math.abs(cuboid.xMax)) <= 50
                    && Math.max(Math.abs(cuboid.yMin), Math.abs(cuboid.yMax)) <= 50
                    && Math.max(Math.abs(cuboid.zMin), Math.abs(cuboid.zMax)) <= 50) {
                part1 += cuboid.count();
            }
            sum += cuboid.count();
        }
        long sum2 = 0;
        for (Cuboid cuboid : intersects) {
            sum2 += cuboid.count();
            if (Math.max(Math.abs(cuboid.xMin), Math.abs(cuboid.xMax)) <= 50
                    && Math.max(Math.abs(cuboid.yMin), Math.abs(cuboid.yMax)) <= 50
                    && Math.max(Math.abs(cuboid.zMin), Math.abs(cuboid.zMax)) <= 50) {
                part1 -= cuboid.count();
            }
        }

        printAnswer(1, part1);
        printAnswer(2, sum - sum2);
    }

    class Cuboid {
        int xMin, xMax, yMin, yMax, zMin, zMax;

        public Cuboid(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
            this.xMin = xMin;
            this.xMax = xMax;
            this.yMin = yMin;
            this.yMax = yMax;
            this.zMin = zMin;
            this.zMax = zMax;

            print();
        }

        long count() {
            long count = (long) (1 + xMax - xMin) * (long) (1 + yMax - yMin) * (long) (1 + zMax - zMin);
            return count;
        }

        public void print() {
            int count = 0;
            for (int x = xMin; x <= xMax; x++) {
                for (int y = yMin; y <= yMax; y++) {
                    for (int z = xMin; z <= zMax; z++) {
                        System.out.println(x + "," + y + "," + z + " - box: " + ++count);
                    }
                }
            }
        }

        public Optional<Cuboid> intersects(Cuboid c) {
            if (xMax >= c.xMin && xMin <= c.xMax && yMax >= c.yMin && yMin <= c.yMax && zMax >= c.zMin && zMin <= c.zMax) {
                int minX = Math.max(c.xMin, xMin);
                int maxX = Math.min(c.xMax, xMax);
                int minY = Math.max(c.yMin, yMin);
                int maxY = Math.min(c.yMax, yMax);
                int minZ = Math.max(c.zMin, zMin);
                int maxZ = Math.min(c.zMax, zMax);
                System.out.println("Intersect cube 2");
                return Optional.of(new Cuboid(minX, maxX, minY, maxY, minZ, maxZ));
            }
            return Optional.empty();
        }
    }
}
