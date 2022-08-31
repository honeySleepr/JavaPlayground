### @Spy, @Mock

> 둘의 차이점 : **Mock**은 대상 객체와 타입만 같은 빈 껍데기를 복사한 인스턴스이고, **Spy**는 실제 대상 객체를 감싼 인스턴스이다. 즉, 두 경우 모두 복사한 객체의 메서드는 호출할 수 있지만, Spy는 실제 메서드와 같이 작동을 하는 반면 Mock은 아무런 작동도 하지 않는다.

[Mockito - Using Spies | Baeldung](https://www.baeldung.com/mockito-spy)

```java
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
```

* @Spy 와 `Mockito.spy()` 두가지 방법으로 사용할 수 있다 (Mock도 마찬가지)
* Spy와 Mock 모두 원하는 값을 리턴하도록 stubbing이 가능하다 (`doReturn()`)

> Spy, Mock, Stub 가 서로 다른거라고 하는데.. Stub과의 차이는 아직 이해가 안되었다
