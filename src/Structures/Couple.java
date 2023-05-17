package Structures;

public class Couple<E, T> {
	public E first;
	public T second;

	public Couple(E first, T second) {
		this.first = first;
		this.second = second;
	}

	public void setFirst(E first) {
		this.first = first;
	}

	public void setSecond(T second) {
		this.second = second;
	}
}
