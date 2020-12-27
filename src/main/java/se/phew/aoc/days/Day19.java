package se.phew.aoc.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day19 extends Challenge {

    HashMap<Integer, Rule> allRules = new HashMap<>();

    public Day19() {
        super(false);

        for (String line : lines) {
            if (line.contains(":")) {
                String[] split = line.split(": ");
                int i = Integer.parseInt(split[0]);
                if (split[1].contains("\"")) {
                    allRules.put(i, new Rule(split[1].charAt(1)));
                } else {
                    allRules.put(i, new Rule(split[1]));
                }
            }
        }

        populateCandidates();

        int maxLength = 0;
        for (String line : lines) {
            if (line.length() > maxLength) {
                maxLength = line.length();
            }
        }

        print("Length of largest message: " + maxLength);

        printAnswer(1, countValidMessages());

        // Rule 0: 8 11
        // Rule 0: (42 | 42 8) (42 31 | 42 11 31)

        // 42 42 31
        // 42 42 42 31
        // 42 42 42 ... 31
        // 42 42 42 42 42 42 42 42 42 42 31
        // 42 42 31
        // 42 42 42 31 31
        // 42 42 42 42 31 31 31
        // 42 42 42 42 42 31 31 31 31
        // 42 42 42 42 42 42 31 31 31 31 31

        String r42 = generateRegexp(allRules.get(42));
        String r31 = generateRegexp(allRules.get(31));

        int count = 0;
        count += countMatchingRegexp("^"+ r42 + "{2,11}" + r31 + "{1}$");
        count += countMatchingRegexp("^"+ r42 + "{3,}" + r31 + "{2}$");
        count += countMatchingRegexp("^"+ r42 + "{4,}" + r31 + "{3}$");
        count += countMatchingRegexp("^"+ r42 + "{5,}" + r31 + "{4}$");
        count += countMatchingRegexp("^"+ r42 + "{6,}" + r31 + "{5}$");

        printAnswer(2, count);
    }

    private int countMatchingRegexp(String regexp) {
        // System.out.println(regexp);
        Pattern pattern = Pattern.compile(regexp);
        int count = 0;
        for (String line : lines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()) {
                count++;
                // System.out.println(matcher.group());
            }
        }
        return count;
    }

    private String generateRegexp(Rule rule) {
        String result = "(";
        for (String c : rule.candidates) {
            result += "" + c + "|";
        }
        result = result.substring(0, result.length() - 1) + ")";
        return result;
    }

    private int countValidMessages() {
        int count = 0;
        for (String line : lines) {
            if (!line.contains(":") && !line.isEmpty()) {
                Rule rule0 = allRules.get(0);
                if (rule0.isValidMessage(line)) {
                    count++;
                }
            }
        }
        return count;
    }

    private void populateCandidates() {
        do {
            for (int id : allRules.keySet()) {
                Rule rule = allRules.get(id);
                if (!rule.isPopulated()) {
                    // System.out.println("Trying to populate " + id);
                    rule.populateCandidates();
                }
            }
        } while (!allRulesPopulated());
        print("All rules fully populated");
    }

    public boolean allRulesPopulated() {
        for (Rule rule : allRules.values()) {
            if (!rule.isPopulated()) {
                return false;
            }
        }
        return true;
    }

    class Rule {

        ArrayList<String> rules = new ArrayList<>();
        ArrayList<String> candidates = new ArrayList<>();

        public Rule(char c) {
            this.candidates.add("" + c);
        }

        public Rule(String input) {
            if (input.contains("|")) {
                this.rules.addAll(Arrays.asList(input.split(" \\| ")));
            } else {
                this.rules.add(input);
            }
        }

        public boolean isValidMessage(String input) {
            return candidates.contains(input);
        }

        public boolean isPopulated() {
            return this.candidates.size() > 0;
        }

        public boolean canBePopulated() {
            for (String rule : rules) {
                String[] ids = rule.split(" ");
                for (String id : ids) {
                    int i = Integer.parseInt(id);
                    if (!allRules.containsKey(i) || !allRules.get(i).isPopulated()) {
                        return false;
                    }
                }
            }
            return true;
        }

        public void populateCandidates() {
            if (!isPopulated() && canBePopulated()) {
                for (String rule : rules) {
                    String[] ids = rule.split(" ");
                    ArrayList<Rule> test = new ArrayList<>();
                    for (String id : ids) {
                        int i = Integer.parseInt(id);
                        test.add(allRules.get(i));
                    }
                    Rule rule1 = test.get(0);
                    if (test.size() == 1) {
                        for (String c1 : rule1.candidates) {
                            candidates.add(c1);
                        }
                    }
                    if (test.size() == 2) {
                        Rule rule2 = test.get(1);
                        for (String c1 : rule1.candidates) {
                            ArrayList<String> tempCandidates = (ArrayList<String>) rule2.candidates.clone();
                            for (String c2 : tempCandidates) {
                                candidates.add(c1 + c2);
                            }
                        }
                    }
                    if (test.size() == 3) {
                        Rule rule2 = test.get(1);
                        Rule rule3 = test.get(2);
                        for (String c1 : rule1.candidates) {
                            for (String c2 : rule2.candidates) {
                                for (String c3 : rule3.candidates) {
                                    candidates.add(c1 + c2 + c3);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
