package game.queue;

public class DoublyNode<T> extends Node<T> {
    private Node<T> prev;

    public DoublyNode(T item, Node<T> next, Node<T> prev) {
        super(item, next);
        this.prev = prev;
        if (prev != null){
            prev.setNext(this);
        }
    }

    public Node<T> getPrev() {
        return this.prev;
    }

    public void setPrev(Node<T> prev) {
        this.prev = prev;
        prev.setNext(this);
    }
}