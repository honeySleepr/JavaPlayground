package mockspy;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

//@ExtendWith(MockitoExtension.class) // 언제 필요한거지?
public class MockSpyTest {

	@Spy
	List<String> spyList2 = new ArrayList<>(); // 메서드 안에서 선언한 spyList와 똑같은 역할을 한다
	@Mock
	List<String> mockList2 = new ArrayList<>();

	@Test
	void mockAndSpy() {
		List spyList = Mockito.spy(new ArrayList());
		List mockList = Mockito.mock(ArrayList.class);

		assertThat(spyList.size()).isEqualTo(0);
		assertThat(mockList.size()).isEqualTo(0);

		String input = "bc";
		spyList.add(input);
		mockList.add(input);

		Mockito.verify(spyList, Mockito.times(1)).add(input); /* 실제로 add() 메서드가 주어진 인자를 받아서 호출되었는지 확인 */
		Mockito.verify(mockList, Mockito.times(1)).add(input); /* times()를 통해 몇번 호출되었는지도 검증*/

		assertThat(spyList.size()).isEqualTo(1);
		assertThat(mockList.size()).isEqualTo(0);

		spyList.clear();
		mockList.clear();

		assertThat(spyList.size()).isEqualTo(0);
		assertThat(mockList.size()).isEqualTo(0);

		Mockito.doReturn(100).when(spyList).size();
		Mockito.doReturn(100).when(mockList).size(); /* spy, mock 두 경우 모두 stubbing 가능 */

		assertThat(spyList.size()).isEqualTo(100);
		assertThat(mockList.size()).isEqualTo(100);
	}
}
