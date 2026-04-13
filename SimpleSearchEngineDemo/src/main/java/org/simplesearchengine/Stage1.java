package org.simplesearchengine;
import java.util.Scanner;

public class Stage1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] words = sc.nextLine().split(" ");
        String target = sc.nextLine();
        int index = -1;

        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(target)) {
                index = i + 1;
                break;
            }
        }
        if (index != -1) {
            System.out.println(index);
        } else {
            System.out.println("Not Found");
        }
    }
}
