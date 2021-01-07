package se.phew.aoc.days;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class Day22 extends Challenge {

    public Day22() {
        super();

        // Part 1
        Player p1 = new Player("Player 1");
        Player p2 = new Player("Player 2");

        Player player = p1;
        for (String line : lines) {
            if (line.startsWith(p1.name)) {
                player = p1;
            } else if (line.startsWith(p2.name)) {
                player = p2;
            } else {
                if (!line.isEmpty()) {
                    player.addCardToDeck(Integer.parseInt(line));
                }
            }
        }

        do {
            int p1card = p1.playCard();
            int p2card = p2.playCard();

            if (p1card > p2card) {
                p1.addToBottom(p1card, p2card);
            } else {
                p2.addToBottom(p2card, p1card);
            }
        } while ( p1.deckIsNotEmpty() && p2.deckIsNotEmpty());

        p1.printDeck();
        p2.printDeck();

        int p1score = p1.calculateScore();
        int p2score = p2.calculateScore();
        int result = Math.max(p1score, p2score);
        printAnswer(1, result);

        // Part 2

        // TBD
    }

    private class Player {
        String name;
        Queue<Integer> deck = new LinkedList<>();

        public Player(String name) {
            this.name = name;
        }

        public void addCardToDeck(int card) {
            deck.add(card);
        }

        public int playCard() {
            return deck.poll();
        }

        public void addToBottom(int winningCard, int losingCard) {
            deck.add(winningCard);
            deck.add(losingCard);
        }

        public boolean deckIsNotEmpty() {
            return deck.size() > 0;
        }

        public void printDeck() {
            String result = deck.stream().map(a -> String.valueOf(a)).collect(Collectors.joining(","));
            print(name + "'s deck: " + result);
        }

        public int calculateScore() {
            int size = deck.size();
            int result = 0;
            for (int i = size; i > 0; i--) {
                result += i*deck.poll();
            }
            return result;
        }
    }
}
