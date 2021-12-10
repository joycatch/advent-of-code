package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

public class Day10 extends Challenge {

    public Day10() {
        super();

        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : "([{<>}])".toCharArray()) {
            map.put(c, 0);
        }
        ArrayList<Long> scores = new ArrayList<>();

        for (String line : lines) {
            Stack<Character> stack = new Stack<>();
            boolean isCorrupt = false;
            for (char c : line.toCharArray()) {
                if (isOpener(c)) {
                    stack.add(c);
                } else {
                    if (getCloser(stack.pop()) != c) {
                        map.put(c, map.get(c) + 1);
                        isCorrupt = true;
                        break;
                    }
                }
            }
            if (!isCorrupt) {
                long count = 0;
                while (!stack.isEmpty()) {
                    count = 5 * count + getCloserPoints(getCloser(stack.pop()));
                }
                scores.add(count);
            }
        }

        Collections.sort(scores);

        printAnswer(1, map.get(')') * 3 + map.get(']') * 57 + map.get('}') * 1197 + map.get('>') * 25137);
        printAnswer(2, scores.get((scores.size()/2)));
    }

    private int getCloserPoints(char c) {
        switch (c) {
            case ')': return 1;
            case ']': return 2;
            case '}': return 3;
            case '>': return 4;
        }
        return -1;
    }

    private char getCloser(char c) {
        switch (c) {
            case '(': return ')';
            case '[': return ']';
            case '{': return '}';
            case '<': return '>';
        }
        return '?';
    }

    private boolean isOpener(char c) {
        return c == '(' || c == '[' || c == '{' || c == '<' ;
    }
}
