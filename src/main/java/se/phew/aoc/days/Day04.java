package se.phew.aoc.days;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day04 extends Challenge {

    public Day04() {
        super();

        int valid = 0;
        int totalEntries = 0;
        Passport passport = new Passport();

        for (String line : lines) {
            if (!line.isEmpty()) {
                String[] splits = line.split(" ");
                for (String split : splits) {
                    String[] temp = split.split(":");
                    String key = temp[0];
                    String value = temp[1];
                    if (!key.isEmpty() && !value.isEmpty()) {
                        if (validKeyAndValue(key, value)) {
                            passport.put(key, value);
                        }
                    } else {
                        System.out.println("LOL");
                    }
                }
            } else {
                totalEntries++;
                if (passport.isValid()) {
                    valid++;
                } else {
                    System.out.println(passport.toString() + " count = " + passport.map.keySet().size());
                }
                passport = new Passport();
            }
        }
        System.out.println("Total valid: " + valid);
        System.out.println("Total totalEntries: " + totalEntries);
    }

    private static final String COLOR_PATTERN = "^#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$";
    private static final Pattern pattern = Pattern.compile(COLOR_PATTERN);

    public static boolean isValidColor(final String colorCode) {
        Matcher matcher = pattern.matcher(colorCode);
        return matcher.matches();
    }

    private static boolean validKeyAndValue(String key, String value) {
        if (key.equals("byr")) {
            try {
                int i = Integer.parseInt(value);
                return i >= 1920 && i <= 2002;
            } catch (Exception e) {
                return false;
            }
        }

        if (key.equals("iyr")) {
            try {
                int i = Integer.parseInt(value);
                return i >= 2010 && i <= 2020;
            } catch (Exception e) {
                return false;
            }
        }

        if (key.equals("eyr")) {
            try {
                int i = Integer.parseInt(value);
                return i >= 2020 && i <= 2030;
            } catch (Exception e) {
                return false;
            }
        }

        if (key.equals("hgt")) {
            if (value.contains("cm")) {
                try {
                    int i = Integer.parseInt(value.replaceAll("cm", ""));
                    return i >= 150 && i <= 193;
                } catch (Exception e) {
                    return false;
                }
            }
            if (value.contains("in")) {
                try {
                    int i = Integer.parseInt(value.replaceAll("in", ""));
                    return i >= 59 && i <= 76;
                } catch (Exception e) {
                    return false;
                }
            }
        }

        if (key.equals("hcl")) {
            return isValidColor(value);
        }

        if (key.equals("ecl")) {
            return value.equals("amb") ||
                    value.equals("blu") ||
                    value.equals("brn") ||
                    value.equals("gry") ||
                    value.equals("grn") ||
                    value.equals("hzl") ||
                    value.equals("oth");
        }

        if (key.equals("pid")) {
            try {
                Integer.parseInt(value);
            } catch (Exception e) {
                return false;
            }

            return value.length() == 9;
        }

        return false;
    }

    class Passport {
        HashMap<String, String> map = new HashMap<>();

        void put(String key, String value) {
            map.put(key, value);
        }

        boolean isValid() {
            return map.containsKey("byr") &&
                    map.containsKey("iyr") &&
                    map.containsKey("eyr") &&
                    map.containsKey("hgt") &&
                    map.containsKey("hcl") &&
                    map.containsKey("ecl") &&
                    map.containsKey("pid");
        }

        public String toString() {
            return map.toString();
        }
    }
}
