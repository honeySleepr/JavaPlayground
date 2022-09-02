package set;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class PracticeSet {

	public static void main(String[] args) {
		/* HashSet과 LinkedHashSet의 데이터 출력 순서 비교 */
		compare(1);
		compare(2);
		compare(5);
		compare(10);
		compare(100);

	}

	private static void compare(int interval) {
		Set<Integer> hashSet = new HashSet<>();
		Set<Integer> linkedHashSet = new LinkedHashSet<>();
		for (int i = 0; i < 1000; i += interval) {
			hashSet.add(i);
			linkedHashSet.add(i);
		}

		StringBuilder sb = new StringBuilder();

		sb.append("\n").append("---------HashSet--------").append(interval).append("\n");
		boolean ordered = true;
		int temp = Integer.MIN_VALUE;
		for (Integer integer : hashSet) {
			sb.append(integer).append(" ");
			if (temp > integer) {
				ordered = false;
			}
			temp = integer;
		}
		sb.append("\n").append("ordered : ").append(ordered);
		sb.append("\n").append("---------LinkedHashSet--------").append("\n");
		temp = Integer.MIN_VALUE;
		ordered = true;
		for (Integer integer : linkedHashSet) {
			sb.append(integer).append(" ");
			if (temp > integer) {
				ordered = false;
			}
			temp = integer;
		}
		sb.append("\n").append("ordered : ").append(ordered);
		System.out.println(sb);
	}

}
