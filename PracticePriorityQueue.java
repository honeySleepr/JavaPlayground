import java.util.PriorityQueue;

public class PracticePriorityQueue {

	public static void main(String[] args) {
		/* 정렬된 순서대로 poll 되는 queue */
		PriorityQueue<Integer> pQueue = new PriorityQueue<>((o1, o2) -> {
			if (Math.abs(o1) == Math.abs(o2)) {
				return o1 - o2; /* 두 수의 절대값이 같으면 원래 값으로 비교해서 오름차순 */
			}
			return Math.abs(o1) - Math.abs(o2); /* 절대값으로 비교해서 오름차순 */
		});
		pQueue.add(1);
		pQueue.add(3);
		pQueue.add(-5);
		pQueue.add(2);
		pQueue.add(-1);
		int size = pQueue.size();
		for (int i = 0; i < size; i++) {
			System.out.println(pQueue.poll());
		}

	}
}
/*
출력 :
-1
1
2
3
-5

*/
