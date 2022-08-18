import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class PracticeQuickSort {

	public int[] solution(int[] arr) {
		quickSort(arr, 0, arr.length - 1);
		return arr;
	}

	private void quickSort(int[] arr, int start, int end) {
		int partition2StartIndex = partition(arr, start, end);

		if (partition2StartIndex - start > 1) {
			quickSort(arr, start, partition2StartIndex - 1);
		}
		if (end - partition2StartIndex > 0) {
			quickSort(arr, partition2StartIndex, end);
		}
	}

	private int partition(int[] arr, int start, int end) {
		int mid = (start + end) / 2;
		int target = arr[mid];
		while (start <= end) {
			while (arr[start] < target) {
				start++;
			}
			while (target < arr[end]) {
				end--;
			}
			if (start <= end) {
				int temp = arr[start];
				arr[start] = arr[end];
				arr[end] = temp;

				start++;
				end--;
			}
		}
		return start;
	}
}

class TestPracticeQuickSort {

	private final PracticeQuickSort p = new PracticeQuickSort();

	@Test
	void 입력_1() {
		int[] arr = {2, 6, 7, 5, 9, 0, 1, 4, 3, 8};
		Arrays.sort(arr);
		assertArrayEquals(arr, p.solution(new int[]{2, 6, 7, 5, 9, 0, 1, 4, 3, 8}));
	}

	@Test
	void 입력_2() {
		int[] arr = {5, 2, 3, 1, 4, 2, 1, 7};
		Arrays.sort(arr);
		assertArrayEquals(arr, p.solution(new int[]{5, 2, 3, 1, 4, 2, 1, 7}));
	}
}
