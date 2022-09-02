## Stream

> 넘 멋진 것
> [프로그래머스 알고리즘 문제](https://school.programmers.co.kr/learn/courses/30/lessons/42579) 를 아래와 같이 풀어버릴 수 있다

![0F25C160-0811-4C8D-A29A-7B445C4A190F](https://user-images.githubusercontent.com/92678400/186892989-b3577f26-eb6a-42fe-9465-c0098294e840.png)

```java
int[]arr=IntStream.range(0,genres.length) //1
        .mapToObj(i->new Song2(genres[i],i,plays[i])) //2
        .collect(Collectors.groupingBy(song2->song2.genre))//3
        .values().stream()//4
        .sorted((o1,o2)->-(playsSum(o1)-playsSum(o2)))//5
        .map(songList->songList.stream().sorted((o1,o2)->-(o1.plays-o2.plays)).limit(2))//6
        .flatMapToInt(songStream->songStream.mapToInt(song->song.id))//7
        .toArray();

```

1. IntStream을 활용한 for문
2. Song 인스턴스들로 구성된 Stream 생성
3. [KEY : 장르(String)를, VALUE : List<Song>] 인 map 생성
4. 각 장르에 대한 List를 꺼낸다
5. 요소들의 plays의 총합이 가장 큰 List가 앞에 오도록 정렬한다
6. 각 List의 요소들도 plays 기준으로 내림차순 정렬 후, 앞에서부터 2개만 남긴다
7. flatMap 역할 : Stream<IntStream> -> IntStream

### IntStream

> for문을 대체할 수 있다

```java
IntStream.range(0,a.length).map(i->a[i]*b[i]).sum()
```

### collect()

> 활용 방법이 굉장히 많다. 꾸준히 이것저것 해보는 중
> [Guide to Java 8 Collectors | Baeldung](https://www.baeldung.com/java-8-collectors)

<br>

#### collect() (인자 3개)

> ArrayList와 같은 컨테이너(?)를 stream 내부에서 생성하여 바로 요소들을 집어넣을 수 있는 최종연산

![04FF2AF2-1E31-4BFC-A1AC-F40308C42583](https://user-images.githubusercontent.com/92678400/186893132-35144d5f-9f51-4e3f-90c6-26580216189d.png)

```java
String string=s.chars()
        .boxed()
        .sorted(Comparator.reverseOrder())
        .collect(StringBuilder::new,StringBuilder::appendCodePoint,StringBuilder::append)
        .toString();
```

* **Supplier 인자** : StringBuilder를 생성한다
* **첫번째 BiConsumer 인자** : Supplier에서 만든 컨테이너와 stream의 요소를 입력받아 어떻게 처리할지 정의한다저걸 풀어
  쓰면 `(sb, integer) -> sb.append(integer)` 와 같다
* **두번째 BiConsumer 인자** :  이전 BiConsumer에서 만들어진 결과들을 어떻게 합칠지 정의한다. `(sb, sb2) -> sb.append(sb2)` 와 같다

> 만약 StringBuilder 대신 예를 들어 ArrayList를 쓴다면
> `ArrayList::new`, `ArrayList::add`, `ArrayList::addAll` 가 된다

<br>

#### Collectors.collectingAndThen()

> Collectors를 이용해 Collection으로 만든 다음에 그 컬렉션에 대고 바로 작업을 수행할 수 있다.
> 예를 들어 `Set A = ~~Collections.toSet()` 으로 받고 `A.다음메서드` 할 필요 없이 한방에 처리

```java
Integer size=Arrays.stream(nums)
        .boxed() // IntStream(원시타입) -> Stream<Integer>(참조타입)
        .collect(Collectors.collectingAndThen(Collectors.toSet(),set.PracticeSet->{return Math.min(set.PracticeSet.size(),nums.length/2);
        }));
```

<br>

#### Collectors.toMap()

> Collection을 **Map**으로 변환해준다

```java
List<Integer> list=new ArrayList<>(List.of(5,2,3,4,5));

        Map<Integer, Integer> collect1=list.stream()
        .collect(Collectors.toMap(i->i,i->i*100));
        // 키 중복(=5)이 있어서 IllegalStateException!

        Map<Integer, Integer> collect2=list.stream()
        .collect(Collectors.toMap(i->i,i->i*100,(v1,v2)->v1*100+v2));
        // [2=200, 3=300, 4=400, 5=50500]

        Map<Integer, Integer> collect3=list.stream()
        .collect(Collectors.toMap(i->i,i->i*100,(v1,v2)->v1*100+v2,LinkedHashMap::new));
// [5=50500, 2=200, 3=300, 4=400]
```

**toMap() 의 인자는 최소 2개, 최대 4개이다**

* toMap(1,2)
* toMap(1,2,3)
* toMap(1,2,3,4)

1. **Key를 결정하는 Function**
   stream의 요소를 인자로 사용하여 반환값을 결정한다.
   * `i -> i` 형태로 그대로 자기 자신을 출력해도 되고 (Function.identity()도 이와 같은 역할을 한다)
   * `i -> i.getname()` 형태로도 쓸 수 있고
   * `i -> Boolean.TRUE` 처럼 입력 인자와 상관 없는 값을 반환해도 된다


2. **Value 를 결정하는 Function**
   1번과 마찬가지로 입력 인자를 받아 반환값을 지정해주면 된다

3. **만약 Key 중복이 일어날 경우 처리하는 BinaryOperator**
   BinaryOperator는 BiFunction의 하위타입 인터페이스이다 즉, 입력값 두 개를 넣고 반환값 하나를 받는 function인 것이다
   * `(a,b) -> a` : key A에 대해 value가 a(원래 있던 값), b(나중에 들어온 값) 두개가 입력되어 중복이 발생했을 때 어떻게 처리할지를 정의해주면 된다 이 경우엔 이후 값은 안 쓰고 기존
     값을 그대로 value로 유지하겠다는 것

4. **반환할 Map 구현체를 정의하는 Supplier**
   네번째 인자를 생략할 경우 default로 **HashMap**이 생성된다
   * 입력 순서를 유지하고 싶다면 `LinkedHashMap::new`
   * 동시성 이슈를 해결하고자 한다면 `ConcurrentHashMap::new`
   * 데이터를 정렬시키고 싶다면 `TreeMap::new`
     등등

![F17A2624-FFD0-475E-8922-AB685D70D48E](https://user-images.githubusercontent.com/92678400/186893167-e9505eb4-6cea-4827-9b60-e125a461a6b6.png)

<br>

#### Collectors.groupingBy()

> Collection을 Map으로 변환해주는데, **toMap()과의 차이점은 Value 로 새로운 Collector를 넣을 수 있다**는 점이다  
> (예: `Map<Integer, List<String>>` 형태의 결과를 만들 수 있다)

**groupingBy() 의 인자는 최소 1개, 최대 3개이다**

* groupingBy(1)
* groupingBy(1,2)
* groupingBy(1,**3**,2)

1. **Key를 결정하는 Function**
   stream의 요소를 인자로 받아서 반환값을 map의 Key로 사용한다(위의 `toMap()`에서와 같다)

> groupingBy 인자를 하나만 주면, value는 `List<stream의 요소>` 가 된다

2. **Value를 결정하는 Collector**
   value들을 어떤식으로 묶어줄건지 결정한다.

* 예를 들어 `Collectors.toList()`로 하면 Value는 List로 만든다는건데, 어떤 값들을 value로 쓸지 지정해주지 않았으므로 위의 예시와 결과가 같다.
* stream의 요소들을 그대로 value로 쓸게 아니라 변형할거라면 `Collectors.mapping()`을 사용한다
  ex: `Collectors.mapping(strings -> strings[0], Collectors.toList())`
* `Collectors.counting()`은 stream에 각 Key의 개수를 Value로 반환한다(Long 타입)

3. **반환할 Map 구현체를 정의하는 Supplier**
   toMap()과 마찬가지로 생략 시 HashMap::new가 선언된다

#### toMap()과 groupBy()

* `Map<String, Integer>`와 같이 Value가 일반 Wrapper 타입이라면 **toMap()** 을 사용한다
* `Map<String, List<Integer>>` 와 같이 Value에 List, Set 등이 들어가는 경우에는 **groupBy()**를 사용한다

### reduce()

> 숫자로 이루어진 Stream의 값들을 이용해 계산한 값을 구한다
`reduce(indentity,(x,y) -> 연산)`
`reduce((x,y) -> 연산)`

**연산 방식**

1. identity 값과 stream의 첫번째 값을 각각 `x`,`y`로 두고 연산을 시작한다.
   * identity가 주어지지 않은 경우, stream의 첫번째 값을 `x`, 다음 값을 `y`로 두고 연산한다
3. **1**의 결과를 `x`, stream의 두번째 값을 `y`로 두고 또 연산을 한다
4. 이렇게 이전 연산 결과를 `x`, stream의 다음 값을 `y`로 두고, stream 마지막 값까지 연산을 반복하여 답을 구한다

> identity를 쓰는 이유는, `reduce(1,(x,y) -> x * (y+1))` 를 예로 들면 이해가 된다. 저 연산을 통해 우리가 얻고자 하는 것은, stream의 모든 요소들에 +1을 한 값들의 곱을 구한다는 뜻이다.
> 예를 들어 {3,2,1}이 있을 때, 위의 연산을 적용하면 `(3+1)*(2+1)*(1+1) = 4*3*2 = 24` 가 된다. 첫 값인 3에 대해서도 3이 아닌 3+1이 적용된다   
> 반면에 identity가 인자에 포함되지 않은 `reduce((x,y) -> x * (y+1)`의 결과는 어떨까? `3*(2+1)*(1+1) = 18` 이다. 즉, 첫째 항인 3에 대해서는 우리가 원하는 연산이 적용되지 않는다

##### 그런데 reduce() 연산을 복잡하게 하는 것 보다 map()을 이용해서 값을 원하는 걸로 바꿔놓고 reduce()는 간단하게 작성하는게 가독성이 더 좋은 것 같다
