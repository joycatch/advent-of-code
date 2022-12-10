package se.phew.aoc.days;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class Challenge {

    private String SESSION = "";

    protected List<String> lines;
    protected boolean isTest;

    public Challenge() {
    }

    public Challenge(boolean isTest) {
        this.isTest = isTest;
        System.out.println("[INFO] ------------------------------------------------------------------------");
        System.out.println("[INFO] RUNNING: " + this.getClass().getSimpleName() + (isTest ? " in test mode" : ""));
        System.out.println("[INFO] ------------------------------------------------------------------------");
        System.out.println("[INFO]");
        try {
            createFilesAndPopulateWithInput();
            lines = Files.readAllLines(getFile(isTest).toPath(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private File getFile(boolean isTest) throws URISyntaxException {
        return new File(new File("src/main/resources/" + getFilename(isTest)).getAbsolutePath());
    }

    private String getFilename(boolean isTest) {
        return "20" + getYear() + "/" + getDay() + (isTest ? "-test" : "") + ".txt";
    }

    private String getYear() {
        return this.getClass().getPackage().getName().split("\\.")[4].replaceAll("twenty", "");
    }

    private String getDay() {
        return this.getClass().getSimpleName().replaceAll("Day(0)*", "");
    }

    protected void createFilesAndPopulateWithInput() {
        try {
            // Create test input file if not already created
            File testFile = getFile(true);
            if (!testFile.exists()) {
                testFile.createNewFile();
            }
            // Don't update real input file if already fetched
            if (getFile(false).exists()) {
                System.out.println("[INFO] Input already fetched");
                System.out.println("[INFO] ------------------------");
                System.out.println("[INFO]");
                return;
            }
            if (StringUtils.isBlank(SESSION)) {
                System.out.println("[INFO] Input cannot be fetched when no SESSION set");
                System.out.println("[INFO] ------------------------");
                System.out.println("[INFO]");
                return;
            }
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.COOKIE, "session=" + SESSION);
            HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
            URI uri = new URI("https://adventofcode.com/20" + getYear() + "/day/" + getDay() + "/input");
            ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
            File file = getFile(false);
            file.createNewFile();
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(result.getBody());
            myWriter.close();
            System.out.println("[INFO] Fetched and updated " + getYear() + " - Day " + getDay() + " input");
            System.out.println("[INFO] -------------------------------------");
            System.out.println("[INFO]");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void printAnswer(int part, Object answer) {
        System.out.println("[INFO] Part " + part + ": " + answer);
        System.out.println("[INFO]");
    }

    protected void print(String info) {
        print(info);
        printEmptyLine();
    }

    protected void printOnly(String info) {
        System.out.println("[INFO] " + info);
    }

    protected void printEmptyLine() {
        System.out.println("[INFO]");
    }
}
