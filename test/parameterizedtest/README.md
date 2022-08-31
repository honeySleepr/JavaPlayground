### @ParameterizedTest, @MethodSource

[Guide to JUnit 5 Parameterized Tests | Baeldung](https://www.baeldung.com/parameterized-tests-junit-5)

* 입력값만 넣을 수 있는 @ValueSource 보다 더 많은 작업을 할 수 있다.

```java
@ParameterizedTest(name = "{index} 입력 : {0}, 출력 : {1}   //   Arguments 전체 출력: {arguments}") // 테스트 결과창 출력 포맷 설정
@MethodSource("makeStream")
@DisplayName("입력 받은 문자열에서 단독으로 존재하는(앞뒤에 다른 문자가 오지 않는) 'note'만 중괄호({})로 표시한다")
public void ParameterizedTest_and_MethodSource(String input,String expected){
        assertThat(highlight(input)).isEqualTo(expected); /* @MethodSource를 이용하면 입력값과 기대값을 따로 빼내어 깔끔한 테스트가 가능하다 */
        }

private String highlight(String input){
        return input.replaceAll("\\bnote\\b","{note}");
        }

public static Stream<Arguments> makeStream(){
        return Stream.of(
        Arguments.of("abc","abc"),
        Arguments.of("note","{note}"),
        Arguments.of("1 note","1 {note}"),
        Arguments.of("1 note 2","1 {note} 2"),
        Arguments.of("keynote","keynote"),
        Arguments.of("ke1note","ke1note"),
        Arguments.of("yes note1","yes note1"),
        Arguments.of("yes notea note","yes notea {note}"),
        Arguments.of("yes note note","yes {note} {note}"),
        Arguments.of("a note anote","a {note} anote")
        );
        }
```

* 테스트 결과
  ![79AD73B9-EDAF-421A-B178-C2FEC30DBA5B](https://user-images.githubusercontent.com/92678400/187623963-fe8ad4d6-b263-48ba-9082-e27ed3e49686.png)
