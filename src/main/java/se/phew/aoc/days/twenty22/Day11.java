package se.phew.aoc.days.twenty22;

import org.apache.commons.lang3.StringUtils;
import se.phew.aoc.days.Challenge;

import java.util.*;
import java.util.stream.Collectors;

public class Day11 extends Challenge {

    List<Monkey> monkeys;

    public Day11() {
        super(false);

        populateMonkeys();

        for (int round = 0; round < 20; round++) {
            for (Monkey monkey : monkeys) {
                monkey.examineAndThrow(0);
            }
        }
        printAnswer(1, calculateMonkeyBusiness());

        populateMonkeys();

        long gcd = monkeys.stream().mapToLong(m -> m.divisibleBy).reduce((a,b) -> a*b).getAsLong();
        for (int round = 0; round < 10000; round++) {
            for (Monkey m : monkeys) {
                m.examineAndThrow(gcd);
            }
        }
        printAnswer(2, calculateMonkeyBusiness());
    }

    private long calculateMonkeyBusiness() {
        List<Long> inspections = monkeys.stream().map(m -> m.inspections).collect(Collectors.toList());
        Collections.sort(inspections, Collections.reverseOrder());
        return inspections.get(0) * inspections.get(1);
    }

    private void populateMonkeys() {
        monkeys = new ArrayList<>();
        Monkey monkey = null;

        int id = 0;
        for (String line : lines) {
            line = line.trim();
            if (line.contains("Monkey")) {
                monkey = new Monkey(id++);
            }
            if (line.startsWith("Starting items: ")) {
                line = line.replace("Starting items: ", "");
                monkey.items = Arrays.stream(line.split(", ")).map(Long::parseLong).collect(Collectors.toList());
            }
            if (line.startsWith("Operation: new = ")) {
                line = line.replace("Operation: new = ", "");
                monkey.operation = line.contains("*") ? Operation.MUL : Operation.ADD;
                monkey.value = line.endsWith("old") ? -1L : Long.parseLong(line.split(" ")[2]);
            }
            if (line.startsWith("Test: divisible by ")) {
                line = line.replace("Test: divisible by ","");
                monkey.divisibleBy = Integer.parseInt(line);
            }
            if (line.startsWith("If true: throw to monkey ")) {
                line = line.replace("If true: throw to monkey ","");
                monkey.throwToIfTrue = Integer.parseInt(line);
            }
            if (line.startsWith("If false: throw to monkey ")) {
                line = line.replace("If false: throw to monkey ","");
                monkey.throwToIfFalse = Integer.parseInt(line);
            }
            if (StringUtils.isBlank(line)) {
                monkeys.add(monkey);
            }
        }
        monkeys.add(monkey);
    }

    enum Operation {
        ADD, MUL
    }

    class Monkey {
        int id;
        List<Long> items = new ArrayList<>();
        Operation operation;
        long value = -1;
        public int divisibleBy;
        int throwToIfTrue;
        int throwToIfFalse;
        long inspections = 0;

        public Monkey(int id) {
            this.id = id;
        }

        public void examineAndThrow(long gcd) {
            for (long item : items) {
                inspections++;
                long worryLevel = item;
                if (operation == Operation.MUL) {
                    worryLevel *= value == -1 ? worryLevel : value;
                } else {
                    worryLevel += value;
                }
                worryLevel = gcd == 0 ? worryLevel / 3 : worryLevel % gcd;
                for (Monkey monkey : monkeys) {
                    if (worryLevel % divisibleBy == 0 && monkey.id == this.throwToIfTrue) {
                        monkey.items.add(worryLevel);
                    }
                    if (worryLevel % divisibleBy != 0 && monkey.id == this.throwToIfFalse) {
                        monkey.items.add(worryLevel);
                    }
                }
            }
            items = new ArrayList<>();
        }
    }
}
