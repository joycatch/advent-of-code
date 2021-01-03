package se.phew.aoc.days;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class Challenge {

    protected List<String> lines;
    protected String className;
    protected boolean isTest;

    public Challenge() {
        this(false);
    }

    public Challenge(boolean isTest) {
        this.isTest = isTest;
        className = this.getClass().getSimpleName();
        System.out.println("[INFO] ------------------------------------------------------------------------");
        System.out.println("[INFO] RUNNING: " + className + (isTest ? " in test mode" : ""));
        System.out.println("[INFO] ------------------------------------------------------------------------");
        System.out.println("[INFO]");
        try {
            String fileName = "2020/" + className.replaceAll("Day", "") + (isTest ? "-test" : "") + ".txt";
            File file = new File(getClass().getClassLoader().getResource(fileName).toURI());
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void printAnswer(int part, Object answer) {
        System.out.println("[INFO] Part " + part + ": " + answer);
        System.out.println("[INFO]");
    }

    protected void print(String info) {
        System.out.println("[INFO] " + info);
        System.out.println("[INFO]");
    }
}
