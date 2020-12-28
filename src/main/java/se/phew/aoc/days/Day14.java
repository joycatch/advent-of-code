package se.phew.aoc.days;

import java.util.HashMap;

public class Day14 extends Challenge {

    public Day14() {
        super();

        HashMap<Integer, Long> memory = new HashMap<>();

        String currentMask = "";
        for (String line : lines) {
            if (line.contains("mask = ")) {
                currentMask = line.substring("mask = ".length());
                System.out.println(currentMask);
            } else {
                line = line.replaceAll("mem\\[", "");
                String[] split = line.split("] = ");
                int position = Integer.parseInt(split[0]);
                int value = Integer.parseInt(split[1]);
                String binary = String.format("%36s", Integer.toBinaryString(value)).replace(' ', '0');
                System.out.println(binary + " <- Original");
                String newBinary = applyMask(currentMask, binary);
                System.out.println(newBinary + " <- New");
                memory.put(position, getValue(newBinary));
            }
        }

        long result = 0;
        for (Long value : memory.values()) {
            result += value;
        }
        printAnswer(1, result);
    }

    private long getValue(String binary) {
        long result = 0;
        for (int i = 0; i < 36; i++) {
            if (binary.charAt(i) == '1') {
                result += (long) Math.pow(2, 35 - i);
            }
        }
        return result;
    }

    private String applyMask(String currentMask, String binary) {
        String result = "";
        for (int i = 0; i < currentMask.length(); i++) {
            switch(currentMask.charAt(i)) {
                case '1': result += '1'; break;
                case '0': result += '0'; break;
                case 'X': result += binary.charAt(i); break;
            }
        }
        return result;
    }
}
