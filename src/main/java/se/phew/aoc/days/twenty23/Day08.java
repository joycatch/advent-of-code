package se.phew.aoc.days.twenty23;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day08 extends Challenge {

    HashMap<String, Node> nodes = new HashMap<>();
    List<Node> endNodes = new ArrayList<>();

    public Day08() {
        super(false);

        String instructions = lines.get(0);

        lines.remove(0);
        lines.remove(0);

        for (String line : lines) {
            String[] split = line.split(" = \\(");
            Node node = getNode(split[0]);
            split = split[1].replaceAll("\\)", "").split(", ");
            node.left = getNode(split[0]);
            node.right = getNode(split[1]);
        }

        Node current = nodes.get("AAA");
        Node goal = nodes.get("ZZZ");
        int steps = 0;
        while (current != goal) {
            for (char instruction : instructions.toCharArray()) {
                steps++;
                current = instruction == 'R' ? current.right : current.left;
                if (current == goal) {
                    break;
                }
            }
        }

        printAnswer(1, steps);

        for (Node node : nodes.values()) {
            if (node.isEndNode()) {
                endNodes.add(node);
            }
        }

        long[] distancesToEndNodes = new long[endNodes.size()];
        int i = 0;
        for (Node node : nodes.values()) {
            if (node.isStartNode()) {
                current = node;
                steps = 0;
                while (!current.isEndNode()) {
                    for (char instruction : instructions.toCharArray()) {
                        steps++;
                        current = instruction == 'R' ? current.right : current.left;
                        if (current.isEndNode()) {
                            break;
                        }
                    }
                }
                distancesToEndNodes[i++] = steps;
            }
        }

        printAnswer(2, lcm(distancesToEndNodes));
    }

    private Node getNode(String name) {
        Node node = nodes.getOrDefault(name, new Node(name));
        nodes.putIfAbsent(name, node);
        return node;
    }

    private static long gcd(long a, long b) {
        while (b > 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private static long lcm(long a, long b) {
        return a * (b / gcd(a, b));
    }

    private static long lcm(long[] input) {
        long result = input[0];
        for (int i = 1; i < input.length; i++) result = lcm(result, input[i]);
        return result;
    }
}

class Node {

    Node left, right;
    String name;

    public Node(String name) {
        this.name = name;
    }

    public boolean isStartNode() {
        return name.endsWith("A");
    }

    public boolean isEndNode() {
        return name.endsWith("Z");
    }
}