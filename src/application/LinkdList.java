package application;

import java.util.Iterator;
import java.util.ListIterator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LinkdList<T extends Comparable<T>> implements Iterable<T> {

	Node<T> head;

	public LinkdList() {
		Node<T> dummy = new Node<>(null);
		head = dummy;
		head.setNext(dummy);
		head.setPrev(dummy);
	}

	public void addFirst(T data) {
		Node<T> nono = new Node<>(data);
		if (head.getNext() == head) {
			nono.setPrev(head);
			nono.setNext(head);
			head.setNext(nono);
			head.setPrev(nono);

		} else {
			nono.setNext(head.getNext());
			nono.setPrev(head);
			head.setNext(nono);
			head.getNext().setPrev(nono);
		}
	}

	public void addLast(T data) {
		Node<T> newNode = new Node<>(data);
		Node<T> curr = head.getNext();
		while (curr.getNext() != head)
			curr = curr.getNext();

		newNode.setNext(head);
		newNode.setPrev(curr);
		curr.setNext(newNode);

	}

	void addSort(T data) {
		Node<T> nono = new Node<>(data);

		if (head.getNext() == head) {
			nono.setPrev(head);
			nono.setNext(head);
			head.setNext(nono);
			head.setPrev(nono);
		} else if (head.getNext().getData().compareTo(data) > 0) {
			nono.setNext(head.getNext());
			nono.setPrev(head);
			head.getNext().setPrev(nono);
			head.setNext(nono);

		} else {
			Node<T> curr = head.getNext();
			while (curr.getNext() != head && curr.getData().compareTo(data) < 0) {
				curr = curr.getNext();
			}

			if (curr.getData().compareTo(data) < 0) {
				nono.setPrev(curr);
				nono.setNext(head);
				head.setPrev(nono);
				curr.setNext(nono);
			} else {
				nono.setNext(curr);
				nono.setPrev(curr.getPrev());
				curr.getPrev().setNext(nono);
				curr.setPrev(nono);
			}

		}

	}

	void delete(T target) {
		if (head.getNext() == head)
			return;

		if (head.getNext().getData().compareTo(target) == 0) {
			head.setNext(head.getNext().getNext());
			return;
		}

		Node<T> curr = head.getNext();
		while (curr.getNext() != head && curr.getData().compareTo(target) != 0) {
			curr = curr.getNext();
		}

		if (curr.getNext() != head)
			curr.getPrev().setNext(curr.getNext());
		else if (curr.getNext() == head && curr.getData().compareTo(target) == 0) // delete last
			curr.getPrev().setNext(head);
		else
			System.out.println("not element found");

	}

	ObservableList<T> linkedToOps() {
		ObservableList<T> o = FXCollections.observableArrayList();
		Node<T> curr = (Node<T>) this.head.getNext();
		while (curr != this.head) {
			o.add(curr.getData());
			curr = curr.getNext();
		}
		return o;
	}

	boolean searchSort(T target) {
		Node<T> curr = head.getNext();
		while (curr != head) {
			if (curr.getData().compareTo(target) == 0)
				return true;

			if (curr.getData().compareTo(target) > 0)
				return false;

			curr = curr.getNext();
		}
		return false;
	}

//	public T getNext(T data) {
//		if (head.getNext() == head) {
//			return null;
//		} else {
//			Node<T> curr = head.getNext();
//			while (curr.getNext() != head) {
//				if (curr.getData().compareTo(data) == 0) {
//					return curr.getNext().getData();
//				}
//				curr = curr.getNext();
//			}
//		}
//		return null;
//
//	}

	int length() {
		int size = 0;
		Node<T> curr = head.getNext();
		while (curr != head) {
			size += 1;
			curr = curr.getNext();
		}
		return size;
	}

//	boolean idHerhaling(int id) {
//		Node<T> curr = head.getNext();
//		while (curr != head) {
//			if (curr.getData()==0) {
//				return false;
//			}
//			curr = curr.getNext();
//		}
//		return true;
//	}

	String print() {
		String s = "head --> :";
		Node<T> curr = head.getNext();
		while (curr != head) {
			s += curr;
			curr = curr.getNext();

		}
		return s + "head";

	}

	@Override
	public Iterator<T> iterator() {
		return new MyListIterator() {
		};
	}

	private class MyListIterator implements Iterator<T> {
		private Node<T> curr = head.getNext();

		public boolean hasNext() {
			return curr != head;
		}

		public T next() {
			T t = curr.getData();
			curr = curr.getNext();
			return t;
		}
	}

//	public ListIterator<T> listItretor() {
//		return new ListItretor();
//	}
//
//	private class ListItretor implements ListIterator<T> {
//
//		private Node<T> curr = head.getNext();
//
//		@Override
//		public boolean hasNext() {
//			return curr != head;
//		}
//
//		@Override
//		public T next() {
//			T t = curr.data;
//			curr = curr.getNext();
//			return t;
//		}
//
//		@Override
//		public boolean hasPrevious() {
//			return curr != head;
//		}
//
//		@Override
//		public T previous() {
//			T data = curr.getData();
//			curr = curr.getPrev();
//			return data;
//		}
//
//		@Override
//		public int nextIndex() {
//			// TODO Auto-generated method stub ` `
//			return 0;
//		}
//
//		@Override
//		public int previousIndex() {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public void remove() {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void set(T e) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void add(T e) {
//			// TODO Auto-generated method stub
//
//		}
//
//	}
//
//	@Override
//	public Iterator<T> iterator() {
//		// TODO Auto-generated method stub
//		return null;
//	}
}