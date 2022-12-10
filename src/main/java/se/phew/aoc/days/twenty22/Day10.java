package se.phew.aoc.days.twenty22;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Day10 extends Challenge {

    int X = 1;

    public Day10() {
        super(false);

        Queue<Instruction> instructions = new LinkedList<>();

        for (String line : lines) {
            if ("noop".equals(line)) {
                instructions.add(new Instruction(1, "noop", 0));
                continue;
            }
            String[] split = line.split(" ");
            instructions.add(new Instruction(2, split[0], Integer.parseInt(split[1])));
        }

        int part1 = 0;

        int cycle = 1;

        List<Character> CRT = new ArrayList<>();
        while (!instructions.isEmpty()) {
            Instruction instruction = instructions.peek();

            if (cycle == 20 || (cycle - 20) % 40 == 0) {
                part1 += cycle * X;
            }

            char character = '.';
            int index = cycle % 40;
            if (index == X || index == X + 1 || index == X + 2) {
                character = '#';
            }
            CRT.add(character);

            instruction.execute();
            if (instruction.isDone()) {
                instructions.remove();
            }
            cycle++;
        }

        printAnswer(1, part1);

        for (int i = 0; i < CRT.size(); i += 40) {
            StringBuilder row = new StringBuilder();
            CRT.subList(i, i + 40).forEach(row::append);
            printOnly(row.toString());
        }
        printEmptyLine();
        printAnswer(2, "RKAZAJBR");
    }

    class Instruction {
        String instruction;
        int value;
        int cycles;

        public Instruction(int cycles, String instruction, int value) {
            this.cycles = cycles;
            this.instruction = instruction;
            this.value = value;
        }

        public void execute() {
            cycles--;
            if (isDone()) {
                complete();
            }
        }

        public boolean isDone() {
            return cycles == 0;
        }

        public void complete() {
            X += value;
        }
    }
}
