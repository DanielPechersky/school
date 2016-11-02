import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class QuadTree<E> {
    protected final int size;
    private QuadTreeNode<E> root;

    public QuadTree(E[][] data) {
        if (data.length != data[0].length)
            throw new IllegalArgumentException("QuadTree needs an array with equal sides");
        if ((data.length&-data.length) != data[0].length || (data[0].length&-data[0].length) != data[0].length)
            throw new IllegalArgumentException("Sides must be power of 2");

        size = data.length;
        root = build(data);
    }

    protected QuadTreeNode<E> getRoot() {
        return root;
    }

    private static <E> QuadTreeNode<E> build(E[][] data) {
        return build(data, 0, data.length, 0, data[0].length);
    }

    private static <E> QuadTreeNode<E> build(E[][] data, int fromRowIndex, int toRowIndex, int fromColumnIndex, int toColumnIndex) {
        if (toRowIndex-fromRowIndex == 1)
            return new QuadTreeNode<>(data[fromRowIndex][fromColumnIndex]);

        int centerRowIndex = fromRowIndex+(toRowIndex-fromRowIndex)/2;
        int centerColumnIndex = fromColumnIndex+(toColumnIndex-fromColumnIndex)/2;

        QuadTreeNode<E> result = new QuadTreeNode<>(
                build(data, fromRowIndex, centerRowIndex, fromColumnIndex, centerColumnIndex),
                build(data, fromRowIndex, centerRowIndex, centerColumnIndex, toColumnIndex),
                build(data, centerRowIndex, toRowIndex, centerColumnIndex, toColumnIndex),
                build(data, centerRowIndex, toRowIndex, fromColumnIndex, centerColumnIndex));

        killChildrenIfNecessary(result);
        return result;
    }

    private static <E> void killChildrenIfNecessary(QuadTreeNode<E> node) {
        if (areChildrenIdentical(node))
            killChildren(node);
    }

    private static <E> boolean areChildrenIdentical(QuadTreeNode<E> node) {
        QuadTreeNode<E>[] children = node.getChildren();

        for (int i = 0; i < children.length-1; i++)
            if (!children[i].equals(children[i+1]))
                return false;
        return true;
    }

    private static <E> void killChildren(QuadTreeNode<E> node) {
        node.setData(node.getChildren()[0].getData());
        node.clearChildren();
    }

    private static void fillArray(Object[][] array, QuadTreeNode node, int size) {
        fillArray(array, node, size, 0, 0);
    }

    private static void fillArray(Object[][] array, QuadTreeNode node, int size, int fromRowIndex, int fromColumnIndex) {
        if (node.isLeaf())
            fillSquare(array, node.getData(), size, fromRowIndex, fromColumnIndex);
        else {
            QuadTreeNode[] children = node.getChildren();
            int subSquareSize = size/2;
            fillArray(array, children[0], subSquareSize, fromRowIndex, fromColumnIndex);
            fillArray(array, children[1], subSquareSize, fromRowIndex, fromColumnIndex+subSquareSize);
            fillArray(array, children[2], subSquareSize, fromRowIndex+subSquareSize, fromColumnIndex+subSquareSize);
            fillArray(array, children[3], subSquareSize, fromRowIndex+subSquareSize, fromColumnIndex);
        }
    }

    private static void fillSquare(Object[][] array, Object value, int size, int fromRowIndex, int fromColumnIndex) {
        for (int row = fromRowIndex; row < fromRowIndex+size; row++)
            Arrays.fill(array[row], fromColumnIndex, fromColumnIndex+size, value);

    }

    public Object[][] toArray() {
        Object[][] array = new Object[size][size];
        fillArray(array);
        return array;
    }

    public int getSize() { return size; }

    public void fillArray(Object[][] array) {
        fillArray(array, root, size);
    }

    public String toString() {
        try {
            return root.toString();
        } catch (NullPointerException ignored) {
            return "()";
        }
    }

    private static <E> String nodeToString(QuadTreeNode<E> node) {
        if (!node.isLeaf())
            return "null";
        return node.getData().toString();
    }

    public String toDepthString() {
        LinkedList<List<QuadTreeNode<E>>> levels = new LinkedList<>();

        List<QuadTreeNode<E>> current = new LinkedList<>();
        current.add(root);

        while (!current.isEmpty()) {
            levels.add(current);
            LinkedList<QuadTreeNode<E>> next = new LinkedList<>();
            current.stream()
                    .filter(node -> !node.isLeaf())
                    .forEach(node -> next.addAll(Arrays.asList(node.getChildren())));
            current = next;
        }

        return String.join("\n", (Iterable<String>) levels.stream()
                .map(line -> String.join(" ", (Iterable<String>) line.stream()
                        .map(QuadTree::nodeToString)::iterator))
                ::iterator);
    }
}
