package org.simplesearchengine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        String fileName = "SimpleSearchEngineDemo/names.txt";

        if (args.length == 2 && args[0].equals("--data")) {
            fileName = args[1];
        }

        List<String> people = new ArrayList<>();
        Map<String, Set<Integer>> invertedIndex = new HashMap<>();

        loadData(fileName, people, invertedIndex);

        Scanner sc = new Scanner(System.in);

        while (true) {
            printMenu();
            String option = sc.nextLine();

            switch (option) {
                case "1":
                    findPerson(sc, people, invertedIndex);
                    break;
                case "2":
                    printAll(people);
                    break;
                case "0":
                    System.out.println("Bye!");
                    return;
                default:
                    System.out.println("Incorrect option! Try again.");
            }
            System.out.println();
        }
    }

    private static void loadData(String fileName, List<String> people,
                                 Map<String, Set<Integer>> index) {

        try {
            Scanner fileScanner = new Scanner(new File(fileName));
            int lineNumber = 0;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                people.add(line);

                String[] words = line.split("\\s+");

                for (String word : words) {
                    String key = word.toLowerCase();

                    index.putIfAbsent(key, new HashSet<>());
                    index.get(key).add(lineNumber);
                }

                lineNumber++;
            }

            fileScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    private static void printMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all persons");
        System.out.println("0. Exit");
    }

    private static void findPerson(Scanner sc,
                                   List<String> people,
                                   Map<String, Set<Integer>> index) {

        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String strategy = sc.nextLine().trim().toUpperCase();

        System.out.println("Enter a name or email to search all suitable people.");
        String[] queryWords = sc.nextLine().toLowerCase().split("\\s+");

        Set<Integer> resultIndexes = new HashSet<>();

        switch (strategy) {

            case "ANY":
                for (String word : queryWords) {
                    if (index.containsKey(word)) {
                        resultIndexes.addAll(index.get(word));
                    }
                }
                break;

            case "ALL":
                boolean firstWord = true;

                for (String word : queryWords) {
                    if (!index.containsKey(word)) {
                        resultIndexes.clear();
                        break;
                    }

                    if (firstWord) {
                        resultIndexes.addAll(index.get(word));
                        firstWord = false;
                    } else {
                        resultIndexes.retainAll(index.get(word));
                    }
                }
                break;

            case "NONE":
                Set<Integer> excluded = new HashSet<>();

                for (String word : queryWords) {
                    if (index.containsKey(word)) {
                        excluded.addAll(index.get(word));
                    }
                }

                for (int i = 0; i < people.size(); i++) {
                    if (!excluded.contains(i)) {
                        resultIndexes.add(i);
                    }
                }
                break;

            default:
                System.out.println("Unknown strategy.");
                return;
        }

        printResults(resultIndexes, people);
    }

    private static void printResults(Set<Integer> resultIndexes,
                                     List<String> people) {

        if (resultIndexes.isEmpty()) {
            System.out.println("No matching people found.");
        } else {
            System.out.println(resultIndexes.size() + " persons found:");
            for (int index : resultIndexes) {
                System.out.println(people.get(index));
            }
        }
    }

    private static void printAll(List<String> people) {
        System.out.println("=== List of people ===");
        for (String person : people) {
            System.out.println(person);
        }
    }
}