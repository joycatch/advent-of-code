package se.phew.aoc.days.twenty20;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;

public class Day23 extends Challenge {

    ArrayList<Integer> list;
    int listSize;

    public Day23() {
        super(false);

        // Part 1
        populateList(false);

        int[] linkedList = playGame(100);

        String answer = "";
        int next = linkedList[1], i = 0;
        do {
            answer += "" + next;
            next = linkedList[next];
        }  while (next != 1);

        printAnswer(1, answer);

        // Part 2
        populateList(true);

        linkedList = playGame(10000000);

        next = linkedList[1];

        printAnswer(2, (long) next * linkedList[next]);
    }

    private int[] playGame(int moves) {

        int[] linkedList = new int[list.size() + 1];
        for (int i = 0; i < list.size() - 1; i++) {
            linkedList[list.get(i)] = list.get(i + 1);
        }
        linkedList[list.get(list.size() - 1)] = list.get(0);

        int[] pickedUp = new int[3];
        int current = list.get(0);

        for (int move = 1; move <= moves; move++) {

            pickedUp[0] = linkedList[current];
            pickedUp[1] = linkedList[pickedUp[0]];
            pickedUp[2] = linkedList[pickedUp[1]];

            int destination = current;
            do {
                if (--destination < 1) {
                    destination = listSize - 1;
                }
            } while (destination == pickedUp[0] || destination == pickedUp[1] || destination == pickedUp[2]);

            linkedList[current] = linkedList[pickedUp[2]];
            int temp = linkedList[destination];
            linkedList[destination] = pickedUp[0];
            linkedList[pickedUp[2]] = temp;

            current = linkedList[current];
        }

        return linkedList;
    }

    private String populateList(boolean part2) {
        list = new ArrayList<>();
        String input = "156794823";
        if (isTest) {
            input = "389125467";
        }
        for (char number : input.toCharArray()) {
            list.add(Integer.parseInt("" + number));
        }
        if (part2) {
            for (int i = 10; i <= 1000000; i++) {
                list.add(Integer.parseInt("" + i));
            }
        }
        listSize = list.size() + 1;
        return input;
    }
}
