import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PracticeStream {

	public static void main(String[] args) {


		/*---------------toMap()----------------------*/

		List<Integer> list = new ArrayList<>(List.of(5, 2, 3, 4, 5));

		Map<Integer, Integer> collect = list.stream()
			.collect(Collectors.toMap(integer -> integer, integer -> 1, (v1, v2) -> v1 + 1));
		System.out.println(collect.entrySet());

		Map<Integer, Long> collect4 = list.stream()
			.collect(Collectors.groupingBy(integer -> integer, Collectors.counting()));
		System.out.println(collect4.entrySet());

		/* Key 중복이 발생할 경우 IllegalStateException이 발생한다 */
		Map<Integer, Integer> collect1 = list.stream()
			.collect(Collectors.toMap(i -> i, i -> i * 100));
		System.out.println(collect1.entrySet());

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
	}

}
