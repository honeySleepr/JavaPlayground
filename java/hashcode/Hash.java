package hashcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Hash {

	public static void main(String[] args) {
		HashSet<Point> set = new HashSet<>();
		Point p = new Point(1, 1);
		Point p2 = new Point(1, 1);
		System.out.println(p == p2);
		System.out.println(p.equals(p2));
		System.out.println(p == p);
		System.out.println(p.equals(p));
		System.out.println("/*------------------*/");

		set.add(p);
		set.add(new Point(1, 1));
		set.add(new Point(2, 1));
		set.add(new Point(1, 1));
		set.add(p);
		set.forEach(System.out::println);
		System.out.println("/*------------------*/");
		HashMap<Point, Integer> map = new HashMap<>();

		map.put(new Point(1, 1), 100);
		map.put(new Point(2, 1), 100);
		map.put(new Point(1, 1), 100);

		System.out.println(map.entrySet());
	}
}

class Point {

	private int a;
	private int b;

	public Point(int a, int b) {
		this.a = a;
		this.b = b;
		System.out.println("hashcode.Point hash : " + this.hashCode());
		System.out.println(
			Integer.toHexString(System.identityHashCode(this))); // 객체 교유의 값을 출력(hashCode를 오버라이딩 해도 변하지 않음)
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			System.out.println("this == o");
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Point point = (Point) o;
		return a == point.a && b == point.b;
	}

	@Override
	public int hashCode() {
		return Objects.hash(a, b);
	}

		@Override
		public String toString() {
			return "hashcode.Point{" +
				   "a=" + a +
				   ", b=" + b +
				   '}';
		}
}
