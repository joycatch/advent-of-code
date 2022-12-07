package se.phew.aoc.days.twenty16;

import se.phew.aoc.days.Challenge;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day01 extends Challenge {

    private char direction = 'N';
    private int x = 0, y = 0;
    private HashSet<String> visited = new HashSet<>();
    private int part2 = -1;

    public Day01() {
        super(false);

        Matcher m = Pattern.compile("((L|R)(\\d+))+").matcher(lines.get(0));
        while (m.find()) {
            rotate(m.group(2).charAt(0));
            walk(Integer.parseInt(m.group(3)));
        }

        printAnswer(1, Math.abs(x) + Math.abs(y));
        printAnswer(2, part2);
    }

    public void rotate(char c) {
        switch (direction) {
            case 'N': direction = c == 'R' ? 'E' : 'W'; break;
            case 'E': direction =  c == 'R' ? 'S' : 'N'; break;
            case 'S': direction = c == 'R' ? 'W' : 'E'; break;
            case 'W': direction = c == 'R' ? 'N' : 'S'; break;
        }
    }

    public void walk(int steps) {
        for (int i = 0; i < steps; i++) {
            switch (direction) {
                case 'N': y += 1; break;
                case 'S': y -= 1; break;
                case 'E': x += 1; break;
                case 'W': x -= 1; break;
            }
            storeVisits();
        }
    }

    private void storeVisits() {
        String currentLocation = x + "," + y;
        if (part2 == -1 && visited.contains(currentLocation)) {
            part2 = Math.abs(x) + Math.abs(y);
        } else {
            visited.add(currentLocation);
        }
    }
}
