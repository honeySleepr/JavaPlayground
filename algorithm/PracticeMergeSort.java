import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class PracticeMergeSort {

	public int[] solution(int[] arr) {
		int[] temp = new int[arr.length];
		mergeSort(arr, temp, 0, arr.length - 1);

		return arr;
	}

	private void mergeSort(int[] arr, int[] temp, int start, int end) {
		if (start < end) {
			int mid = (start + end) / 2;
			mergeSort(arr, temp, start, mid);
			mergeSort(arr, temp, mid + 1, end);
			merge(arr, temp, start, mid, end);
		}
	}

	private void merge(int[] arr, int[] temp, int start, int mid, int end) {
		for (int i = start; i <= end; i++) {
			temp[i] = arr[i];
		}
		int part1 = start;
		int part2 = mid + 1;
		int index = start;
		while (part1 <= mid && part2 <= end) {
			if (temp[part1] <= temp[part2]) {
				arr[index] = temp[part1];
				part1++;
			} else {
				arr[index] = temp[part2];
				part2++;
			}
			index++;
		}
		for (int i = 0; i <= mid - part1; i++) {
			arr[index + i] = temp[part1 + i];
		}
	}

}

class TestPracticeMergeSort {

	private final PracticeMergeSort p = new PracticeMergeSort();

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
