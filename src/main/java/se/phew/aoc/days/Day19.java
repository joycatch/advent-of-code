package se.phew.aoc.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Day19 extends Challenge {

    HashMap<Integer, Rule> allRules = new HashMap<>();

    public Day19() {
        super(false);

        for (String line : lines) {
            if (line.contains(":")) {
                String[] split = line.split(": ");
                int i = Integer.parseInt(split[0]);
                if (split[1].contains("\"")) {
                    allRules.put(i, new Rule(i, split[1].charAt(1)));
                } else {
                    allRules.put(i, new Rule(i, split[1]));
                }
            }
        }

        do {
            for (int id : allRules.keySet()) {
                Rule rule = allRules.get(id);
                if (!rule.isPopulated()) {
                    System.out.println("Trying to populate " + id);
                    rule.populateCandidates();
                }
            }
        } while (!allRulesPopulated());

        System.out.println("All rules fully populated");


        allRules.put(8, new Rule(8, "42 | 42 8"));
        allRules.get(0).removeCandidates();


        do {
            for (int id : allRules.keySet()) {
                Rule rule = allRules.get(id);
                if (!rule.isPopulated()) {
                    System.out.println("Trying to populate 2 - " + id);
                    rule.populateCandidates();
                }
            }
        } while (!allRulesPopulated());

        System.out.println("All rules fully populated 2");


        // Let's find those that match

        int count = 0;
        for (String line : lines) {
            if (!line.contains(":") && !line.isEmpty()) {
                Rule rule0 = allRules.get(0);
                if (rule0.isValidMessage(line)) {
                    count++;
                }
            }
        }
        System.out.println("Matching: " + count);
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
        int id = -1;

        public Rule(int id, char c) {
            this.id = id;
            this.candidates.add("" + c);
        }

        public Rule(int id, String input) {
            this.id = id;
            if (input.contains("|")) {
                this.rules.addAll(Arrays.asList(input.split(" \\| ")));
            } else {
                this.rules.add(input);
            }
            populateCandidates();
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
                            for (String c2 : rule2.candidates) {
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

        public void removeCandidates() {
            this.candidates = new ArrayList<>();
        }
    }
}
