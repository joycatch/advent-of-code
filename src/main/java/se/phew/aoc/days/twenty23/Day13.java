package se.phew.aoc.days.twenty23;

import org.apache.commons.lang3.StringUtils;
import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.List;

public class Day13 extends Challenge {

    public Day13() {
        super(false);

        List<Pattern> patterns = new ArrayList<>();

        Pattern pattern = new Pattern();
        for (String line : lines) {
            if (StringUtils.isBlank(line)) {
                patterns.add(pattern);
                pattern = new Pattern();
            } else {
                pattern.addLine(line);
            }
        }
        patterns.add(pattern);

        int part1 = 0, part2 = 0;
        for (Pattern p : patterns) {
            int h = p.getHorizontalLocation(true);
            int v = p.getVerticalLocation(true);
            part1 += h > -1 ? h * 100 : v;

            h = p.getHorizontalLocation(false);
            v = p.getVerticalLocation(false);
            part2 += h > -1 ? h * 100 : v;
        }

        printAnswer(1, part1);
        printAnswer(2, part2);
    }
}

class Pattern {

    List<String> lines = new ArrayList<>();

    public void addLine(String line) {
        this.lines.add(line);
    }

    public int getHorizontalLocation(List<String> lines, boolean part1) {
        int result = -1;
        for (int i = 0; i < lines.size() - 1; i++) {
            boolean isMirror = true;
            int numberOfSmudges = 0;
            for (int diff = 1; i - diff + 1 >= 0 && i + diff < lines.size(); diff++) {
                String up = lines.get(i - diff + 1);
                String down = lines.get(i + diff);
                if (part1) {
                    if (countDiff(up, down) > 0){
                        isMirror = false;
                        break;
                    }
                } else {
                    numberOfSmudges += countDiff(up, down);
                    if (numberOfSmudges > 1) {
                        isMirror = false;
                        break;
                    }
                }
            }
            if (part1 && isMirror || !part1 && isMirror && numberOfSmudges == 1) {
                return i + 1;
            }
        }
        return result;
    }

    private static int countDiff(String up, String down) {
        int diff = 0;
        for (int i = 0; i < up.length(); i++) {
            diff += up.charAt(i) == down.charAt(i) ? 0 : 1;
        }
        return diff;
    }

    public int getHorizontalLocation(boolean part1) {
        return getHorizontalLocation(this.lines, part1);
    }

    public int getVerticalLocation(boolean part1) {
        return getHorizontalLocation(rotate(), part1);
    }

    public List<String> rotate() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < lines.get(0).toCharArray().length; i++) {
            String newLine = "";
            for (String line : lines) {
                newLine += line.charAt(i);
            }
            result.add(newLine);
        }
        return result;
    }
}