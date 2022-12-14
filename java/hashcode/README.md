## hashCode()와 equals()
> 두 객체를 비교할 때 사용되는 메서드들

### 두 객체를 비교하는 과정
1. 우선 `hashCode()` 를 통해 **hash**값이 일치하는지 확인한다
    * 해쉬값이 **다르면** 무조건 다른 객체로 판단된다
    * 해쉬값이 **같으면** equals()를 통해 추가적인 판별을 하게된다
        * 즉 **hash값이 다르면 무조건 서로 다른 객체로 판별되고, hash값이 같으면 다를 수도 있고 같을 수도 있다**
2. 1에서 hash값이 달랐다면, `equals()`에서 정의된 로직에 따라 일치 여부를 추가적으로 판별하게된다.
3. **hashcode()와 equals()의 결과가 모두 일치해야 같은 객체로 판별된다**

> hashcode를 오버라이딩 해주지 않으면 우리가 같은 객체라고 판단하는 객체들이라도 서로 해쉬값이 다르게 된다  
> 
예를 들어 아래와 같은 클래스가 있을 때
```java
class hashcode.Point{
    private int a;
    private int b;
}
```  
우리는 `hashcode.Point p1 = hashcode.Point(1,1)` 와  `hashcode.Point p2 = hashcode.Point(1,1)` 는 상태(a,b)가 같기 때문에 서로  같다고 판단되기를 원한다고 하자

### hashCode()를 오버라이딩 해주지 않으면?
**객체의 주소값 만으로 해쉬를 만든다**. 그렇기 때문에 객체의 상태(a,b)가 같더라도 해쉬값은 서로 다르게 된다. 그러면 HashSet에 넣게될 경우에 중복으로 걸러지지 않으며,
p1과 p2를 HashMap의 Key로 써도 같은 Key로 인식되지 않는다.
즉, 위에 1단계에서 서로 다른 객체로 판별되어 equals() 까지 도달하지도 못한다

### equals()를 오버라이딩 해주지 않으면?
**객체의 주소값으로만 비교한다** (`p1 == p2`). 그래서 hashCode()를 오버라이딩 하여 상태(a,b)가 같은 두 객체 p1, p2의 해쉬값이 같아졌더라도, equals() 검증 단계에서는 두 주소값이 달라 서로 다른 객체로 판별되어버린다. 우리가 원하는건 그게 아니기 때문에 equals()에서 상태(a,b)가 같으면 true를 반환하도록 오버라이딩 해야한다

> 각 메서드의 오버라이딩은 IntelliJ의 오버라이딩 기능(`Control(^) + O`)을 사용하면 쉽게 할 수 있다
