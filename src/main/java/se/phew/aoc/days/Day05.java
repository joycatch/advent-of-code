package se.phew.aoc.days;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Day05 extends Challenge {

    public Day05() {
        super();

        ArrayList<String> list = new ArrayList<String>();

        for (String line : lines) {
            list.add(line);
        }

        Collections.sort(list);

        HashMap<String, ArrayList<String>> map = new HashMap<>();

        System.out.println("After Sorting:");

        for(String entry : list){
            String row = entry.substring(0, 7);
            String seat = entry.substring(7);

            if (map.get(row) == null) {
                ArrayList<String> value = new ArrayList<>();
                value.add(seat);
                map.put(row, value);
            } else {
                map.get(row).add(seat);
            }
        }

        for (String key : map.keySet()) {
            if (map.get(key).size() != 8) {
                System.out.println("On row: " + key);
                System.out.println(map.get(key));
            }
        }

        // BBBFBBB LLL = 0
        // 1110111
        // 6543210

        // LRR
        // 011
        // 210

        // 64 + 32 + 16 + 4 + 2 + 1 = 119

        /*BFFFBBB LLR = 1
        1000111
        64 + 4 + 2 + 1 = 71

        569*/
    }

}
