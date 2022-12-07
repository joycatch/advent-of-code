package se.phew.aoc.days.twenty20;

import se.phew.aoc.days.Challenge;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class Day22 extends Challenge {

    int game = 1;

    public Day22() {
        super(false);

        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");

        populateDecks(p1, p2);

        // Part 1
        do {
            int p1card = p1.playCard();
            int p2card = p2.playCard();

            if (p1card > p2card) {
                p1.addToBottom(p1card, p2card);
            } else {
                p2.addToBottom(p2card, p1card);
            }
        } while ( !p1.isEmpty() && !p2.isEmpty());

        // p1.printDeck();
        // p2.printDeck();
        printAnswer(1, Math.max(p1.calculateScore(), p2.calculateScore()));

        // Part 2
        populateDecks(p1, p2);
        playGame(game, p1, p2);

        // p1.printDeck();
        // p2.printDeck();
        printAnswer(2, Math.max(p1.calculateScore(), p2.calculateScore()));
    }

    private void populateDecks(Player player1, Player player2) {
        Player player = player1;
        for (String line : lines) {
            if (line.startsWith(player1.name)) {
                player = player1;
            } else if (line.startsWith(player2.name)) {
                player = player2;
            } else {
                if (!line.isEmpty()) {
                    player.addCardToDeck(Integer.parseInt(line));
                }
            }
        }
    }

    private int playGame(int gameId, Player p1, Player p2) {
        int roundId = 1;
        HashSet<String> previousRounds = new HashSet<>();
        do {
            String uniqueRoundId = p2.toString() + p2.toString();
            if (!previousRounds.contains(uniqueRoundId)) {
                previousRounds.add(uniqueRoundId);

                int p1card = p1.playCard();
                int p2card = p2.playCard();

                if (p1.canPlaySubGame(p1card) && p2.canPlaySubGame(p2card)) {
                    Player newPlayer1 = new Player(p1, p1card);
                    Player newPlayer2 = new Player(p2, p2card);
                    int winner = playGame(++game, newPlayer1, newPlayer2);
                    if (winner == 1) {
                        p1.addToBottom(p1card, p2card);
                    } else {
                        p2.addToBottom(p2card, p1card);
                    }
                } else {
                    if (p1card > p2card) {
                        p1.addToBottom(p1card, p2card);
                    } else {
                        p2.addToBottom(p2card, p1card);
                    }
                }
            } else {
                return 1;
            }

            roundId++;
        } while ( !p1.isEmpty() && !p2.isEmpty());

        return p1.isEmpty() ? 2 : 1;
    }

    private class Player {
        String name;
        Queue<Integer> deck = new LinkedList<>();

        public Player(String name) {
            this.name = name;
        }

        public Player(Player player, int cards) {
            this.name = player.name;
            this.deck.addAll(player.deck.stream().limit(cards).collect(Collectors.toList()));
        }

        public void addCardToDeck(int card) {
            deck.add(card);
        }

        public int playCard() {
            return deck.poll();
        }

        public boolean canPlaySubGame(int card) {
            return deck.size() >= card;
        }

        public void addToBottom(int winningCard, int losingCard) {
            deck.add(winningCard);
            deck.add(losingCard);
        }

        public boolean isEmpty() {
            return deck.size() == 0;
        }

        public int calculateScore() {
            int size = deck.size();
            int result = 0;
            for (int i = size; i > 0; i--) {
                result += i*deck.poll();
            }
            return result;
        }

        public void printDeck() {
            String result = deck.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(","));
            System.out.println(name + "'s deck: " + result);
        }

        public String toString() {
            return deck.toString();
        }
    }
}
