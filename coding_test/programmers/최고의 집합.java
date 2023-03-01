import java.util.*;

class Solution {
    public int[] solution(int n, int s) {
        int[] answer = new int[n];
        
        int half = s/n;
        int remainder = s%n;
        if (half == 0) {
            int[] nothing = {-1};
            return nothing;
        } else if (remainder == 0) {
            for (int i = 0; i<n; i++) {
                answer[i] = half;
            }
        } else {
            for (int j = n-1; j>=0; j--) {
                answer[j] = half;
                if (remainder !=0 ) {
                    answer[j] += 1;
                    remainder -= 1;
                }
            }
        }
        
        return answer;
    }
}
