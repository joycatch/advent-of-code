package se.phew.aoc.days;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Day21 extends Challenge {

    public Day21() {
        super();

        HashMap<String, HashSet<String>> map = new HashMap<>();
        HashMap<String, ArrayList<String>> reverse = new HashMap<>();
        HashMap<String, Integer> count = new HashMap<>();
        HashMap<String, Integer> count2 = new HashMap<>();

        for (String line : lines) {
            line = line.replaceAll("\\)", "");
            String[] split = line.split(" \\(contains ");
            String[] ingredients = split[0].split(" ");
            String[] allergens = split[1].split(", ");

            for (String a : allergens) {
                if (!map.containsKey(a)) {
                    map.put(a, new HashSet<>());
                } else {
                    System.out.println("Suspicious: ");
                    for (String i : ingredients) {
                        System.out.println(" - " + i);
                        map.get(a).add(i);
                    }
                }
            }


            for (String i : ingredients) {
                if (!map.containsKey(i)) {
                    reverse.put(i, new ArrayList<>());
                }
                for (String a : allergens) {
                    reverse.get(i).add(a);
                }
            }

            for (String i : ingredients) {
                if (!count.containsKey(i)) {
                    count.put(i, 0);
                }
                count.put(i, count.get(i) + 1);
            }

            for (String a : allergens) {
                if (!count2.containsKey(a)) {
                    count2.put(a, 0);
                }
                count2.put(a, count2.get(a) + 1);
            }
        }

        //kfcds, nhms, sbzzf, or trh

        System.out.println("LOL");

    }

}
