package se.phew.aoc.days.twenty15;

import se.phew.aoc.days.Challenge;

import java.util.HashMap;

public class Day03 extends Challenge {

    HashMap<String, Integer> map = new HashMap<>();
    int x = 0, y = 0, rx = 0, ry = 0, turn = 0;

    public Day03() {
        super(false);

        // Part 1
        map.put("0,0", 1);
        for (char c : lines.get(0).toCharArray()) {
            move(c, true);
        }
        printAnswer(1, map.keySet().size());

        // Part 2
        x = 0;
        y = 0;
        map = new HashMap<>();
        map.put("0,0", 2);
        for (char c : lines.get(0).toCharArray()) {
            move(c, turn++ % 2 == 0);
        }
        printAnswer(2, map.keySet().size());
    }

    private void move(char c, boolean santa) {
        int x = (santa ? this.x : rx);
        int y = (santa ? this.y : ry);

        switch (c) {
            case '^': y++; break;
            case 'v': y--; break;
            case '>': x++; break;
            case '<': x--; break;
        }
        String key = x + "," + y;
        if (map.get(key) == null) {
            map.put(key, 1);
        } else {
            map.put(key, map.get(key) + 1);
        }
        if (santa) {
            this.x = x;
            this.y = y;
        } else {
            this.rx = x;
            this.ry = y;
        }
    }
}
