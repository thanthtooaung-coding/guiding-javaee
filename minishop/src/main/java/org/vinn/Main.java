package org.vinn;

import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {

        /*
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter something(No space): ");
        char[] array = scanner.nextLine().toCharArray();
        String unShuffled = new String(array);
        System.out.println("Array before shuffling - " + unShuffled);
        ArrayUtils.shuffle(array);
        String shuffled = new String(array);
        System.out.println("Modified Array after shuffling - " + shuffled);
         */

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter something (No space): ");
        String input = scanner.nextLine();

        List<Character> charList = new ArrayList<>();
        for (char c : input.toCharArray()) {
            charList.add(c);
        }

        System.out.println("Array before shuffling: " + input);

        Collections.shuffle(charList);

        StringBuilder shuffled = new StringBuilder();
        for (char c : charList) {
            shuffled.append(c);
        }

        System.out.println("Modified Array after shuffling: " + shuffled);
    }
}