package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

import java.util.*;
import java.util.stream.Collectors;

public class Day12 extends Challenge {

    HashMap<String, Cave> cavesMap = new HashMap<>();
    HashSet<String> uniquePaths = new HashSet<>();

    public Day12() {
        super();

        for (String line : lines) {
            String[] split = line.split("-");
            Cave c1 = addCave(split[0]);
            Cave c2 = addCave(split[1]);
            c1.adjacent.add(c2);
            c2.adjacent.add(c1);
        }

        Cave start = cavesMap.get("start");
        HashMap<String, Integer> allowed = new HashMap<>();
        for (Cave cave : cavesMap.values()) {
            allowed.put(cave.name, cave.big ? 10000 : 1);
        }
        allowed.put("start", 0);
        traverse(start, allowed, new Stack<>());

        printAnswer(1, uniquePaths.size());

        for (Cave cave : cavesMap.values()) {
            if (!cave.big && !cave.name.equals("start")) {
                allowed.put(cave.name, 2);
                traverse(start, allowed, new Stack<>());
                allowed.put(cave.name, 1);
            }
        }
        printAnswer(2, uniquePaths.size());
    }

    private void traverse(Cave current, HashMap<String, Integer> allowed, Stack<Cave> visited) {
        visited.push(current);
        allowed.put(current.name, allowed.get(current.name) - 1);
        if (!current.name.equals("end")) {
            for (Cave next : current.adjacent) {
                if (allowed.get(next.name) != null && allowed.get(next.name) > 0) {
                    traverse(next, allowed, visited);
                }
            }
        } else {
            uniquePaths.add(getPath(visited));
        }
        allowed.put(current.name, allowed.get(current.name) + 1);
        visited.pop();
    }

    private String getPath(List<Cave> visited) {
        return visited.stream().map(s -> s.name).collect(Collectors.joining(","));
    }

    private Cave addCave(String name) {
        if (cavesMap.get(name) == null) {
            cavesMap.put(name, new Cave(name));
        }
        return cavesMap.get(name);
    }

    static class Cave {
        String name;
        boolean big;
        List<Cave> adjacent = new ArrayList<>();

        public Cave(String name) {
            this.name = name;
            big = name.chars().anyMatch(i -> Character.isLetter(i) && Character.isUpperCase(i));
        }
    }
}
