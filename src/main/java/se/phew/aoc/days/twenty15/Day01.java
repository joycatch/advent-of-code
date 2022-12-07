package se.phew.aoc.days.twenty15;

import se.phew.aoc.days.Challenge;

public class Day01 extends Challenge {

    public Day01() {
        super(false);

        for (String line : lines) {

            long up = line.chars().filter(ch -> ch == '(').count();
            long down = line.chars().filter(ch -> ch == ')').count();

            printAnswer(1, up - down);

            int pos = 0;
            int floor = 0;
            for (Character c : line.toCharArray()) {
                pos++;
                floor += c == '(' ? 1 : -1;
                if (floor < 0) {
                    printAnswer(2, pos);
                    break;
                }
            }
        }
    }
}
