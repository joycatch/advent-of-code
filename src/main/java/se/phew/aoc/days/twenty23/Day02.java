package se.phew.aoc.days.twenty23;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.List;

public class Day02 extends Challenge {

    public Day02() {
        super(false);

        int part1 = 0, part2 = 0;

        for (String line : lines) {
            line = line.replaceAll("Game ", "");
            String[] split = line.split(": ");

            Game game = new Game(Integer.parseInt(split[0]), split[1]));
            if (game.isPart1Possible()) {
                part1 += game.number;
            }
            part2 += game.power();
        }

        printAnswer(1, part1);
        printAnswer(2, part2);
    }
}

class Game {
    int number;
    List<Show> shows = new ArrayList<>();

    public Game(int number, String input) {
        this.number = number;
        for (String show : input.split("; ")) {
            shows.add(new Show(show));
        }
    }

    public boolean isPart1Possible() {
        for (Show show : shows) {
            if (show.red > 12 || show.green > 13 || show.blue > 14) {
                return false;
            }
        }
        return true;
    }

    public int power() {
        int red = 0, green = 0, blue = 0;
        for (Show show : shows) {
            red = show.red > red ? show.red : red;
            green = show.green > green ? show.green : green;
            blue = show.blue > blue ? show.blue : blue;
        }
        return red * green * blue;
    }
}

class Show {
    int red = 0, green = 0, blue = 0;

    public Show(String input) {
        for (String cube : input.split(", ")) {
            String[] cubeAttributes = cube.split(" ");
            switch (cubeAttributes[1]) {
                case "red":     this.red = Integer.parseInt(cubeAttributes[0]); break;
                case "green":   this.green = Integer.parseInt(cubeAttributes[0]); break;
                case "blue":    this.blue = Integer.parseInt(cubeAttributes[0]); break;
            }
        }
    }
}