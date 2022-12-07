package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Day15 extends Challenge {

    int[][] offsets = new int[][] { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
    int[][] map;
    int columns, rows;
    HashMap<String, Node> nodes;

    public Day15() {
        super(false);

        columns = lines.size();
        rows = lines.get(0).length();
        map = new int[columns][rows];

        for (int y = 0; y < columns; y++) {
            int x = 0;
            for (char c : lines.get(y).toCharArray()) {
                map[y][x++] = Integer.parseInt(String.valueOf(c));
            }
        }

        Graph graph = createAndPopulateGraph();
        graph = calculateShortestPathFromSource(graph, nodes.get(getNodeName(0, 0)));
        int part1 = graph.nodes.stream().filter(n -> n.y == rows - 1 && n.x == columns - 1).findFirst().get().distance;
        printAnswer(1, part1);

        int[][] newMap = new int[columns*5][rows*5];

        for (int iy = 0; iy < 5; iy++) {
            for (int ix = 0; ix < 5; ix++) {
                for (int y = 0; y < columns; y++) {
                    for (int x = 0; x < rows; x++) {
                        int newRisk = map[y][x] + iy + ix;
                        newMap[iy*rows + y][ix*columns + x] = newRisk > 9 ? (newRisk % 9) : newRisk;
                    }
                }
            }
        }

        map = newMap;
        columns *= 5;
        rows *= 5;

        graph = createAndPopulateGraph();
        graph = calculateShortestPathFromSource(graph, nodes.get(getNodeName(0, 0)));
        int part2 = graph.nodes.stream().filter(n -> n.y == rows - 1 && n.x == columns - 1).findFirst().get().distance;
        printAnswer(2, part2);
    }

    private Graph createAndPopulateGraph() {
        nodes = new HashMap<>();
        Graph graph = new Graph();
        for (int y = 0; y < columns; y++) {
            for (int x = 0; x < rows; x++) {
                nodes.put(getNodeName(y, x), new Node(y, x));
            }
        }
        for (Node node : nodes.values()) {
            setDistances(node);
            graph.addNode(node);
        }
        return graph;
    }

    private String getNodeName(int y, int x) {
        return y + "," + x;
    }

    private void setDistances(Node node) {
        for (int[] offset : offsets) {
            int ix = node.x + offset[0];
            int iy = node.y + offset[1];
            if (ix < 0 || ix >= rows || iy < 0 || iy >= columns) {
                continue;
            }
            node.addDestination(nodes.get(getNodeName(iy, ix)), map[iy][ix]);
        }
    }

    public class Graph {
        private Set<Node> nodes = new HashSet<>();

        public void addNode(Node nodeA) {
            nodes.add(nodeA);
        }
    }

    public class Node {
        int y, x;
        private List<Node> shortestPath = new LinkedList<>();
        private Integer distance = Integer.MAX_VALUE;
        Map<Node, Integer> adjacentNodes = new HashMap<>();

        public void addDestination(Node destination, int distance) {
            adjacentNodes.put(destination, distance);
        }

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getDistance() {
            return distance;
        }

        public List<Node> getShortestPath() {
            return shortestPath;
        }

        public void setShortestPath(LinkedList<Node> shortestPath) {
            this.shortestPath = shortestPath;
        }

        public Map<Node, Integer> getAdjacentNodes() {
            return adjacentNodes;
        }
    }

    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {
        source.setDistance(0);
        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);
        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);
            for (Map.Entry<Node, Integer> adjacencyPair : currentNode.getAdjacentNodes().entrySet()) {
                Node adjacentNode = adjacencyPair.getKey();
                Integer edgeWeight = adjacencyPair.getValue();
                if (!settledNodes.contains(adjacentNode)) {
                    calculateMinimumDistance(adjacentNode, edgeWeight, currentNode);
                    unsettledNodes.add(adjacentNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static Node getLowestDistanceNode(Set < Node > unsettledNodes) {
        Node lowestDistanceNode = null;
        int lowestDistance = Integer.MAX_VALUE;
        for (Node node: unsettledNodes) {
            int nodeDistance = node.getDistance();
            if (nodeDistance < lowestDistance) {
                lowestDistance = nodeDistance;
                lowestDistanceNode = node;
            }
        }
        return lowestDistanceNode;
    }

    private static void calculateMinimumDistance(Node evaluationNode, Integer edgeWeigh, Node sourceNode) {
        Integer sourceDistance = sourceNode.getDistance();
        if (sourceDistance + edgeWeigh < evaluationNode.getDistance()) {
            evaluationNode.setDistance(sourceDistance + edgeWeigh);
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }
}
