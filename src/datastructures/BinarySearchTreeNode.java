package datastructures;

class BinarySearchTreeNode<E extends Comparable<E>> extends Node<E> implements Comparable<BinarySearchTreeNode<E>> {
    private BinarySearchTreeNode<E> left, right;

    BinarySearchTreeNode(E data) {
        super(data);
        left = null;
        right = null;
    }

    BinarySearchTreeNode<E> getLeft() { return left; }

    void setLeft(BinarySearchTreeNode<E> left) { this.left = left; }

    BinarySearchTreeNode<E> getRight() { return right; }

    void setRight(BinarySearchTreeNode<E> right) { this.right = right; }

    boolean isLeaf() {
        return left == null && right == null;
    }

    @Override
    public String toString() {
        if (isLeaf())
            return getData().toString();

        String s = "("+getData().toString();
        if (left != null)
            s += ",L:"+left.toString();
        if (right != null)
            s += ",R:"+right.toString();

        return s+")";
    }

    @Override
    public int compareTo(BinarySearchTreeNode<E> otherNode) {
        return getData().compareTo(otherNode.getData());
    }
}
