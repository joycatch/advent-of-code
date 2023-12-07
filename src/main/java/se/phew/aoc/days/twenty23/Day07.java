package se.phew.aoc.days.twenty23;

import se.phew.aoc.days.Challenge;

import java.util.*;

public class Day07 extends Challenge {

    public Day07() {
        super(false);

        for (int part = 1; part < 3; part++) {
            List<Hand> hands = new ArrayList<>();
            for (String line : lines) {
                String[] split = line.split(" ");
                hands.add(new Hand(split[0], Long.parseLong(split[1]), part));
            }
            Collections.sort(hands);
            long result = 0;
            for (int i = 0; i < hands.size(); i++) {
                Hand hand = hands.get(i);
                result += (i+1) * hand.bid;
                // System.out.println(hand.hand + " " + hand.bid + " * " + (i+1));
            }
            printAnswer(part, result);
        }
    }
}

class Hand implements Comparable<Object> {

    String hand;
    long bid;
    int part;

    public Hand(String hand, long bid, int part) {
        this.hand = hand;
        this.bid = bid;
        this.part = part;
    }

    public int getType() {
        Map<Character, Integer> cardCount = new HashMap<>();
        for (char card : hand.toCharArray()) {
            cardCount.put(card, cardCount.getOrDefault(card, 0) + 1);
        }
        if (cardCount.size() == 1) {
            return 6;
        }
        boolean hasThreeOfAKind = false;
        for (int count : cardCount.values()) {
            if (count == 4) {
                return 5;
            } else if (count == 3) {
                hasThreeOfAKind = true;
            }
        }
        if (hasThreeOfAKind) {
            for (int count : cardCount.values()) {
                if (count == 2) {
                    return 4;
                }
            }
        }
        int pairCount = 0;
        for (int count : cardCount.values()) {
            if (count == 3) {
                return 3;
            } else if (count == 2) {
                pairCount++;
            }
        }
        return pairCount;
    }

    @Override
    public int compareTo(Object o) {
        Hand other = (Hand) o;
        int jokerValue = part == 1 ? 11 : 1;
        if (part == 1 ? getType() == other.getType() : getBestType() == other.getBestType()) {
            for (int i = 0; i < 5; i++) {
                if (this.hand.charAt(i) != other.hand.charAt(i)) {
                    return getCardValue(this.hand.charAt(i), jokerValue) - getCardValue(other.hand.charAt(i), jokerValue);
                }
            }
        }
        return part == 1 ? getType() - other.getType() : getBestType() - other.getBestType() ;
    }

    public int getCardValue(char c, int jokerValue) {
        switch (c) {
            case 'A': return 14;
            case 'K': return 13;
            case 'Q': return 12;
            case 'J': return jokerValue;
            case 'T': return 10;
            default:  return Character.getNumericValue(c);
        }
    }

    public int getBestType() {
        String candidateCards = "AKQT98765432";
        int bestType = getType();
        for (char candidateCard : candidateCards.toCharArray()) {
            Hand candidate = new Hand(this.hand.replaceAll("J", String.valueOf(candidateCard)), this.bid, this.part);
            int candidateType = candidate.getType();
            if (candidateType > bestType) {
                bestType = candidateType;
            }
        }
        return bestType;
    }
}