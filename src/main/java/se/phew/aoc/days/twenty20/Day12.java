package se.phew.aoc.days.twenty20;

import se.phew.aoc.days.Challenge;

public class Day12 extends Challenge {

    public Day12() {
        super(false);

        Ship ship = followInstructions(false);

        printAnswer(1, ship.position());

        ship = followInstructions(true);

        printAnswer(2, ship.position());

    }

    private Ship followInstructions(boolean waypoint) {
        Ship ship = new Ship(10, 1);

        for (String line : lines) {
            char instruction = line.charAt(0);
            int value = Integer.parseInt(line.substring(1));
            if (instruction == 'F') {
                if (!waypoint) {
                    ship.forward(value);
                } else {
                    ship.forwardTowardsWaypoint(value);
                }
            } else if (instruction == 'L' || instruction == 'R') {
                if (!waypoint) {
                    ship.turn(instruction, value);
                } else {
                    ship.rotateWaypoint(instruction, value);
                }
            } else {
                if (!waypoint) {
                    ship.move(instruction, value);
                } else {
                    ship.moveTowardsWaypoint(instruction, value);
                }
            }
        }
        return ship;
    }

    static class Ship {

        int x = 0;
        int y = 0;
        int direction = 0;

        int wx = 0;
        int wy = 0;

        public Ship(int direction) {
            this.direction = direction;
        }

        public Ship(int x, int y) {
            this.wx = x;
            this.wy = y;
        }

        public void turn(char c, int value) {
            if (c == 'R') {
                direction = (direction + value) % 360;
            } else {
                direction = direction - value;
                while (direction < 0) {
                    direction += 360;
                }
            }
        }

        public void forward(int value) {
            switch (direction) {
                case 0:
                    move('E', value);
                    break;
                case 90:
                    move('S', value);
                    break;
                case 180:
                    move('W', value);
                    break;
                case 270:
                    move('N', value);
                    break;
            }
        }

        public void move(char c, int steps) {
            switch (c) {
                case 'N':
                    y += steps;
                    break;
                case 'S':
                    y -= steps;
                    break;
                case 'E':
                    x += steps;
                    break;
                case 'W':
                    x -= steps;
                    break;
            }
        }


        public void rotateWaypoint(char c, int value) {
            int rotated = 0;
            if (c == 'R') {
                rotated = value % 360;
            } else {
                rotated = -value;
                while (rotated < 0) {
                    rotated += 360;
                }
            }

            int temp;
            switch (rotated) {
                case 90:
                    temp = wy;
                    wy = -wx;
                    wx = temp;
                    break;
                case 180:
                    wx = -wx;
                    wy = -wy;
                    break;
                case 270:
                    temp = wx;
                    wx = -wy;
                    wy = temp;
                    break;
                case 0:
                    break;
                default:
                    System.out.println("!!! Invalid rotation: " + rotated);
                    break;
            }
        }

        public void forwardTowardsWaypoint(int value) {
            this.x = this.x + (value * wx);
            this.y = this.y + (value * wy);
        }

        public void moveTowardsWaypoint(char c, int steps) {
            switch (c) {
                case 'N':
                    wy += steps;
                    break;
                case 'S':
                    wy -= steps;
                    break;
                case 'E':
                    wx += steps;
                    break;
                case 'W':
                    wx -= steps;
                    break;
            }
        }

        public int position() {
            return Math.abs(x) + Math.abs(y);
        }
    }
}