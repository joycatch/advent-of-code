package se.phew.aoc.days.twenty22;

import se.phew.aoc.days.Challenge;

import java.util.Stack;

public class Day05 extends Challenge {

    Stack[] stacks;

    public Day05() {
        super(false);

        printAnswer(1, run(true));
        printAnswer(2, run(false));
    }

    private String run(boolean part1) {
        loadStacks();

        for (String line : lines) {
            if (line.contains("move")) {
                String[] s = line.replace("move ", "").replace(" from ", " to ").split(" to ");
                int amount = Integer.parseInt(s[0]);
                int from = Integer.parseInt(s[1]) - 1;
                int to = Integer.parseInt(s[2]) - 1;
                if (part1) {
                    for (int i = 0; i < amount; i++) {
                        stacks[to].push(stacks[from].pop());
                    }
                } else {
                    Stack tempStack = new Stack();
                    for (int i = 0; i < amount; i++) {
                        tempStack.push(stacks[from].pop());
                    }
                    for (int i = 0; i < amount; i++) {
                        stacks[to].push(tempStack.pop());
                    }
                }
            }
        }

        String result = "";
        for (Stack stack : stacks) {
            result += stack.peek();
        }
        return result;
    }

    private void loadStacks() {
        int rows = isTest ? 3 : 8;
        int noOfStacks = isTest ? 3 : 9;
        stacks = new Stack[noOfStacks];

        for (int i = 0; i < noOfStacks; i++) {
            stacks[i] = new Stack();
        }

        for (int row = rows - 1; row >= 0; row--) {
            for (int i = 0; i < noOfStacks; i++) {
                char c = lines.get(row).charAt(1 + (i * 4));
                if (c != ' ') {
                    stacks[i].push(c);
                }
            }
        }
    }
}
