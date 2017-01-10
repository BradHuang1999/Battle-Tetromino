package client.queue;

public class Queue<T> {
	private DoublyNode<T> head = null;
	private DoublyNode<T> tail = null;
	
	public void enqueue(T item){
		if (this.head == null){
			this.head = new DoublyNode<T> (item, this.tail, null);
		} else if (this.tail == null){
			this.tail = new DoublyNode<T> (item, null, this.head);
		} else {
			DoublyNode<T> node = new DoublyNode<T> (item, null, this.tail);
			this.tail = node;
		}
	}
	
	public T dequeue(){
		T item = this.head.getItem();
		this.head = (DoublyNode<T>)this.head.getNext();
		return item;
	}

	public void display(){
		Node<T> tempNode = head;
		System.out.println(tempNode.getItem());

		while (tempNode.getNext() != null){
			tempNode = tempNode.getNext();
			System.out.println(tempNode.getItem());
		}
	}
}
