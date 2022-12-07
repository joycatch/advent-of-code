package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;

public class Day21 extends Challenge {

    ArrayList<Player> players = new ArrayList<>();
    int[] DICE_THROWS = new int[] {
            1+1+1, 1+1+2, 1+1+3, 1+2+1, 1+2+2, 1+2+3, 1+3+1, 1+3+2, 1+3+3,
            2+1+1, 2+1+2, 2+1+3, 2+2+1, 2+2+2, 2+2+3, 2+3+1, 2+3+2, 2+3+3,
            3+1+1, 3+1+2, 3+1+3, 3+2+1, 3+2+2, 3+2+3, 3+3+1, 3+3+2, 3+3+3 };

    public Day21() {
        super(false);

        Player player1 = new Player(1, Integer.parseInt(lines.get(0).split(": ")[1]));
        Player player2 = new Player(2, Integer.parseInt(lines.get(1).split(": ")[1]));
        players.add(player1);
        players.add(player2);
        DeterministicDice dice = new DeterministicDice();
        boolean aWinner = false;

        while (!aWinner) {
            for (Player player : players) {
                player.move(dice.nextValue() + dice.nextValue() + dice.nextValue());
                if (player.score >= 1000) {
                    aWinner = true;
                    break;
                }
            }
        }
        printAnswer(1, dice.rolls * (Math.min(players.get(0).score, players.get(1).score)));

        player1 = new Player(1, Integer.parseInt(lines.get(0).split(": ")[1]));
        player2 = new Player(2, Integer.parseInt(lines.get(1).split(": ")[1]));

        printAnswer(2, getWinnerCountFromAllUniverses(player1, player2));
    }

    public long getWinnerCountFromAllUniverses(Player player1, Player player2) {
        long[][][][] universes = new long[10][10][22][22];
        universes[player1.position - 1][player2.position -1][0][0] = 1;
        for (int s1 = 0; s1 < 21; s1++) {
            for (int s2 = 0; s2 < 21; s2++) {
                for (int p1 = 0; p1 < 10; p1++) {
                    for (int p2 = 0; p2 < 10; p2++) {
                        for (int p1Roll : DICE_THROWS) {
                            int newP1 = (p1 + p1Roll) % 10;
                            int newS1 = Math.min(s1 + newP1 + 1, 21);
                            if (newS1 == 21) {
                                universes[newP1][p2][newS1][s2] += universes[p1][p2][s1][s2];
                            } else {
                                for (int p2Roll : DICE_THROWS) {
                                    int newP2 = (p2 + p2Roll) % 10;
                                    int newS2 = Math.min(s2 + newP2 + 1, 21);
                                    universes[newP1][newP2][newS1][newS2] += universes[p1][p2][s1][s2];
                                }
                            }
                        }
                    }
                }
            }
        }
        long playerOneWins = 0L;
        long playerTwoWins = 0L;
        for (int p1 = 0; p1 < 10; p1++) {
            for (int p2 = 0; p2 < 10; p2++) {
                for (int score = 0; score < 21; score++) {
                    playerOneWins += universes[p1][p2][21][score];
                    playerTwoWins += universes[p1][p2][score][21];
                }
            }
        }
        return Math.max(playerOneWins, playerTwoWins);
    }

    class Player {
        int number;
        int position;
        int score = 0;

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
