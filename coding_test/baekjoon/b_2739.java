package Coding_test.baekjoon;

import java.util.Scanner;

public class b_2739 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        for (int i = 1; i <= 9; i++) {
            System.out.println(a + " * " + i + " = " + i*a);
        }
    }
}
