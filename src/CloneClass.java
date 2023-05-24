public class CloneClass {
	public String msg;

	CloneClass(String msg) {
		this.msg = msg;
	}

	public void printMsg() {
		System.out.println(msg);
	}

	@Override
	public Object clone() {
		return new CloneClass(this.msg);
	}
}
