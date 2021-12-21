package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;

public class Day21 extends Challenge {

    ArrayList<Player> players;

    public Day21() {
        super();

        players = new ArrayList<>();
        players.add(new Player(1, Integer.parseInt(lines.get(0).split(": ")[1])));
        players.add(new Player(2, Integer.parseInt(lines.get(1).split(": ")[1])));
        DeterministicDice dice = new DeterministicDice();
        boolean aWinner = false;

        while (!aWinner) {
            for (Player player : players) {
                int first = dice.nextValue();
                int second = dice.nextValue();
                int third = dice.nextValue();
                player.move(first + second + third);
                // System.out.println("Player " + player.number + " rolls " + first + "+" + second + "+" + third +
                // " and moves to space " + player.position + " for a total score of " + player.score + ".");
                if (player.score >= 1000) {
                    aWinner = true;
                    break;
                }
            }
        }

        printAnswer(1, dice.rolls * (Math.min(players.get(0).score, players.get(1).score)));
        printAnswer(2, "");
    }

    class Player {
        int number;
        int position;
        long score = 0;

        public Player(int number, int position) {
            this.number = number;
            this.position = position;
        }

        void move(int steps) {
            position += steps;
            position = position % 10 == 0 ? 10 : position % 10;
            score += position;
        }
    }

    class DeterministicDice {
        int value = 1;
        int rolls = 0;

        public int nextValue() {
            rolls++;
            if (value == 101) {
                value = 1;
            }
            return value++;
        }
    }
}
