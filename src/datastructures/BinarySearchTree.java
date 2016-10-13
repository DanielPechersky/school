package datastructures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BinarySearchTree<E extends Comparable<E>> {
    private BinarySearchTreeNode<E> root;

    public BinarySearchTree() { root = null; }

    public BinarySearchTree(Iterable<E> data) {
        this();
        insert(data);
    }

    // get a sorted list of data from a node and all it's children
    private static <E extends Comparable<E>> LinkedList<E> getSortedList(BinarySearchTreeNode<E> node) {
        LinkedList<E> sortedList = new LinkedList<>();
        if (node.getLeft() != null)
            sortedList.addAll(getSortedList(node.getLeft()));
        sortedList.add(node.getData());
        if (node.getRight() != null)
            sortedList.addAll(getSortedList(node.getRight()));
        return sortedList;
    }

    // get the node with data passed in
    private BinarySearchTreeNode<E> findNode(E data) {
        BinarySearchTreeNode<E> current = root;

        while (current != null && !current.getData().equals(data)) {
            if (data.compareTo(current.getData()) == -1)
                current = current.getLeft();
            else
                current = current.getRight();
        }
        return current;
    }

    // finds the parent that the node should be a child of
    private BinarySearchTreeNode<E> findPlace(E data) {
        BinarySearchTreeNode<E> current = null;
        BinarySearchTreeNode<E> next = root;

        while (next != null) {
            current = next;
            if (data.compareTo(current.getData()) == -1)
                next = current.getLeft();
            else
                next = current.getRight();

        }
        return current;
    }

    public void insert(Iterable<E> data) {
        insertWithoutBalancing(data);
        balance();
    }

    public void insert(E data) {
        insertWithoutBalancing(data);
        balance();
    }

    private void insertWithoutBalancing(Iterable<E> data) {
        for (E d : data)
            insertWithoutBalancing(d);
    }

    private void insertWithoutBalancing(E data) {
        BinarySearchTreeNode<E> toInsert = new BinarySearchTreeNode<>(data);
        if (root != null) {
            BinarySearchTreeNode<E> parent = findPlace(data);

            if (toInsert.compareTo(parent) == -1)
                parent.setLeft(toInsert);
            else
                parent.setRight(toInsert);
        } else
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
            // if root is to be deleted
            BinarySearchTreeNode<E> toDelete;
            if (root.getData().equals(data))
                toDelete = root;
            else
                toDelete = findNode(data);

            BinarySearchTreeNode<E> rightmostParent = toDelete;
            BinarySearchTreeNode<E> rightmost = toDelete.getLeft();

            if (rightmost == null)
                toDelete.setData(null);
            else {
                while (rightmost.getRight() != null) {
                    rightmostParent = rightmost;
                    rightmost = rightmost.getRight();
                }
                toDelete.setData(rightmost.getData());
                rightmostParent.setRight(rightmost.getLeft());
            }
        }
    }

    public void balance() {
        // get center, split list, get centers, split lists, stop splitting if lists are length 1
        if (root != null) {
            ArrayList<E> list = new ArrayList<>(getSortedList(root));

            root = new BinarySearchTreeNode<>(list.remove(list.size()/2));

            if (list.size() > 0) {
                balance(new ArrayList<>(list.subList(list.size()/2, list.size())));

                if (list.size() > 1)
                    balance(new ArrayList<>(list.subList(0, list.size()/2)));
            }
        }
    }

    private void balance(List<E> list) {
        insertWithoutBalancing(list.remove(list.size()/2));

        if (list.size() <= 2) {
            insertWithoutBalancing(list);
        } else {
            balance(new ArrayList<>(list.subList(0, list.size()/2)));
            balance(new ArrayList<>(list.subList(list.size()/2, list.size())));
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
