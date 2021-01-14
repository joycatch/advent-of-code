package se.phew.aoc.days;

import java.util.ArrayList;

public class Day16 extends Challenge {

    ArrayList<Field> fields = new ArrayList<>();

    public Day16() {
        super();

        double result = 0;
        ArrayList<Ticket> validTickets = new ArrayList<>();
        for (String line : lines) {
            if (line.contains(": ")) {
                String[] split = line.split(": ");
                Field field = new Field(split[0], split[1].split(" or "));
                fields.add(field);
            }

            if (line.contains(",")) {
                Ticket ticket = new Ticket(line);
                for (int value : ticket.values) {
                    int countInvalids = 0;
                    for (Field field : fields) {
                        if (!field.valid(value)) {
                            countInvalids++;
                        }
                    }
                    if (countInvalids == fields.size()) {
                        result += value;
                        ticket = null;
                        break;
                    }
                }
                if (ticket != null) {
                    validTickets.add(ticket);
                }
            }
        }

        printAnswer(1, (int) result);

        ArrayList<ArrayList<Integer>> columns = new ArrayList<>();

        for (int i = 0; i < validTickets.get(0).values.size(); i++) {
            columns.add(new ArrayList<>());
        }

        for (Ticket ticket : validTickets) {
            for (int i = 0; i < ticket.values.size(); i++) {
                columns.get(i).add(ticket.values.get(i));
            }
        }

        for (Field field : fields) {
            // System.out.println("\n\n" + field.name + " could be column: ");

            for (int i = 0; i < columns.size(); i++) {
                boolean candidate = true;
                for (int value : columns.get(i)) {
                    if (!field.valid(value)) {
                        candidate = false;
                    }
                }
                if (candidate) {
                    field.candidate(i);
                    // System.out.print(i + ", ");
                }
            }
        }

        boolean allFieldsHaveBeenAssigned = false;
        do {
            for (Field field : fields) {
                if (field.candidateColumn.size() == 1) {
                    int toBeRemoved = field.columnDetermined();
                    for (Field other : fields) {
                        other.candidateColumn.remove((Object) toBeRemoved);
                    }
                }
            }

            allFieldsHaveBeenAssigned = true;
            for (Field field : fields) {
                if (field.candidateColumn.size() > 0) {
                    allFieldsHaveBeenAssigned = false;
                }
            }

        } while (!allFieldsHaveBeenAssigned);

        // System.out.println("\n\n");

        result = 1;
        for (Field field : fields) {
            if (field.name.startsWith("departure")) {
                result *= validTickets.get(0).values.get(field.column);
                // System.out.println("Field " + field.name + " has column: " + field.column + " and value " + validTickets.get(0).values.get(field.column));
            }
        }

        printAnswer(2, String.format("%.0f", result));
    }

    static class Field {

        private String name;
        private int min1, min2, max1, max2;
        ArrayList<Integer> candidateColumn = new ArrayList<>();
        private int column;

        public Field(String name, String[] limits) {
            this.name = name;
            String[] split = limits[0].split("-");
            String[] split1 = limits[1].split("-");
            this.min1 = Integer.parseInt(split[0]);
            this.min2 = Integer.parseInt(split1[0]);
            this.max1 = Integer.parseInt(split[1]);
            this.max2 = Integer.parseInt(split1[1]);
        }

        public boolean valid(int value) {
            return (value >= min1 && value <= max1) || (value >= min2 && value <= max2);
        }

        public void candidate(int i) {
            candidateColumn.add(i);
        }

        public int columnDetermined() {
            column = candidateColumn.iterator().next();
            candidateColumn = new ArrayList<>();
            return column;
        }
    }

    static class Ticket {

        ArrayList<Integer> values = new ArrayList<>();

        public Ticket(String input) {
            for (String split : input.split(",")) {
                values.add(Integer.parseInt(split));
            }
        }
    }
}