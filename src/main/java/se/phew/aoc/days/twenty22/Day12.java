package se.phew.aoc.days.twenty22;

import se.phew.aoc.days.Challenge;

import java.util.*;

public class Day12 extends Challenge {

    int[][] offsets = new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
    int[][] map;
    int rows, columns, startY, startX, goalY, goalX;
    HashMap<String, Node> nodes;

    public Day12() {
        super(false);

        rows = lines.size();
        columns = lines.get(0).length();
        map = new int[rows][columns];
        nodes = new HashMap<>();

        for (int y = 0; y < rows; y++) {
            int x = 0;
            for (char c : lines.get(y).toCharArray()) {
                int value = (int) c - (int) 'a';
                if (value == -14) {
                    startY = y;
                    startX = x;
                    value = -1;
                }
                if (value == -28) {
                    goalY = y;
                    goalX = x;
                    value = 1 + (int) 'z' - (int) 'a';
                }
                nodes.put(getNodeName(y, x), new Node(y, x, value));
                x++;
            }
        }

        for (Node node : nodes.values()) {
            setNeighbours(node);
        }
        Node start = nodes.get(getNodeName(startY, startX));
        Node goal = nodes.get(getNodeName(goalY, goalX));

        printAnswer(1, shortestDistanceFrom(start, goal));

        int shortest = Integer.MAX_VALUE;
        for (Node node : nodes.values()) {
            if (node.value == 0) {
                int distance = shortestDistanceFrom(node, goal);
                if (distance > 0 && distance < shortest) {
                    shortest = distance;
                }
            }
        }

        printAnswer(2, shortest);
    }

    private int shortestDistanceFrom(Node start, Node goal) {
        nodes.values().stream().forEach(n -> n.distance = 0);

        Set<Node> visited = new HashSet<>();
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            if (current == goal) {
                return current.distance;
            }

            for (Node node : current.neighbours) {
                node.distance = current.distance + 1;
                if (!visited.contains(node)) {
                    visited.add(node);
                    queue.add(node);
                }
            }
        }
        return -1;
    }

    private String getNodeName(int y, int x) {
        return y + "," + x;
    }

    private void setNeighbours(Node node) {
        for (int[] offset : offsets) {
            int ix = node.x + offset[0];
            int iy = node.y + offset[1];
            if (ix < 0 || ix >= columns || iy < 0 || iy >= rows) {
                continue;
            }
            Node other = nodes.get(getNodeName(iy, ix));
            if (other.value <= node.value + 1) {
                node.neighbours.add(other);
            }
        }
    }

    class Node {
        int y, x, value;
        private Set<Node> neighbours = new HashSet<>();
        private Integer distance = 0;

        public Node(int y, int x, int value) {
            this.y = y;
            this.x = x;
            this.value = value;
        }
    }
}
