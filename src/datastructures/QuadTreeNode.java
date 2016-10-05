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
        instantiateChildrenArray(NW, NE, SE, SW);
        killChildrenIfNecessary();
    }

    QuadTreeNode<E>[] getChildren() {
        return children.clone();
    }

    @SuppressWarnings("unchecked")
    private void instantiateChildrenArray() {
        children = (QuadTreeNode<E>[]) new QuadTreeNode[4];
        Arrays.fill(children, null);
    }

    @SuppressWarnings("unchecked")
    private void instantiateChildrenArray(QuadTreeNode<E> NW, QuadTreeNode<E> NE, QuadTreeNode<E> SE, QuadTreeNode<E> SW) {
        children = (QuadTreeNode<E>[]) new QuadTreeNode[]{NW, NE, SE, SW};
    }

    void killChildrenIfNecessary() {
        if (areChildrenIdentical())
            killChildren();
    }

    boolean areChildrenIdentical() {
        for (int i = 0; i < children.length-1; i++)
            if (!children[i].equals(children[i+1]))
                return false;
        return true;
    }

    private void killChildren() {
        setData(children[0].getData());
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
        return String.format("([%s,%s][%s,%s])", children[0].toString(), children[1].toString(), children[3].toString(), children[2].toString());
    }
}
