* 예제 코드

```java
for(int i=-200;i<=200;i++){
        Integer a=i;
        Integer b=i;
        System.out.println((a==b)+" a : "+a+" b1 :"+b);
        }
```

**결과** : -128~127 범위에서는 true이지만, 그 외에는 false가 된다

> `Integer a = 1;` 형태로 선언하는건 `Integer a = Integer.valueOf(1)` 과 같다  
> 이때 `valueOf()` 는 -128 부터 127 까지는 캐시된 값을 반환하고,
> 이 범위를 벗어나면 새로운 Integer 인스턴스를 만들어 반환한다.  

Integer 간의 `==`비교가 어쩔 때는 제대로 되고, 어쩔 땐 안되는 이유가 이거였다  

* **valueOf()** 코드 참고

```java
public static Integer valueOf(int i){
        if(i>=IntegerCache.low&&i<=IntegerCache.high)
        return IntegerCache.cache[i+(-IntegerCache.low)];
        return new Integer(i);
        }
```


* Wrapper Class 끼리는 `equals()`를 사용하는게 당연하지만,`Integer 끼리 비교` 하는 경우를 `Integer와 int를 비교`할 때와 혼동하는 바람에 실수를 하게되었다.

> 결론: Integer Class 끼리의 비교는 항상 `equals()`를 사용하자
