package se.phew.aoc.days;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Day18 extends Challenge {
    ScriptEngineManager mgr = new ScriptEngineManager();
    ScriptEngine engine;

    public Day18() {
        super();

        engine = mgr.getEngineByName("JavaScript");

        /*
        lines = new ArrayList<>();
        lines.add("1 + 2 * 3 + 4 * 5 + 6");
        lines.add("1 + (2 * 3) + (4 * (5 + 6))");
        lines.add("2 * 3 + (4 * 5)");
        lines.add("5 + (8 * 3 + 9 + 3 * 4 * 3)");
        lines.add("5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))");
        lines.add("((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2");
         */

        long sum = 0;
        for (String line : lines) {
            System.out.print(line);
            do {
                String[] splits = line.split("\\(|\\)");
                for (String split : splits) {
                    String patternString = "[0-9]+.*[0-9]+";
                    Pattern pattern = Pattern.compile(patternString);
                    Matcher matcher = pattern.matcher(split);
                    if (matcher.matches()) {
                        String temp = "" + evaluatePart2(split);
                        split = split.replaceAll("\\*", "\\\\\\*");
                        split = split.replaceAll("\\+", "\\\\\\+");
                        line = line.replaceAll("\\(" + split + "\\)", temp);
                    }
                }
            } while (line.contains("("));

            Object evaluate = evaluatePart2(line);
            if (evaluate instanceof Double) {
                sum += Double.parseDouble(evaluate.toString());
            } else if (evaluate instanceof Integer) {
                System.out.println(" : " + (int) evaluate);
            }
            // System.out.println(sum);
        }

        System.out.println("Sum: " + sum);

    }

    private Object evaluatePart1(String input) {
        input = input.replaceAll(" ", "");
        int count = 0;
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '+' || input.charAt(i) == '*') {
                count++;
            }
        }
        input = String.join("", Collections.nCopies(count + 1, "(")) + input;
        input = input.replaceAll("\\+", "\\)\\+");
        input = input.replaceAll("\\*", "\\)\\*");
        input = input + ")";
        try {
            return engine.eval(input);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private Object evaluatePart2(String input) {
        input = input.replaceAll(" ", "");
        List<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile("([0-9]+\\+)+[0-9]+").matcher(input);
        while (m.find()) {
            String group = m.group();
            group = group.replaceAll("\\+", "\\\\\\+");
            input = input.replaceFirst(group, "(" + group + ")");
            allMatches.add(group);
        }
        try {
            return engine.eval(input);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
