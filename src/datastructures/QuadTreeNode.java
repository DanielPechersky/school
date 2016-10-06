package datastructures;

import java.util.Arrays;

public class QuadTreeNode<E> extends Node<E> {
    private QuadTreeNode<E>[] children;

    QuadTreeNode(E data) {
        super(data);
        instantiateChildrenArray();
    }

    QuadTreeNode(QuadTreeNode<E> NW, QuadTreeNode<E> NE, QuadTreeNode<E> SE, QuadTreeNode<E> SW) {
        super(null);
        instantiateChildrenArray();
        setChildren(NW, NE, SE, SW);
    }

    QuadTreeNode<E>[] getChildren() {
        return children.clone();
    }

    void setChildren(QuadTreeNode<E> NW, QuadTreeNode<E> NE, QuadTreeNode<E> SE, QuadTreeNode<E> SW) {
        children[0] = NW;
        children[1] = NE;
        children[2] = SE;
        children[3] = SW;
    }

    @SuppressWarnings("unchecked")
    private void instantiateChildrenArray() {
        children = (QuadTreeNode<E>[]) new QuadTreeNode[4];
        Arrays.fill(children, null);
    }

    boolean isLeaf() {
        return getData() != null;
    }

    boolean equals(QuadTreeNode<E> node) {
        return isLeaf() && node.isLeaf() && getData().equals(node.getData());
    }

    @Override
    public String toString() {
        if (isLeaf())
            return getData().toString();
        return String.format("(%s,%s,%s,%s)", children[0].toString(), children[1].toString(), children[2].toString(), children[3].toString());
    }
}
