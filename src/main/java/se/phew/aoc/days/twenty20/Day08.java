package se.phew.aoc.days.twenty20;

import se.phew.aoc.days.Challenge;

public class Day08 extends Challenge {

    private int accumulator = 0;
    private int part1 = 0;

    public Day08() {
        super(false);
        Instruction[] instructions = new Instruction[lines.size()];

        int i = 0;
        int nops = 0;
        int jmps = 0;

        for (String line : lines) {
            String[] split = line.split(" ");
            nops += (split[0].equals("nop")) ? 1 : 0;
            jmps += (split[0].equals("jmp")) ? 1 : 0;
            instructions[i++] = new Instruction(split[0], Integer.parseInt(split[1]));
        }

        testSequence(instructions, 1);
        printAnswer(1, part1);

        swapInstructions(instructions, nops, "nop", "jmp");
        swapInstructions(instructions, jmps, "jmp", "nop");
    }

    private void swapInstructions(Instruction[] instructions, int iterations, String from, String to) {
        for (int i = 0; i < iterations; i++) {
            int counted = 0;
            for (int j = 0; j < instructions.length; j++) {
                if (instructions[j].instruction.equals(from)) {
                    if (counted++ == i) {
                        // System.out.println(from + " -> " + to + " at position " + j + " (iteration: " + counted + ")");
                        instructions[j].instruction = to;
                        if (testSequence(instructions)) {
                            printAnswer(2, accumulator);
                            i = iterations;
                        }
                        instructions[j].instruction = from;
                    }
                }
            }
        }
    }

    private boolean testSequence(Instruction[] instructions) {
        return testSequence(instructions, 2);
    }

    private boolean testSequence(Instruction[] instructions, int part) {
        for (int i = 0; i < instructions.length; ) {
            if (instructions[i].visited) {
                if (part == 1) {
                    part1 = accumulator;
                }
                // Reset all and return
                accumulator = 0;
                for (i = 0; i < instructions.length; i++) {
                    instructions[i].visited = false;
                }
                return false;
            }
            instructions[i].visited = true;
            if (instructions[i].instruction.equals("acc")) {
                accumulator += instructions[i].value;
                i++;
            }
            if (instructions[i].instruction.equals("jmp")) {
                i += instructions[i].value;
                if (i == instructions.length) {
                    break;
                }
                if (i > instructions.length) {
                    return false;
                }
            }
            if (instructions[i].instruction.equals("nop")) {
                i++;
            }
        }
        return true;
    }

    static class Instruction {
        boolean visited = false;
        String instruction;
        int value;

        public Instruction(String instruction, int value) {
            this.instruction = instruction;
            this.value = value;
        }

        @Override
        public String toString() {
            return instruction + " " + ((value >= 0) ? "+" : "") + value;
        }
    }
}