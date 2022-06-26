package Coding_test.baekjoon;

import java.util.HashSet;
import java.util.Scanner;

public class b_3052 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 0; i <= 11; i ++) {
            hashSet.add(scanner.nextInt() % 42);
        }

        scanner.close();
        System.out.println(hashSet.size());
    }
}
