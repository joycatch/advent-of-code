package se.phew.aoc.days;

import java.util.HashSet;

public class Day2 extends Challenge {

    public Day2() {
        super();

        HashSet<String> strings = new HashSet<>();
        strings.addAll(lines);

        System.out.println("Alright... ");

        int totalCount = 0;

        for (String line : strings) {
            String[] split = line.split(":");
            String[] parts = split[0].split(" ");
            String[] minMax = parts[0].split("-");
            int min = Integer.parseInt(minMax[0]);
            int max = Integer.parseInt(minMax[1]);
            String character = parts[1];

            String test = split[1];
            /*int count = 0;
            for(int i = 0; i < test.length(); i++) {
                if (test.charAt(i) == character.charAt(0)) {
                    count++;
                }
            }*/

            test = test.trim();
            System.out.println("Text = " + test + " len: " + test.length() + " char " + character.charAt(0));
            System.out.println("min = " + min + " - " + test.charAt(min - 1));
            System.out.println("max = " + max + " - " + test.charAt(max - 1));

            int length = test.length();
            if (length >= min && length >= max) {
                boolean first = character.charAt(0) == test.charAt(min - 1);
                boolean last = character.charAt(0) == test.charAt(max - 1);

                if ((first && !last) || (!first && last)) {
                    totalCount++;
                    System.out.println("yes");

                } else {
                    System.out.println("no");
                }
            }
        }
        System.out.println("Total count: " + totalCount);
    }
}
