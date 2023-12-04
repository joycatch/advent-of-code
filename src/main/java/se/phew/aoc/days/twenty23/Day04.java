package se.phew.aoc.days.twenty23;

import se.phew.aoc.days.Challenge;

import java.util.*;
import java.util.stream.Collectors;

public class Day04 extends Challenge {

    public Day04() {
        super(false);
        TreeMap<Integer, List<Card>> map = new TreeMap<>();
        List<Card> cards = new ArrayList<>();
        for (String line : lines) {
            line = line.replaceAll("Card ", "");
            String[] split = line.split(": ");
            int cardNumber = Integer.parseInt(split[0].trim());
            Card card = new Card(cardNumber, split[1].trim());
            cards.add(card);
            map.putIfAbsent(cardNumber, new ArrayList<>());
            map.get(cardNumber).add(card);
        }

        printAnswer(1, cards.stream().mapToInt(Card::points).sum());

        for (int cardNumber : map.keySet()) {
            for (int j = 0; j < map.get(cardNumber).size(); j++) {
                for (int i = cardNumber + 1; i <= cardNumber + map.get(cardNumber).get(0).correctNumbers(); i++) {
                    Card cardToCopy = map.get(i).get(0);
                    map.get(i).add(cardToCopy);
                }
            }
        }

        printAnswer(2, map.keySet().stream().mapToInt(n -> map.get(n).size()).sum());
    }
}

class Card {
    int number;
    List<Integer> winningNumbers = new ArrayList<>();
    List<Integer> numbers = new ArrayList<>();

    public Card(int number, String input) {
        this.number = number;
        input = input.replaceAll("  ", " ");
        String[] split = input.split(" \\| ");
        for (String value : split[0].split(" ")) {
            winningNumbers.add(Integer.parseInt(value.trim()));
        }
        for (String value : split[1].split(" ")) {
            numbers.add(Integer.parseInt(value.trim()));
        }
    }

    public int points() {
        return (int) Math.pow(2, correctNumbers() - 1);
    }

    public int correctNumbers() {
        return numbers.stream().filter(n -> winningNumbers.contains(n)).collect(Collectors.toList()).size();
    }
}