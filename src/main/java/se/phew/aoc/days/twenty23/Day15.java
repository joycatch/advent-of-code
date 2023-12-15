package se.phew.aoc.days.twenty23;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Day15 extends Challenge {

    public Day15() {
        super(false);

        printAnswer(1, Arrays.stream(lines.get(0).split(",")).mapToInt(s -> getHash(s)).sum());

        ArrayList<Box> boxes = new ArrayList<>();
        for (int i = 0; i < 256; i++) {
            boxes.add(new Box(i));
        }

        for (String instruction : lines.get(0).split(",")) {
            if (instruction.endsWith("-")) {
                String label = instruction.replaceAll("-", "");
                boxes.get(getHash(label)).removeLens(label);
            } else {
                String[] split = instruction.split("=");
                boxes.get(getHash(split[0])).addLens(split[0], Integer.parseInt(split[1]));
            }
        }

        int result = 0;
        for (Box box : boxes) {
            result += box.getFocusingPower();
        }

        printAnswer(2, result);
    }

    int getHash(String input) {
        int currentValue = 0;
        for (char c : input.toCharArray()) {
            currentValue = ((currentValue + (int) c) * 17 % 256);
        }
        return currentValue;
    }
}

class Box {

    int number = 0;
    ArrayList<Lens> lenses = new ArrayList<>();

    public Box(int number) {
        this.number = number + 1;
    }

    void addLens(String label, int focal) {
        for (Lens lens : lenses) {
            if (lens.label.equals(label)) {
                lens.value = focal;
                return;
            }
        }
        lenses.add(new Lens(label, focal));
    }

    public int getFocusingPower() {
        int result = 0;
        int i = 1;
        for (Lens lens : lenses) {
            result += number * (i++) * lens.value;
        }
        return result;
    }

    public void removeLens(String label) {
        lenses.remove(new Lens(label));
    }
}

class Lens {
    String label;
    int value;

    public Lens(String label) {
        this.label = label;
    }

    public Lens(String label, int value) {
        this.label = label;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        Lens lens = (Lens) o;
        return Objects.equals(label, lens.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}