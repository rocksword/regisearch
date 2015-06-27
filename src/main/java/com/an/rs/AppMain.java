package com.an.rs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class AppMain {
    private static String input = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input key: ");
        while (scanner.hasNext()) {
            input = scanner.next();
            if (input == null || input.isEmpty()) {
                System.err.println("Invalid input.");
                continue;
            }
            if (input.equals("q")) {
                System.out.println("Exit application.");
                System.exit(0);
            }
            process(input);
            System.out.println("Please input key: ");
        }
        scanner.close();
    }

    private static void process(String input) {
        String homeDir = System.getenv("RS_HOME");
        if (homeDir == null || homeDir.isEmpty()) {
            System.err.println("Please set RS_HOME.");
            System.exit(1);
        }
        File d = new File(homeDir);
        File[] files = d.listFiles();
        StringBuilder text = new StringBuilder();
        for (File f : files) {
            String path = f.getPath();
            String fileName = path.substring(path.lastIndexOf(File.separator) + 1);
            if (fileName.endsWith(".txt")) {
                try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        if (line.contains(input)) {
                            text.append(fileName).append("\t").append(line).append("\n");
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("");
        System.out.println("Result:");
        System.out.println(text.toString());
    }
}
