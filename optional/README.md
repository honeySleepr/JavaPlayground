# Optional

> * 모든 참조타입 변수를 저장할 수 있는 Wrapper 클래스
* 예상치 못한 null 값을 걸러내는 예외 처리를 쉽게 할 수 있게 해준다.


* Optional 에는 직접적으로 null 값이 할당되면 Optional을 사용한 의미가 없다.
* ex) `Optional<User> userOpt = null`
    * 값을 이용해 Optional을 만들때는 `ofNullable(“값”)`을 사용한다
    * 빈 값을 반환할 때는 `Optional.emtpy()`를 사용한다
    * 또는 stream의 `findAny()`와 같은 메서드를 사용한다
```java
public void saveUser(Optional<User> userOpt){
User user = userOpt.orElseGet(()-> ~~~)  
// saveUser(null)와 같이 인자로 null이 들어왔다면 NPE

```

* 필드변수나 메서드/생성자의 인자 및 파라미터로는 적합하지 않다 ( Serializable를 상속받지 않고, 코드가 쓸데없이 무거워지기 때문이라고 한다. 잘 모르겠다)
* 리턴값으로만 쓰자!

* 비어있는 컬렉션을 반환해야할 경우 Optional을 사용하지 않고 Collections.emptyList() ,emptyMap() , emptySet() . 등을 사용해야 더 가벼운 코드를 만들 수 있다.


## Optional 처리 방법

#### 값이 있는 경우 / 값이 없는 경우

* 값 반환 / 다른 값 반환 :                        `orElse()`
* 값 반환 / 메서드 호출 + 다른 값 반환 : `orElseGet()`
* 값 반환 / 에러 발생 :                             `orElseThrow()`

* 값을 이용해 메서드 호출 / 아무것도 안함 : `ifPresent()`
* 값을 이용해 메서드 호출 / 다른 메서드 호출 : `ifPresentOrElse()`

* 값도 반환하고 메서드도 호출 / 다른값 반환 : `stream().map().findAny()…`

### orElse()

* 같은 타입 변수를 인자로 받는다.
* 반환값이 있는 메서드를 넣어도 되지만, Optional 값 유무에 상관없이 무조건 해당 메서드가 호출된다.

### orElseGet()

* 같은 타입을 반환하는 Supplier 메서드를 인자로 받는다 ( `()-> method();`)
* `orElse()`와 달리 값이 없을 경우에만 인자의 메서드가 호출된다

### orElseThrow()

* 인자로 Throwable 한 Supplier 메서드를 받는다.
* (`()-> new IllegalStateException(”Empty user!”)`
* 인자로 아무것도 넘기지 않으면 디폴트인 `NoSuchElementException` 을 던진다
