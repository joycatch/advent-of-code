package se.phew.aoc.days;

public class Day03 extends Challenge {

    public Day03() {
        super();

        int trees = 0;

        int lineCount = 0;
        int xPos = 0;
        for (String line : lines) {
            StringBuilder sb = new StringBuilder(line);
            if (lineCount % 2 == 0) {
                int pos = xPos % line.length();
                xPos++;
                char setThis = 'O';
                if (line.charAt(pos) == '#') {
                    trees++;
                    setThis = 'X';
                }
                sb.setCharAt(pos, setThis);
            }
            System.out.println(sb.toString());
            lineCount++;
        }
        System.out.println("Total count: " + trees);
    }

    /*

    Right 1, down 1: 79
    Right 3, down 1: 216
    Right 5, down 1: 91
    Right 7, down 1: 96
    Right 1, down 2: 45

    Answer: 6708199680

     */
}
