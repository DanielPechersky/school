package datastructures;

public class QuadTreeNode<E> extends Node<E> {
    private QuadTreeNode<E> NW, NE, SW, SE;

    public QuadTreeNode(E data) {
        super(data);
    }

    QuadTreeNode<E> getNW() { return NW; }
    QuadTreeNode<E> getNE() { return NE; }
    QuadTreeNode<E> getSW() { return SW; }
    QuadTreeNode<E> getSE() { return SE; }

    void setNW(QuadTreeNode<E> node) { NW = node; }
    void setNE(QuadTreeNode<E> node) { NE = node; }
    void setSW(QuadTreeNode<E> node) { SW = node; }
    void setSE(QuadTreeNode<E> node) { SE = node; }
}
