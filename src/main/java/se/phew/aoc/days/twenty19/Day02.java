package se.phew.aoc.days.twenty19;

import se.phew.aoc.days.Challenge;

public class Day02 extends Challenge {

    public Day02() {
        super();

        // Part 1
        printAnswer(1, getValueAtFirstPosition(12, 2));

        // Part 2
        int part2 = -1;
        outerloop:
        for (int noun = 0; noun < 100; noun++) {
            for (int verb = 0; verb < 100; verb++) {
                if (getValueAtFirstPosition(noun, verb) == 19690720) {
                    part2 = 100 * noun + verb;
                    break outerloop;
                }
            }
        }
        printAnswer(2, part2);
    }

    private int getValueAtFirstPosition(int first, int second) {
        String line = lines.get(0);
        String[] program = line.split(",");
        program[1] = String.valueOf(first);
        program[2] = String.valueOf(second);

        for (int i = 0; i < program.length; i += 4) {
            int opcode = Integer.parseInt(program[i]);
            if (opcode == 99) {
                break;
            }
            int p1 = Integer.parseInt(program[i + 1]);
            int p2 = Integer.parseInt(program[i + 2]);
            int p3 = Integer.parseInt(program[i + 3]);
            int v1 = Integer.parseInt(program[p1]);
            int v2 = Integer.parseInt(program[p2]);
            if (opcode == 1) {
                program[p3] = String.valueOf(v1 + v2);
            }
            if (opcode == 2) {
                program[p3] = String.valueOf(v1 * v2);
            }
        }
        return Integer.parseInt(program[0]);
    }
}
