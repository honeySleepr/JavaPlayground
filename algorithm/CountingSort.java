import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

public class CountingSort {

	public int[] countingSort(int[] arr) {
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		for (int i : arr) {
			min = Math.min(min, i);
			max = Math.max(max, i);
		}

		int[] countArr = new int[max - min + 1];
		for (int i : arr) {
			countArr[i - min]++;
		}

		/* countArr를 누적 합 행렬로 변환 */
		for (int i = 1; i < countArr.length; i++) {
			countArr[i] = countArr[i - 1] + countArr[i];
		}

		int[] sortedArr = new int[arr.length];
		for (int i : arr) {
			countArr[i - min]--;
			sortedArr[countArr[i - min]] = i;
		}

		return sortedArr;
	}

	static class TestCountingSort {

		private final CountingSort p = new CountingSort();

		@Test
		void test0() {
			int[] input = {3, 1, 0, 5, 4, 2};
			int[] output = {0, 1, 2, 3, 4, 5};
			assertArrayEquals(output, p.countingSort(input));
		}

		@Test
		void test1() {
			int[] input = {8, -4, 1, 5, 0, 9, 4, 3, 1};
			int[] output = {-4, 0, 1, 1, 3, 4, 5, 8, 9};
			assertArrayEquals(output, p.countingSort(input));
		}

		@Test
		void test2() {
			int[] input = {2, 3, 2, 3, 1, 1, 3, 1};
			int[] output = {1, 1, 1, 2, 2, 3, 3, 3};
			assertArrayEquals(output, p.countingSort(input));
		}
	}

}
