package Coding_test.baekjoon;

import java.util.Scanner;

public class b_2525 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int hour = scanner.nextInt();
        int minute = scanner.nextInt();
        int addMinute = scanner.nextInt();

        int all = hour*60 + minute + addMinute;
        hour = all/60;
        minute = all%60;
        
        if (hour >= 24) {
            hour = hour-24;
        }

        System.out.println(hour + " " + minute);
    }
}
