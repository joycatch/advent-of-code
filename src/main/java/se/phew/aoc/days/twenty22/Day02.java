package se.phew.aoc.days.twenty22;

import se.phew.aoc.days.Challenge;

public class Day02 extends Challenge {

    public Day02() {
        super();

        int part1 = 0;
        int part2 = 0;
        for (String line : lines) {
            String[] s = line.split(" ");

            Shape opponent = Shape.from(s[0]);
            Shape me = Shape.from(s[1]);
            Outcome outcome = outcome(opponent, me);
            part1 += me.value + outcome.value;

            outcome = Outcome.from(s[1]);
            me = needToPlayShape(outcome, opponent);
            part2 += me.value + outcome.value;
        }

        printAnswer(1, part1);
        printAnswer(2, part2);
    }

    private Outcome outcome(Shape opponent, Shape me) {
        if (opponent != me) {
            switch (me) {
                case ROCK:
                    return opponent == Shape.PAPER ? Outcome.LOSS : Outcome.WIN;
                case PAPER:
                    return opponent == Shape.SCISSORS ? Outcome.LOSS : Outcome.WIN;
                case SCISSORS:
                    return opponent == Shape.ROCK ? Outcome.LOSS : Outcome.WIN;
            }
        }
        return Outcome.DRAW;
    }

    private Shape needToPlayShape(Outcome outcome, Shape opponent) {
        if (outcome != Outcome.DRAW) {
            switch (opponent) {
                case ROCK:
                    return outcome == Outcome.WIN ? Shape.PAPER : Shape.SCISSORS;
                case PAPER:
                    return outcome == Outcome.WIN ? Shape.SCISSORS : Shape.ROCK;
                case SCISSORS:
                    return outcome == Outcome.WIN ? Shape.ROCK : Shape.PAPER;
            }
        }
        return opponent;
    }

    enum Outcome {
        WIN(6), DRAW(3), LOSS(0);

        int value;

        Outcome(int value) {
            this.value = value;
        }

        static Outcome from(String o) {
            return "Y".equals(o) ? DRAW : "X".equals(o) ? LOSS : WIN;
        }
    }

    enum Shape {
        ROCK(1), PAPER(2), SCISSORS(3);

        int value;

        Shape(int value) {
            this.value = value;
        }

        static Shape from(String s) {
            switch (s) {
                case "A": case "X": return ROCK;
                case "B": case "Y": return PAPER;
            }
            return SCISSORS;
        }
    }
}
