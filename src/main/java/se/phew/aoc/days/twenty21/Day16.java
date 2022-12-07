package se.phew.aoc.days.twenty21;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day16 extends Challenge {

    static HashMap<Character, String> decode = new HashMap<>();
    static {
        for (int i = 0; i < 10; i++) {
            String binary = Integer.toBinaryString(i);
            decode.put((char) ('0' + i), ("0000" + binary).substring(binary.length()));
        }
        decode.put('A', "1010");
        decode.put('B', "1011");
        decode.put('C', "1100");
        decode.put('D', "1101");
        decode.put('E', "1110");
        decode.put('F', "1111");
    }
    static String binary = "";
    static int index = 0;

    public Day16()   {
        super(false);

        for (Character c : lines.get(0).toCharArray()) {
            binary += decode.get(c);
        }

        Packet packet = new Packet();

        printAnswer(1, packet.getVersion());
        printAnswer(2, packet.getValue());
    }

    public static int index(int count) {
        index += count;
        return index;
    }

    public static int read(int count) {
        return Integer.parseInt(binary.substring(index, index(count)), 2);
    }

    static class Packet {
        int version, typeID;
        long value;
        List<Packet> packets = new ArrayList<>();

        public Packet() {
            version = read(3);
            typeID = read(3);

            if (typeID == 4) {
                String result = "";
                int read;
                do {
                    read = read(1);
                    result += binary.substring(index, index + 4);
                    index += 4;
                } while (read == 1);
                value = Long.parseLong(result, 2);
            } else {
                if (read(1) == 0) {
                    int lenght = read(15);
                    int newPacketsUntil = index + lenght;
                    while (index < newPacketsUntil) {
                        packets.add(new Packet());
                    }
                } else {
                    int size = read(11);
                    for (int i = 0; i < size; i++) {
                        packets.add(new Packet());
                    }
                }
            }
        }

        public int getVersion() {
            return version + packets.stream().mapToInt(p -> p.getVersion()).sum();
        }

        public long getValue() {
            if (packets.size() == 0) {
                return value;
            } else {
                switch (typeID) {
                    case 0: return packets.stream().mapToLong(p -> p.getValue()).sum();
                    case 1: return packets.stream().mapToLong(p -> p.getValue()).reduce(1L, (a, b) -> a * b);
                    case 2: return packets.stream().mapToLong(p -> p.getValue()).min().orElse(0);
                    case 3: return packets.stream().mapToLong(p -> p.getValue()).max().orElse(0);
                    case 5: return packets.get(0).getValue() > packets.get(1).getValue() ? 1L : 0L;
                    case 6: return packets.get(0).getValue() < packets.get(1).getValue() ? 1L : 0L;
                    case 7: return packets.get(0).getValue() == packets.get(1).getValue() ? 1L : 0L;
                    default: throw new IllegalArgumentException("Unexpected typeID: " + typeID);
                }
            }
        }
    }
}
