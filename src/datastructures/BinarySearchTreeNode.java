package datastructures;

class BinarySearchTreeNode<E extends Comparable<E>> extends Node<E> implements Comparable<BinarySearchTreeNode<E>> {
    private BinarySearchTreeNode<E> left, right;

    public BinarySearchTreeNode(E data) {
        super(data);
        left = null;
        right = null;
    }

    public BinarySearchTreeNode<E> getLeft() { return left; }

    public void setLeft(BinarySearchTreeNode<E> left) { this.left = left; }

    public BinarySearchTreeNode<E> getRight() { return right; }

    public void setRight(BinarySearchTreeNode<E> right) { this.right = right; }

    public boolean isLeaf() {
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
