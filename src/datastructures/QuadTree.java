package datastructures;

public class QuadTree<E> {
    private QuadTreeNode<E> root;

    public QuadTree(E[][] data) {
        root = build(data);
    }

    private QuadTreeNode<E> build(E[][] data) {
        return build(data, 0, data.length, 0, data[0].length);
    }

    private QuadTreeNode<E> build(E[][] data, int fromRowIndex, int toRowIndex, int fromColumnIndex, int toColumnIndex) {
        if (toRowIndex-fromRowIndex == 1)
            return new QuadTreeNode<>(data[fromRowIndex][fromColumnIndex]);

        int centerRowIndex = fromRowIndex+(toRowIndex-fromRowIndex)/2;
        int centerColumnIndex = fromColumnIndex+(toColumnIndex-fromColumnIndex)/2;

        return new QuadTreeNode<>(
                build(data, fromRowIndex, centerRowIndex, fromColumnIndex, centerColumnIndex),
                build(data, fromRowIndex, centerRowIndex, centerColumnIndex, toColumnIndex),
                build(data, centerRowIndex, toRowIndex, centerColumnIndex, toColumnIndex),
                build(data, centerRowIndex, toRowIndex, fromColumnIndex, centerColumnIndex));
    }

    public String toString() {
        try {
            return root.toString();
        } catch (NullPointerException ignored) {
            return "()";
        }
    }
}
