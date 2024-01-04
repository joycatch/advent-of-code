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

        int part1 = 0;
        for (Pattern p : patterns) {
            int h = p.getHorizontalLocation();
            part1 += h > -1 ? h * 100 : p.getVerticalLocation();
        }

        printAnswer(1, part1);
    }
}

class Pattern {

    List<String> lines = new ArrayList<>();

    public void addLine(String line) {
        this.lines.add(line);
    }

    public int getHorizontalLocation(List<String> lines) {
        int result = -1;
        for (int i = 0; i < lines.size() - 1; i++) {
            boolean isMirror = true;
            for (int diff = 1; i - diff + 1 >= 0 && i + diff < lines.size(); diff++) {
                String up = lines.get(i - diff + 1);
                String down = lines.get(i + diff);
                if (!up.equals(down)) {
                    isMirror = false;
                    break;
                }
            }
            if (isMirror) {
                return i + 1;
            }
        }
        return result;
    }

    public int getHorizontalLocation() {
        return getHorizontalLocation(this.lines);
    }

    public int getVerticalLocation() {
        return getHorizontalLocation(rotate());
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