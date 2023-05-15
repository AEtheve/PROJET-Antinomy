public class CloneTest {
	public static void main(String[] args) {
		SuperClass s1 = new SuperClass(new CloneClass("This is the original subclass msg"));
		SuperClass s2 = (SuperClass) s1.clone();

		s2.subclass.msg = "This is the new msg";

		s1.subclass.printMsg();
		s2.subclass.printMsg();
	}
}
