package datastructures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BinarySearchTree<E extends Comparable<E>> {
    private BinarySearchTreeNode<E> root;

    public BinarySearchTree() { root = null; }

    public BinarySearchTree(Iterable<E> data) {
        this();
        insert(data);
    }

    private static <E extends Comparable<E>> void insert(Iterable<BinarySearchTreeNode<E>> toInsert, BinarySearchTreeNode<E> insertIn) {
        for (BinarySearchTreeNode<E> node : toInsert)
            insert(node, insertIn);
    }

    private static <E extends Comparable<E>> void insert(BinarySearchTreeNode<E> toInsert, BinarySearchTreeNode<E> insertIn) {
        if (toInsert.compareTo(insertIn) == -1)
            if (insertIn.getLeft() != null)
                insert(toInsert, insertIn.getLeft());
            else
                insertIn.setLeft(toInsert);
        else if (insertIn.getRight() != null)
            insert(toInsert, insertIn.getRight());
        else
            insertIn.setRight(toInsert);
    }

    private static <E extends Comparable<E>> LinkedList<BinarySearchTreeNode<E>> getSortedNodeList(BinarySearchTreeNode<E> node) {
        LinkedList<BinarySearchTreeNode<E>> sortedList = new LinkedList<>();
        if (node.getLeft() != null)
            sortedList.addAll(getSortedNodeList(node.getLeft()));
        sortedList.add(new BinarySearchTreeNode<>(node.getData()));
        if (node.getRight() != null)
            sortedList.addAll(getSortedNodeList(node.getRight()));
        return sortedList;
    }

    private static <E extends Comparable<E>> LinkedList<E> getSortedList(BinarySearchTreeNode<E> node) {
        LinkedList<E> sortedList = new LinkedList<>();
        if (node.getLeft() != null)
            sortedList.addAll(getSortedList(node.getLeft()));
        sortedList.add(node.getData());
        if (node.getRight() != null)
            sortedList.addAll(getSortedList(node.getRight()));
        return sortedList;
    }

    private static <E extends Comparable<E>> BinarySearchTreeNode<E> getParent(E childData, BinarySearchTreeNode<E> searchIn) {
        if (childData.compareTo(searchIn.getData()) == -1) {
            if (searchIn.getLeft() == null)
                return null;
            else if (!searchIn.getLeft().getData().equals(childData))
                return getParent(childData, searchIn.getLeft());
        } else if (searchIn.getRight() == null)
            return null;
        else if (!searchIn.getRight().getData().equals(childData))
            return getParent(childData, searchIn.getRight());
        return searchIn;
    }

    public void insert(Iterable<E> data) {
        for (E d : data)
            insertWithoutBalancing(d);
        balance();
    }

    public void insert(E data) {
        insertWithoutBalancing(data);
        balance();
    }

    private void insertWithoutBalancing(E data) {
        BinarySearchTreeNode<E> toInsert = new BinarySearchTreeNode<>(data);
        if (root != null)
            insert(toInsert, root);
        else
            root = toInsert;
    }

    public LinkedList<E> getSortedList() {
        if (root != null)
            return getSortedList(root);
        return null;
    }

    public void delete(Iterable<E> data) {
        for (E d : data)
            deleteWithoutBalancing(d);
        balance();
    }

    public void delete(E data) {
        deleteWithoutBalancing(data);
        balance();
    }

    private void deleteWithoutBalancing(E data) {
        if (root != null) {
            if (root.getData().equals(data)) {
                BinarySearchTreeNode<E> toDelete = root;

                if (root.getLeft() != null) {
                    root = toDelete.getLeft();
                    if (root.getRight() != null)
                        insert(getSortedNodeList(toDelete.getRight()), root);

                } else if (root.getRight() != null)
                    root = toDelete.getRight();
                else
                    root = null;
            } else {
                BinarySearchTreeNode<E> parent = getParent(data, root);

                if (data.compareTo(parent.getData()) == -1) {
                    BinarySearchTreeNode<E> toDelete = parent.getLeft();
                    if (toDelete.getLeft() != null) {
                        parent.setLeft(toDelete.getLeft());
                        if (toDelete.getRight() != null)
                            insert(getSortedNodeList(toDelete.getRight()), parent.getLeft());
                    } else if (toDelete.getRight() != null)
                        parent.setLeft(toDelete.getRight());
                    else
                        parent.setLeft(null);
                } else {
                    BinarySearchTreeNode<E> toDelete = parent.getRight();
                    if (toDelete.getLeft() != null) {
                        parent.setRight(toDelete.getLeft());
                        if (toDelete.getRight() != null)
                            insert(getSortedNodeList(toDelete.getRight()), parent.getRight());
                    } else if (toDelete.getRight() != null)
                        parent.setRight(toDelete.getRight());
                    else
                        parent.setRight(null);
                }
            }
        }
    }

    // TODO:
    public void balance() {
        // get center, split list, get centers, split lists, stop splitting if lists are length 1
        if (root != null) {
            ArrayList<BinarySearchTreeNode<E>> list = new ArrayList<>(getSortedNodeList(root));
            root = list.remove(list.size()/2);

            Queue<ArrayList<BinarySearchTreeNode<E>>> splitLists = new LinkedList<>();

            if (list.size() > 0) {
                splitLists.add(new ArrayList<>(list.subList(list.size()/2, list.size())));

                if (list.size() > 1)
                    splitLists.add(new ArrayList<>(list.subList(0, list.size()/2)));
            }

            while (splitLists.size() > 0) {
                list = splitLists.remove();
                insert(list.remove(list.size()/2), root);

                if (list.size() <= 2) {
                    insert(list, root);
                } else {
                    splitLists.add(new ArrayList<>(list.subList(0, list.size()/2+1)));
                    splitLists.add(new ArrayList<>(list.subList(list.size()/2+1, list.size())));
                }

            }
        }
    }

    @Override
    public String toString() {
        try {
            return root.toString();
        } catch (NullPointerException e) {
            return "()";
        }
    }
}
