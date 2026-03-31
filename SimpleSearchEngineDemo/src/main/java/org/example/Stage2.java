package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Stage2 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the number of people:");
        int n = Integer.parseInt(sc.nextLine().trim());

        List<String> data = new ArrayList<>();

        System.out.println("Enter all people:");
        for (int i = 0; i < n; i++) {
            data.add(sc.nextLine().trim());
        }

        System.out.println();
        System.out.println("Enter the number of search queries:");
        int m = Integer.parseInt(sc.nextLine().trim());

        for (int i = 0; i < m; i++) {

            System.out.println();
            System.out.println("Enter data to search people:");
            String query = sc.nextLine().trim().toLowerCase();

            boolean found = false;

            System.out.println();
            System.out.println("Found people:");

            for (String line : data) {
                if (line.toLowerCase().contains(query)) {
                    System.out.println(line);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No matching people found.");
            }
        }

        sc.close();
    }
}