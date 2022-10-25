### 요약

**Wrapper 타입**

- `List.copyOf()` 또는 `new ArrayList<>(원본 List)`와 같은 **생성자**를 이용하면 deep copy가 된다.

**Reference(객체) 타입**

- `List.copyOf()` 또는 생성자를 이용하면, Collection 자체는 원본과 독립적인 Collection이 된다. 즉 원본에서 remove(1)을 하더라도, 복사된 Collection에서는 remove
  되지 않는다 ( 여기까지는 Wrapper 타입과 같다 )
- 하.지.만. 각 Collection에 저장된 인스턴스들이 참조하고 있는 주소는 모두 같기 때문에, collection 안에 저장된 인스턴스의 상태를 변경하면, 다른 collection에서도 변경이 적용된다.

- 그렇기 때문에 객체 타입 Collection에서 완벽한 Deep Copy를 하려면 객체 자체도 Deep Copy 해줘야한다.
- 이 경우 해당 객체가 Cloneable을 구현하게 하여 `clone()`을 재정의하는 것 보다는, 객체 내에서 **복사 생성자** 또는 **복사 팩토리 메서드**를 만들어 사용하는 것이 더 권장된다고 한다.

### 참고

[https://github.com/Meet-Coder-Study/book-effective-java](https://github.com/Meet-Coder-Study/book-effective-java/blob/main/3%EC%9E%A5/13_clone_%EC%9E%AC%EC%A0%95%EC%9D%98%EB%8A%94_%EC%A3%BC%EC%9D%98%ED%95%B4%EC%84%9C_%EC%A7%84%ED%96%89%ED%95%98%EB%9D%BC_%EB%B0%95%EC%B0%BD%EC%9B%90.md)  
https://zzang9ha.tistory.com/372
