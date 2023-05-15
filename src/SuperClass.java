public class SuperClass {
	public CloneClass subclass;

	public SuperClass(CloneClass subclass) {
		this.subclass = subclass;
	}

	@Override
	public Object clone() {
		return new SuperClass((CloneClass) subclass.clone());
	}
}
