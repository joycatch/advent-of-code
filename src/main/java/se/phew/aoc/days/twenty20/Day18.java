package se.phew.aoc.days.twenty20;

import se.phew.aoc.days.Challenge;

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
    ScriptEngine engine = mgr.getEngineByName("JavaScript");

    boolean printEachLine = false;

    public Day18() {
        super();

        printAnswer(1, runPart(1));
        printAnswer(2, runPart(2));
    }

    private long runPart(int part) {
        long sum = 0;
        for (String originalLine : lines) {
            String line = originalLine;
            if (printEachLine) {
                System.out.print(line);
            }
            do {
                String[] splits = line.split("\\(|\\)");
                for (String split : splits) {
                    String patternString = "[0-9]+.*[0-9]+";
                    Pattern pattern = Pattern.compile(patternString);
                    Matcher matcher = pattern.matcher(split);
                    if (matcher.matches()) {
                        String temp = "" + (part == 1 ? evaluatePart1(split) : evaluatePart2(split));
                        split = split.replaceAll("\\*", "\\\\\\*");
                        split = split.replaceAll("\\+", "\\\\\\+");
                        line = line.replaceAll("\\(" + split + "\\)", temp);
                    }
                }
            } while (line.contains("("));

            Object evaluate = (part == 1 ? evaluatePart1(line) : evaluatePart2(line));
            if (evaluate instanceof Double) {
                sum += Double.parseDouble(evaluate.toString());
            } else if (evaluate instanceof Integer) {
                sum += (int) evaluate;
            }
            if (printEachLine) {
                System.out.println(" = " + evaluate.toString());
            }
        }
        return sum;
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
