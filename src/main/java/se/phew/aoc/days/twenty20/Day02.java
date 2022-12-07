package se.phew.aoc.days.twenty20;

import se.phew.aoc.days.Challenge;

public class Day02 extends Challenge {

    public Day02() {
        super(false);

        int totalCountPart1 = 0;
        int totalCountPart2 = 0;

        for (String line : lines) {
            String[] split = line.split(":");
            String[] parts = split[0].split(" ");
            String[] minMax = parts[0].split("-");
            int min = Integer.parseInt(minMax[0]);
            int max = Integer.parseInt(minMax[1]);
            String character = parts[1];
            String password = split[1];

            int count = 0;
            for(int i = 0; i < password.length(); i++) {
                if (password.charAt(i) == character.charAt(0)) {
                    count++;
                }
            }
            if (count >= min && count <= max) {
                totalCountPart1++;
            }

            password = password.trim();
            int length = password.length();
            if (length >= min && length >= max) {
                boolean first = character.charAt(0) == password.charAt(min - 1);
                boolean last = character.charAt(0) == password.charAt(max - 1);
                if ((first && !last) || (!first && last)) {
                    totalCountPart2++;
                }
            }
        }
        printAnswer(1, totalCountPart1);
        printAnswer(2, totalCountPart2);
    }
}
