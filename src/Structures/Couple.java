package Structures;

public class Couple<E, T> {
	public E first;
	public T second;

	public Couple(E first, T second) {
		this.first = first;
		this.second = second;
	}

	public String toString() {
		return "(" + this.first + ", " + this.second + ")";
	}
}
