package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

import java.util.*;
import java.util.stream.Collectors;

public class Day04 extends Challenge {

    static int BOARD_SIZE = 5;

    public Day04() {
        super(false);

        List<Integer> numbers = Arrays.stream(lines.get(0).split(",")).mapToInt(s -> Integer.parseInt(s)).boxed().collect(Collectors.toList());
        List<Board> boards = new ArrayList<>();
        for (int i = 2; i < lines.size(); i += 6) {
            Board board = new Board();
            for (int y = 0; y < BOARD_SIZE; y++) {
                board.assign(y, lines.get(y + i).trim().replaceAll(" +", " "));
            }
            boards.add(board);
        }

        int firstWinnerScore = 0;
        int lastWinnerScore = 0;
        for (int i = 5; i < numbers.size(); i++) {
            List<Integer> currentSequence = numbers.subList(0, i);
            List<Board> discard = new ArrayList<>();
            for (Board board : boards) {
                if (board.wins(currentSequence)) {
                    if (firstWinnerScore == 0) {
                        firstWinnerScore = board.sumWithout(currentSequence) * currentSequence.get(i - 1);
                    }
                    lastWinnerScore = board.sumWithout(currentSequence) * currentSequence.get(i - 1);
                    discard.add(board);
                }
            }
            boards.removeAll(discard);
        }

        printAnswer(1, firstWinnerScore);
        printAnswer(2, lastWinnerScore);
    }

    static class Board {
        int[][] board = new int[BOARD_SIZE][BOARD_SIZE];

        public void assign(int y, String line) {
            String[] split = line.split(" ");
            for (int x = 0; x < split.length; x++) {
                board[y][x] = Integer.parseInt(split[x]);
            }
        }

        public boolean wins(List<Integer> sequence) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (sequence.containsAll(Arrays.stream(board[y]).boxed().collect(Collectors.toList()))) {
                    return true;
                }
            }
            for (int x = 0; x < BOARD_SIZE; x++) {
                Set<Integer> column = new HashSet<>();
                for (int y = 0; y < BOARD_SIZE; y++) {
                    column.add(board[y][x]);
                }
                if (sequence.containsAll(column)) {
                    return true;
                }
            }
            return false;
        }

        public int sumWithout(List<Integer> sequence) {
            int sum = 0;
            for (int x = 0; x < BOARD_SIZE; x++) {
                for (int y = 0; y < BOARD_SIZE; y++) {
                    if (!sequence.contains(board[x][y])) {
                        sum += board[x][y];
                    }
                }
            }
            return sum;
        }
    }
}
