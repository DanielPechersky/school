package datastructures;

abstract class Node<E> {
    private E data;

    Node(E data) { this.data = data; }

    E getData() { return data; }

    void setData(E data) { this.data = data; }
}
