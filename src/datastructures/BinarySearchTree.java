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

    private static <E extends Comparable<E>> LinkedList<E> getSortedList(BinarySearchTreeNode<E> node) {
        LinkedList<E> sortedList = new LinkedList<>();
        if (node.getLeft() != null)
            sortedList.addAll(getSortedList(node.getLeft()));
        sortedList.add(node.getData());
        if (node.getRight() != null)
            sortedList.addAll(getSortedList(node.getRight()));
        return sortedList;
    }

    private BinarySearchTreeNode<E> getParent(E childData) {
        BinarySearchTreeNode<E> parent = null;
        BinarySearchTreeNode<E> current = root;

        while (!childData.equals(current.getData())) {
            parent = current;
            if (childData.compareTo(current.getData()) == -1)
                current = current.getLeft();
            else
                current = current.getRight();

            if (current == null)
                return null;
        }
        return parent;
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
            BinarySearchTreeNode<E> current = null;
            BinarySearchTreeNode<E> next = root;

            while (next != null) {
                current = next;
                if (toInsert.compareTo(next) == -1)
                    next = current.getLeft();
                else
                    next = current.getRight();
            }
            if (toInsert.compareTo(current) == -1)
                current.setLeft(toInsert);
            else
                current.setRight(toInsert);
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
            if (root.getData().equals(data)) {
                BinarySearchTreeNode<E> toDelete = root;

                if (root.getLeft() != null) {
                    root = toDelete.getLeft();
                    if (root.getRight() != null)
                        insert(getSortedList(toDelete.getRight()));

                } else if (root.getRight() != null)
                    root = toDelete.getRight();
                else
                    root = null;
            } else {
                BinarySearchTreeNode<E> parent = getParent(data);

                if (data.compareTo(parent.getData()) == -1) {
                    BinarySearchTreeNode<E> toDelete = parent.getLeft();
                    if (toDelete.getLeft() != null) {
                        parent.setLeft(toDelete.getLeft());
                        if (toDelete.getRight() != null)
                            insert(getSortedList(toDelete.getRight()));
                    } else if (toDelete.getRight() != null)
                        parent.setLeft(toDelete.getRight());
                    else
                        parent.setLeft(null);
                } else {
                    BinarySearchTreeNode<E> toDelete = parent.getRight();
                    if (toDelete.getLeft() != null) {
                        parent.setRight(toDelete.getLeft());
                        if (toDelete.getRight() != null)
                            insert(getSortedList(toDelete.getRight()));
                    } else if (toDelete.getRight() != null)
                        parent.setRight(toDelete.getRight());
                    else
                        parent.setRight(null);
                }
            }
        }
    }

    public void balance() {
        // get center, split list, get centers, split lists, stop splitting if lists are length 1
        if (root != null) {
            ArrayList<E> list = new ArrayList<>(getSortedList(root));
            root = new BinarySearchTreeNode<>(list.remove(list.size()/2));

            Queue<ArrayList<E>> splitLists = new LinkedList<>();

            if (list.size() > 0) {
                splitLists.add(new ArrayList<>(list.subList(list.size()/2, list.size())));

                if (list.size() > 1)
                    splitLists.add(new ArrayList<>(list.subList(0, list.size()/2)));
            }

            while (splitLists.size() > 0) {
                list = splitLists.remove();
                insertWithoutBalancing(list.remove(list.size()/2));

                if (list.size() <= 2) {
                    insertWithoutBalancing(list);
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
