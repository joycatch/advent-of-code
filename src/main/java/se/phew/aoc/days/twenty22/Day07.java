package se.phew.aoc.days.twenty22;

import se.phew.aoc.days.Challenge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day07 extends Challenge {

    public Day07() {
        super(false);

        Directory fileSystem = new Directory("/");
        Directory current = fileSystem;
        Stack<Directory> stack = new Stack<>();

        for (String line : lines) {
            if (line.startsWith("$ ")) {
                if ("cd".equals(line.substring(2, 4))) {
                    String folderName = line.replace("$ cd ", "");
                    switch (folderName) {
                        case "/":
                            current = fileSystem;
                            break;
                        case "..":
                            current = stack.pop();
                            break;
                        default:
                            stack.push(current);
                            current = current.directories().stream().filter(f -> folderName.equals(f.name)).findFirst().get();
                            break;
                    }
                }
            } else {
                if (line.startsWith("dir ")) {
                    current.files.add(new Directory(line.replaceAll("dir ", "")));
                } else {
                    String[] s = line.split(" ");
                    current.files.add(new FileType(s[1], Integer.parseInt(s[0])));
                }
            }
        }

        ArrayList<Integer> sizes = new ArrayList<>();
        traverse(fileSystem, sizes);
        printAnswer(1, sizes.stream().mapToInt(s -> s).filter(s -> s < 100000).sum());

        int minimumFolderSize = Math.abs(40000000 - fileSystem.size());
        List<Integer> options = sizes.stream().filter(s -> s >= minimumFolderSize).collect(Collectors.toList());
        Collections.sort(options);
        printAnswer(2, options.stream().findFirst().get());
    }

    private static void traverse(Directory current, ArrayList<Integer> sizes) {
        for (Directory directory : current.directories()) {
            sizes.add(directory.size());
            if (!directory.directories().isEmpty()) {
                traverse(directory, sizes);
            }
        }
    }

    abstract class File {
        String name;
        abstract int size();
    }

    class Directory extends File {
        List<File> files = new ArrayList<>();

        public Directory(String name) {
            this.name = name;
        }

        public int size() {
            return files.stream().mapToInt(f -> f.size()).sum();
        }

        public List<Directory> directories() {
            return files.stream().filter(f -> f instanceof Directory).map(f -> (Directory) f).collect(Collectors.toList());
        }

        public String toString() {
            return name;
        }
    }

    class FileType extends File {
        int size;
        public FileType(String name, int size) {
            this.name = name;
            this.size = size;
        }

        public int size() {
            return size;
        }
    }
}
