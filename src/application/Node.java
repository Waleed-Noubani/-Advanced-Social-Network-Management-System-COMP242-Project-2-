package application;

public class Node<T extends Comparable<T>> {
	T data;
	private Node<T> next;
	private Node<T> prev;

	Node(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}

	public Node<T> getPrev() {
		return prev;
	}

	public void setPrev(Node<T> prev) {
		this.prev = prev;
	}

	@Override
	public String toString() {
		return " [" + data + "] -->";
	}

}
