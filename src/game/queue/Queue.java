package game.queue;

public class Queue<T> {
	private DoublyNode<T> head = null;
	private DoublyNode<T> tail = null;
	
	public void enqueue(T item){
		if (this.head == null){
			this.head = new DoublyNode<> (item, this.tail, null);
		} else if (this.tail == null){
			this.tail = new DoublyNode<> (item, null, this.head);
		} else {
			DoublyNode<T> node = new DoublyNode<> (item, null, this.tail);
			this.tail = node;
		}
	}
	
	public T dequeue(){
		T item = this.head.getItem();
		this.head = (DoublyNode<T>)this.head.getNext();
		return item;
	}

    public Object[] peek(int amount){
		T[] items = (T[])new Object[amount];
		DoublyNode<T> node = this.head;
		for (int i = 0; i < amount; i++){
			items[i] = node.getItem();
			node = (DoublyNode<T>)node.getNext();
		}
		return items;
	}
}