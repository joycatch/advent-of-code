package se.phew.aoc.days;

import java.util.LinkedList;
import java.util.Queue;

public class Day23 extends Challenge {

    public Day23() {
        super();

        String input = "156794823";
        if (isTest) {
            input = "389125467";
        }

        Queue<Integer> list = new LinkedList<>();
        for (char number : input.toCharArray()) {
            list.add(Integer.parseInt("" + number));
        }

        int[] pickedUp = new int[3];

        for (int move = 1; move <= 100; move++) {
            System.out.println("\n-- move " + move + " --");
            System.out.print("cups: ");
            for (int i = 0; i < input.length(); i++) {
                int n = list.poll();
                System.out.print((i == 0 ? "(" + n + ")" : n ) + " ");
                list.add(n);
            }
            System.out.print("\npick up: ");
            int number = list.poll();
            for (int j = 0; j < 3; j++) {
                pickedUp[j] = list.poll();
                System.out.print(pickedUp[j] + ", ");
            }
            list.add(number);
            list = findDestination(list, number);
            for (int j = 0; j < 3; j++) {
                list.add(pickedUp[j]);
            }
            rotateTo(list, number);
        }

        rotateTo(list, 1);
        list.remove(1);
        String answer = "";
        for (int i : list) {
            answer += "" + i;
        }

        print("");
        printAnswer(1, answer);
    }

    private Queue<Integer> rotateTo(Queue<Integer> list, int number) {
        int lastNumber;
        do {
            lastNumber = list.poll();
            list.add(lastNumber);
        } while (lastNumber != number);
        return list;
    }

    private Queue<Integer> findDestination(Queue<Integer> list, int needle) {
        int min = list.stream().reduce(Integer::min).get();
        do {
            if (--needle < min) {
                needle = list.stream().reduce(Integer::max).get();
            }
        } while (!list.contains(needle));
        System.out.println("\ndestination: " + needle);
        return rotateTo(list, needle);
    }
}
