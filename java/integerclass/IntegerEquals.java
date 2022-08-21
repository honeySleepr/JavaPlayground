package integerclass;

public class IntegerEquals {

    public static void main(String[] args) {
        for (int i = -200; i <= 200; i++) {
            Integer a = i;
            Integer b = i;
            System.out.println((a == b) + " a : " + a + " b1 :" + b);
        }
        /* -128~127 까지만 true 지만, 그 외에는 false가 된다 */
        /* Wrapper class 끼리는 항상 equals()로 비교하도록 주의하자 */
    }
}
