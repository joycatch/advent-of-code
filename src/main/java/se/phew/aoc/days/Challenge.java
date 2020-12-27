package se.phew.aoc.days;

import org.apache.log4j.Logger;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class Challenge {

    protected List<String> lines;

    public Challenge() {
        this(false);
    }

    public Challenge(boolean test) {

        String fileName = this.getClass().getSimpleName();

        System.out.println("[INFO] ------------------------------------------------------------------------");
        System.out.println("[INFO] RUNNING: " + fileName);
        System.out.println("[INFO] ------------------------------------------------------------------------\n");
        try {
            fileName = "2020/" + fileName.replaceAll("Day", "") + (test ? "-test" : "") + ".txt";
            File file = new File(getClass().getClassLoader().getResource(fileName).toURI());
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
