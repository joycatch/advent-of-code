package se.phew.aoc.days;

import java.util.HashMap;

public class Day07 extends Challenge {

    public Day07() {
        super();

        HashMap<String, Bag> allBags = new HashMap<>();

        for (String line : lines) {
            line = line.replaceAll(" bags\\.", "");
            line = line.replaceAll(" bags, ", " bag, ");
            line = line.replaceAll(" bag, ", ",");
            String[] split = line.split(" bags contain ");
            if (!allBags.containsKey(split[0])) {
                allBags.put(split[0], new Bag(split[0]));
            }
            if (split.length == 2 && !"no other".contains(split[1])) {
                for (String bag : split[1].split(",")) {
                    String[] countAndColor = bag.split(" ");
                    String color = countAndColor[1] + " " + countAndColor[2];
                    if (!allBags.containsKey(color)) {
                        allBags.put(color, new Bag(color));
                    }
                    allBags.get(split[0]).addBags(allBags.get(color), Integer.parseInt(countAndColor[0]));
                }
            }
        }

        int count = 0;
        for (String color : allBags.keySet()) {
            if (allBags.get(color).containsBag("shiny gold")) {
                count++;
            }
        }

        printAnswer(1, count);
        printAnswer(2, allBags.get("shiny gold").countBags());
    }

    static class Bag {
        private String color;
        private HashMap<Bag, Integer> bags = new HashMap<>();

        public Bag(String color) {
            this.color = color;
        }

        public void addBags(Bag bag, int count) {
            this.bags.put(bag, count);
        }

        public boolean containsBag(String color) {
            if (this.color.equals(color)) {
                return false;
            }
            for (Bag b : bags.keySet()) {
                if (b.color.equals(color) || b.containsBag(color)) {
                    return true;
                }
            }
            return false;
        }

        public int countBags() {
            int result = 0;
            for (Bag bag : this.bags.keySet()) {
                result += this.bags.get(bag) + this.bags.get(bag) * bag.countBags();
            }
            return result;
        }
    }
}
