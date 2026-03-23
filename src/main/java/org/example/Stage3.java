package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Stage3 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of people:");
        int n = Integer.parseInt(sc.nextLine());

        List<String> people = new ArrayList<>();

        System.out.println("Enter all people:");
        for (int i = 0; i < n; i++) {
            people.add(sc.nextLine());
        }

        while (true) {

            System.out.println();
            System.out.println("=== Menu ===");
            System.out.println("1. Find a person");
            System.out.println("2. Print all people");
            System.out.println("0. Exit");

            String option = sc.nextLine();

            if (option.equals("1")) {

                System.out.println();
                System.out.println("Enter a name or email to search all suitable people.");

                String search = sc.nextLine().toLowerCase();
                boolean found = false;

                for (String person : people) {
                    if (person.toLowerCase().contains(search)) {
                        System.out.println(person);
                        found = true;
                    }
                }

                if (!found) {
                    System.out.println("No matching people found.");
                }

            } else if (option.equals("2")) {

                System.out.println();
                System.out.println("=== List of people ===");

                for (String person : people) {
                    System.out.println(person);
                }

            } else if (option.equals("0")) {

                System.out.println("Bye!");
                break;

            } else {
                System.out.println("Incorrect option! Try again.");
            }
        }

        sc.close();
    }
}