package se.phew.aoc.days.twenty20;

import se.phew.aoc.days.Challenge;

import java.util.*;

public class Day13 extends Challenge {

    public Day13() {
        super(false);

        HashMap<Long, Integer> map = new HashMap<>();
        int earliestDeparture = Integer.parseInt(lines.get(0));
        int pos = 0;
        for (String id : lines.get(1).split(",")) {
            if (!id.equals("x")) {
                map.put(Long.parseLong(id), pos);
            }
            pos++;
        }

        // Part 1
        outerloop:
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            int timestamp = earliestDeparture + i;
            for (long bus : map.keySet()) {
                if (timestamp % bus == 0) {
                    print("Take bus: " + bus + " after " + i + " minutes... ");
                    printAnswer(1, bus * i);
                    break outerloop;
                }
            }
        }

        // Part 2
        List<Long> busses = new ArrayList<>(map.keySet());
        int size = busses.size();
        long N = busses.stream().reduce(1L, (a, b) -> a * b);
        long b[] = new long[size];
        long n[] = new long[size];
        long x[] = new long[size];
        long bnx[] = new long[size];
        long sum = 0;

        for (int i = 0; i < size; i++) {
            b[i] = map.get(busses.get(i));
            n[i] = N / busses.get(i);
            x[i] = findModularInverse(n[i], busses.get(i));
            bnx[i] = b[i]*n[i]*x[i];
            sum += bnx[i];
        }
        long t = Math.abs(N - (sum % N));

        // print("All busses depart on time? " + allBussesDepartureAtTheCorrectTime(t, map));

        printAnswer(2, t);
    }

    public int findModularInverse(long number, long m) {
        number = number % m;
        for (int x = 1; x < m; x++) {
            if ((number * x) % m == 1) {
                return x;
            }
        }
        return 1;
    }

    public boolean allBussesDepartureAtTheCorrectTime(long t, HashMap<Long, Integer> map) {
        for (long id : map.keySet()) {
            if ((t + map.get(id)) % id != 0) {
                return false;
            }
        }
        return true;
    }
}
