package deepcopy;

import java.util.LinkedList;
import java.util.List;

/**
 * 참고 : <a href="https://zzang9ha.tistory.com/372">https://zzang9ha.tistory.com/372</a>
 */
public class PracticeDeepCopy {

	public static void main(String[] args) {
		/* shallow copy (Wrapper type) */
		List<Integer> originList1 = new LinkedList<>();
		originList1.add(0);
		originList1.add(1);
		originList1.add(2);
		List<Integer> list2 = originList1;

		originList1.remove(1);

		System.out.println("\nShallow copy (Wrapper type)");
		System.out.println("originList1");
		for (Integer integer : originList1) {
			System.out.println(integer);
		}
		System.out.println("=========");
		System.out.println("list2");
		for (Integer integer : list2) {
			System.out.println(integer);
		}

		/* deep copy (Wrapper type) */
		List<Integer> originList2 = new LinkedList<>();
		originList2.add(0);
		originList2.add(1);
		originList2.add(2);
		List<Integer> list4 = List.copyOf(originList2);
		List<Integer> list5 = new LinkedList<>(originList2);

		originList2.remove(1);

		System.out.println("\nDeep copy (Wrapper type)");
		System.out.println("originList2");
		for (Integer integer : originList2) {
			System.out.println(integer);
		}
		System.out.println("=========");
		System.out.println("list4 - List.copyOf");
		for (Integer integer : list4) {
			System.out.println(integer);
		}
		System.out.println("=========");
		System.out.println("list5 - new LinkedList<>(originList2) ");
		for (Integer integer : list5) {
			System.out.println(integer);
		}

		/* deep copy (Reference type) */
		List<BC> originList3 = new LinkedList<>();
		originList3.add(new BC("0"));
		originList3.add(new BC("1"));
		originList3.add(new BC("2"));
		List<BC> list6 = List.copyOf(originList3);
		List<BC> list7 = new LinkedList<>(originList3);
		List<BC> list8 = new LinkedList<>();
		for (BC bc : originList3) {
			list8.add(new BC(bc));
		}

		originList3.remove(1);
		originList3.get(0).setName("bcbcbc");

		System.out.println("\nDeep copy 객체 타입 (Refrence type)");
		System.out.println("originList3");
		for (BC bc : originList3) {
			System.out.println(bc);
		}
		System.out.println("=========");
		System.out.println("list6 - List.copyOf(originList3)");
		for (BC bc : list6) {
			System.out.println(bc);
		}
		System.out.println("=========");
		System.out.println("list7 - new LinkedList<>(originList3)");
		for (BC bc : list7) {
			System.out.println(bc);
		}
		System.out.println("=========");
		System.out.println("list8 - list8.add(new BC(bc)) (복사 생성자 활용)");
		for (BC bc : list8) {
			System.out.println(bc);
		}
	}

	static class BC {

		String name;

		public BC(String name) {
			this.name = name;
		}

		/* 복사 생성자 */
		public BC(BC bc) {
			this.name = bc.name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "BC :" + name;
		}
	}

}
