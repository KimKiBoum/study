import java.util.PriorityQueue;
import java.util.Collections;

class Solution {
    public long solution(int n, int[] works) {
        long answer = 0;
        
        PriorityQueue<Integer> priorityQueueHighest = new PriorityQueue<>(Collections.reverseOrder());
        for (int work : works) {
            priorityQueueHighest.offer(work);
        }
        while (n > 0) {
            int highest = priorityQueueHighest.poll();
            if (highest <= 0) {
                return 0;
            }
            priorityQueueHighest.offer(highest-1);
            n -= 1;
        }
        
        for (int highest : priorityQueueHighest) {
            answer += highest*highest;
        }
        return answer;
    }
}
