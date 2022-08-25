package stream;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PracticeStream {

	public static void main(String[] args) {

		/*---------------reduce()----------------------*/
		int reduce = IntStream.of(3, 2)
			.reduce(0, (x, y) -> x * (y * x + 1)); // 0 * 0 = 0
		System.out.println(reduce);

		/**
		 * identity 값을 x 자리에 넣고, stream의 첫번째 값(3)을 y 자리에 넣고 연산을 돌렸을 때 나오는 값이
		 * 실제 연산의 첫번째 값이 된다. 즉, 여기서 identity를 이용해 첫 계산을 하면 1*(3+1) = 4 이다
		 * 그럼 실제 연산은 x=4, y=2 가 되어 4 * (2 + 1) = 12 가 된다
		 * 다음 연산은 x=12, y=1이 되어 12*(1+1) = 24 가 최종 답이 된다
		 */
		int reduce1 = IntStream.of(3, 2, 1)
			.reduce(1, (x, y) -> x * (y + 1)); // (3+1)(2+1)(1+1) = 24
		int reduce2 = IntStream.of(3, 2, 1)
			.reduce((x, y) -> x * (y + 1)).getAsInt(); // (3)(2+1)(1+1) = 18

		System.out.println("reduce1 = " + reduce1 + " reduce2 = " + reduce2);

		/*---------------toMap()----------------------*/

		List<Integer> list = new ArrayList<>(List.of(5, 2, 3, 4, 5));

		Map<Integer, Integer> collect = list.stream()
			.collect(Collectors.toMap(integer -> integer, integer -> 1, (v1, v2) -> v1 + 1));
		System.out.println(collect.entrySet());

		Map<Integer, Long> collect4 = list.stream()
			.collect(Collectors.groupingBy(integer -> integer, Collectors.counting()));
		System.out.println(collect4.entrySet());

		/* Key 중복이 발생할 경우 IllegalStateException이 발생한다 */
		//		Map<Integer, Integer> collect1 = list.stream()
		//			.collect(Collectors.toMap(i -> i, i -> i * 100));
		//		System.out.println(collect1.entrySet());

		/* 중복 Key가 있을 경우 Value를 어떻게 처리해줄지 정의한다 */
		Map<Integer, Integer> collect2 = list.stream()
			.collect(Collectors.toMap(i -> i, i -> i * 100, (v1, v2) -> v1 * 100 + v2));
		System.out.println(collect2.entrySet());

		Map<Integer, Integer> collect3 = list.stream()
			.collect(Collectors.toMap(i -> i, i -> i * 100, (v1, v2) -> v1 * 100 + v2, HashMap::new));
		System.out.println(collect3.entrySet());


		/*------------------groupingBy()-------------------*/

		//		Stream<String[]> stream = Stream.of(
		//			new String[]{"CAR", "Audi"},
		//			new String[]{"BIKE", "Harley Davidson"});

		Map<Integer, Long> collect5 = list.stream()
			.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		System.out.println(collect5.entrySet());

		/*------------------toMap(), groupingBy() 활용 비교-------------------*/
		String[][] input = {{"a", "A"}, {"b", "B"}, {"c", "A"}};

		int result1 = Arrays.stream(input)
			.collect(Collectors.groupingBy(s -> s[1], Collectors.mapping(s -> s[0], Collectors.counting())))
			.values()
			.stream()
			.reduce(1L, (x, y) -> x * (y + 1))
			.intValue();

		int result2 = Arrays.stream(input)
			.collect(Collectors.toMap(strings -> strings[1], strings -> 1, (x, y) -> x + 1))
			.values()
			.stream()
			.reduce(1, (x, y) -> x * (y + 1));

		System.out.println("result1 = " + result1 + " result2 = " + result2);
	}

}
