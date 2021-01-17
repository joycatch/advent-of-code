package se.phew.aoc.days.twenty20;

import se.phew.aoc.days.Challenge;

import java.util.HashMap;

public class Day06 extends Challenge {

    public Day06() {
        super();

        GroupVotes groupVotes = new GroupVotes();
        int part1 = 0;
        int part2 = 0;

        for (String line : lines) {
            if (!line.isEmpty()) {
                groupVotes.contributors++;
                for (char ch : line.toCharArray()) {
                    groupVotes.add(ch);
                }
            } else {
                part1 += groupVotes.getAnybodyAnsweredYesCount();
                part2 += groupVotes.getEverybodyAnsweredYesCount();
                groupVotes = new GroupVotes();
            }
        }
        part1 += groupVotes.getAnybodyAnsweredYesCount();
        part2 += groupVotes.getEverybodyAnsweredYesCount();

        printAnswer(1, part1);
        printAnswer(2, part2);
    }


    static class GroupVotes {
        HashMap<Character, Integer> map = new HashMap<>();
        int contributors = 0;

        void add(char key) {
            if (map.get(key) == null) {
                map.put(key, 1);
            } else {
                map.put(key, map.get(key) + 1);
            }
        }

        int getAnybodyAnsweredYesCount() {
            return map.keySet().size();
        }

        int getEverybodyAnsweredYesCount() {
            int result = 0;
            for (Character c : map.keySet()) {
                if (map.get(c) == contributors) {
                    result++;
                }
            }
            return result;
        }

        public String toString() {
            return map.toString();
        }
    }
}
