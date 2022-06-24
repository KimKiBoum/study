package Coding_test.baekjoon;

import java.util.*;

public class b_1152 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String st = scanner.nextLine();
        String[] st_arr = st.split(" ");

        if (st_arr.length == 0) {
            System.out.println(0);
        } else if (st_arr[0] == "") {
            System.out.println(st_arr.length - 1);
        } else {
            System.out.println(st_arr.length);
        }
    }
}
