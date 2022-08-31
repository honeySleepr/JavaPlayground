import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * <h2>쿠킴한테 추천 받은 <a href="https://www.youtube.com/watch?v=5PDYHNCcjYM">최범균님의 TDD 연습</a>과<br>@ParameterizedTest,
 * @MethodSource를
 * 함께 연습<br> 참고: <a href="https://www.baeldung.com/parameterized-tests-junit-5">Baeldung</a> </h2>
 */
public class ParameterizedTestAnnotationTest {

	@Test
	public void ManualTest() { /* 테스트 안에 입력값과 기대값을 전부 입력 */
		assertThat(highlight("abc")).isEqualTo("abc");
		assertThat(highlight("note")).isEqualTo("{note}");
		assertThat(highlight("yes notea note")).isEqualTo("yes notea {note}");
	}

	@ParameterizedTest(name = "{index} 입력 : {0} ----- Arguments 전체 출력: {arguments}")
	@ValueSource(strings = {"abc", "note", "yes notea note"})
	public void ParameterizedTest_and_ValueSource(String input) {
		assertThat(highlight(input)).isEqualTo("???"); /*  @ValueSource는 이와 같은 테스트에는 적합하지 않은 것 같다 */
	}

	@ParameterizedTest(name = "{index} 입력 : {0}, 출력 : {1}   //   Arguments 전체 출력: {arguments}")
	@MethodSource("makeStream")
	@DisplayName("입력 받은 문자열에서 단독으로 존재하는(앞뒤에 다른 문자가 오지 않는) 'note'만 중괄호({})로 표시한다")
	public void ParameterizedTest_and_MethodSource(String input, String expected) {
		assertThat(highlight(input)).isEqualTo(expected); /* @MethodSource를 이용하면 입력값과 기대값을 따로 빼내어 깔끔한 테스트가 가능하다 */
	}

	/**
	 * @param input
	 * @return 입력받은 문자열에서 앞과 뒤가 비어있는 "note" 문자만 "{note}"로 변환한 문자열
	 */
	private String highlight(String input) {
		return input.replaceAll("\\bnote\\b", "{note}");
	}

	public static Stream<Arguments> makeStream() {
		return Stream.of(
			Arguments.of("abc", "abc"),
			Arguments.of("note", "{note}"),
			Arguments.of("1 note", "1 {note}"),
			Arguments.of("1 note 2", "1 {note} 2"),
			Arguments.of("keynote", "keynote"),
			Arguments.of("ke1note", "ke1note"),
			Arguments.of("yes note1", "yes note1"),
			Arguments.of("yes notea note", "yes notea {note}"),
			Arguments.of("yes note note", "yes {note} {note}"),
			Arguments.of("a note anote", "a {note} anote"),
			Arguments.of("no a note anote note aa", "no a {note} anote {note} aa")
		);
	}

}
