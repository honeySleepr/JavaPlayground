package interfaces;

public class MainClass implements PracticeInterface {

	public static void main(String[] args) {
		PracticeInterface m = new MainClass();
		System.out.println(NAME);
		m.abstractMethods();
		m.defaultMethods(); // 내부에서 private 메서드 실행
		PracticeInterface.staticMethods();
	}

	@Override
	public void abstractMethods() {
		System.out.println("abstract method");
	}

	@Override
	public void defaultMethods() {
		PracticeInterface.super.defaultMethods();
	}
}
