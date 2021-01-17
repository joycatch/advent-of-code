package se.phew.aoc.days.twenty20;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.HashMap;

public class Day14 extends Challenge {

    public Day14() {
        super();

        HashMap<Long, Long> memoryPart1 = new HashMap<>();
        HashMap<Long, Long> memoryPart2 = new HashMap<>();

        String currentMask = "";
        for (String originalLine : lines) {
            String line = originalLine;
            if (line.contains("mask = ")) {
                currentMask = line.substring("mask = ".length());
            } else {
                line = line.replaceAll("mem\\[", "");
                String[] split = line.split("] = ");
                long position = Long.parseLong(split[0]);
                long value = Long.parseLong(split[1]);
                String binaryValue = String.format("%36s", Integer.toBinaryString((int) value)).replace(' ', '0');
                String newBinaryValue = applyMaskPart1(currentMask, binaryValue);
                memoryPart1.put(position, getValue(newBinaryValue));

                String binaryPosition = String.format("%36s", Integer.toBinaryString((int) position)).replace(' ', '0');
                ArrayList<String> newBinaryPositions = applyMaskPart2(currentMask, binaryPosition);
                for (String newPosition : newBinaryPositions) {
                    memoryPart2.put(getValue(newPosition), value);
                }
            }
        }

        long result = 0;
        for (Long value : memoryPart1.values()) {
            result += value;
        }
        printAnswer(1, result);

        result = 0;
        for (Long value : memoryPart2.values()) {
            result += value;
        }
        printAnswer(2, result);
    }

    private ArrayList<String> applyMaskPart2(String currentMask, String binary) {
        String mask = "";
        for (int i = 0; i < currentMask.length(); i++) {
            switch(currentMask.charAt(i)) {
                case '0': mask += binary.charAt(i); break;
                case '1': mask += '1'; break;
                case 'X': mask += 'X'; break;
            }
        }
        // System.out.println(mask);
        ArrayList<String> result = new ArrayList<>();

        generateMasks(mask, result);

        return result;
    }

    private void generateMasks(String binary, ArrayList<String> result) {
        if (!binary.contains("X")) {
            result.add(binary);
        } else {
            generateMasks(binary.replaceFirst("X", "0"), result);
            generateMasks(binary.replaceFirst("X", "1"), result);
        }
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

    private String applyMaskPart1(String currentMask, String binary) {
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
