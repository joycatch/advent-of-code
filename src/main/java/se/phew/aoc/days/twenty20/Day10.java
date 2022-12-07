package se.phew.aoc.days.twenty20;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.Collections;

public class Day10 extends Challenge {

    public Day10() {
        super(false);

        ArrayList<Integer> adapters = new ArrayList<>();
        for (String line : lines) {
            adapters.add(Integer.parseInt(line));
        }
        adapters.add(0);
        Collections.sort(adapters);
        adapters.add(adapters.get(adapters.size() - 1) + 3);

        int one = 0;
        int three = 0;

        int previous = 0;
        int consecutiveOnes = 0;
        long result = 1;

        for (int i = 0; i < adapters.size(); i++) {
            int diff = adapters.get(i) - previous;
            switch (diff) {
                case 1:
                    one++;
                    consecutiveOnes++;
                    break;
                case 3:
                    three++;
                    // Note, no break!
                case 2:
                    if (consecutiveOnes > 0) {
                        switch (consecutiveOnes) {
                            case 4: result *= 7; break;
                            case 3: result *= 4; break;
                            case 2: result *= 2; break;
                            case 1: result *= 1; break;
                        }
                    }
                    consecutiveOnes = 0;
                    break;
            }
            previous = adapters.get(i);
            // Print the sorted list
            // System.out.print(value + " ");
        }

        printAnswer(1, one * three);
        printAnswer(2, result);
    }
}
