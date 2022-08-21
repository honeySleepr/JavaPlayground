package interfaces;

public interface PracticeInterface {
	String NAME = "BC"; /* `public static final`이 자동으로 붙는다 */

	/**
	 * <h2>구현 클래스에서 `public 메서드`로 구현해야하는 메서드</h2>
	 */
	void abstractMethods();

	/**<h2>
	 * 인터페이스 내에서 구현할 수 있는 메서드. public 접근제어자가 자동으로 붙는다.<br>
	 * 구현 클래스에서 오버라이딩도 가능하다.</h2>
	 */
	default void defaultMethods(){
		System.out.println("default methods");
		privateMethods();
	}
	/**<h2>
	 * 인터페이스 내에서만 구현 및 사용 가능한 private 메서드<br>
	 * 오버라이딩 불가 (자바9에서 추가된 기능)</h2>
	 */
	private void privateMethods(){
		System.out.println(">>>>private methods");
	}

	/**
	 * <h2>
	 * 인터페이스 내에서 구현되어야하는 public static 메서드<br> 오버라이딩 불가 </h2>
	 */
	static void staticMethods(){ /*  */
		System.out.println("static methods");
	}


}
