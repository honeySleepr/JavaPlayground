import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PracticeOptional {
    private static final List<User> users = new ArrayList<>();
    private int count;

    public static void main(String[] args) {
        PracticeOptional practiceOptional = new PracticeOptional();
        practiceOptional.runTest();
    }

    private void runTest() {
        Optional<User> userOpt = getOptionalUser("OPT");
        Optional<User> ofNullableOpt = Optional.ofNullable(null);
        Optional<User> emptyOpt = Optional.empty();
        User userBC = new User("BC");

        /* 값 반환 / 다른 값 반환 */
        User userOptTest1 = userOpt.orElse(createUser("bc"));
        User userOptTest2 = userOpt.orElse(new User()); // 1,2 처럼 쓰는건 nono
        User userOptTest3 = userOpt.orElse(userBC);

        /* 값 반환 / 메서드 호출 + 다른 값 반환 */
        User userOptTest4 = userOpt.orElseGet(() -> new User("bc"));
        User userOptTest5 = userOpt.orElseGet(() -> createUser("bc"));
        User userOptTest6 = userOpt.orElseGet(() -> {
            increaseErrorCount();
            System.out.println("다른 메서드 호출 가능");
            return userBC;
        });
        // User userOptTest7 = userOpt.orElseGet(() -> new String()); // 6,7 : User 객체를 반환하지 않는 메서드는 안됨
        // User userOptTest8 = userOpt.orElseGet(() -> System.out.println("프린트!"));


        /* 값 반환 / 에러 발생 (+ 기타 메서드 실행 가능) */
        User userOptTest9 = userOpt.orElseThrow(() -> new IllegalStateException("Empty user!"));
        User userOptTest10 = emptyOpt.orElseThrow(() -> {
            increaseErrorCount();
            new User("bc"); //의미는 없지만..호출 가능
            throw new IllegalStateException();
        });
        // User userOptTest11 = userOpt.orElseThrow(() -> new User("bc", "0")); //throwable 상속받는 객체만 반환 가능

        /* 값을 이용해 메서드 호출 / 아무것도 안함 */
        userOpt.ifPresent(user -> {
            System.out.println("user 저장 : " + user.getName());
            addUser(user);
        });

        /* 값을 이용해 메서드 호출 / 다른 메서드 호출 */
        userOpt.ifPresentOrElse((user) -> {
            System.out.println("유저 저장");
            addUser(user);
        }, () -> {
            User userDummy = createUser("dummy");
            addUser(userDummy);
        });

        /* 값도 반환하고 메서드도 호출 / 다른값 반환 */
        String userName = userOpt.stream().map(user -> {
            System.out.println(user.getName());
            increaseErrorCount();
            return user.getName();
        }).findAny().orElse("없다~");
    }

    private User createUser(String name) {
        System.out.println("--------orELse---------");
        return new User(name);
    }

    private Optional<User> getOptionalUser(String name) {
        return Optional.ofNullable(new User(name));
    }

    private void increaseErrorCount() {
        System.out.println(count + 1);
        this.count++;
    }

    private void addUser(User user) {
        users.add(user);
    }

}

class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public User() {
    }

    public String getName() {
        return name;
    }
}
