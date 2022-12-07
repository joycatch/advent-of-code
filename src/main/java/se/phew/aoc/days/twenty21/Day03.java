package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;

public class Day03 extends Challenge {

    public Day03() {
        super(false);

        String gamma = "";
        String epsilon = "";

        int[] count = new int[lines.get(0).length()];
        for (String line : lines) {
            for (int i = 0; i < count.length; i++) {
                count[i] += Character.getNumericValue(line.charAt(i));
            }
        }
        for (int i = 0; i < count.length; i++) {
            gamma += String.valueOf(count[i] >= (double) lines.size() / 2 ? '1' : '0');
            epsilon += String.valueOf(count[i] >= (double) lines.size() / 2 ? '0' : '1');
        }

        ArrayList<String> oxygen = new ArrayList<>(lines);
        ArrayList<String> CO2 = new ArrayList<>(lines);

        filter(oxygen, '1', '0');
        filter(CO2, '0', '1');

        printAnswer(1, Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2));
        printAnswer(2, Integer.parseInt(oxygen.get(0), 2) * Integer.parseInt(CO2.get(0), 2));
    }

    private void filter(ArrayList<String> list, char most, char least) {
        for (int i = 0; list.size() > 1; i++) {
            filter(list, i, most, least);
        }
    }

    private void filter(ArrayList<String> list, int i, char most, char least) {
        int count = 0;
        for (String line : list) {
            count += Character.getNumericValue(line.charAt(i));
        }
        char toTestAgainst = count >= (double) list.size() / 2 ? most : least;
        ArrayList<String> discard = new ArrayList<>();
        for (String line : list) {
            if (line.charAt(i) != toTestAgainst) {
                discard.add(line);
            }
        }
        list.removeAll(discard);
    }
}
