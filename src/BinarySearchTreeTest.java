import datastructures.BinarySearchTree;

import java.util.LinkedList;

public class BinarySearchTreeTest {
    public static void main(String[] args) {
        // tree data is passed in as a list of numbers separated by commas
        String treeDataString = args[0];
        System.out.println(treeDataString);

        LinkedList<Integer> treeData = new LinkedList<>();
        for (String n : treeDataString.split(","))
            treeData.add(Integer.parseInt(n));

        BinarySearchTree<Integer> tree = new BinarySearchTree<>(treeData);

        System.out.println(tree);
        System.out.println(tree.getSortedList());
        tree.delete(Integer.parseInt(args[1]));
        System.out.println(tree);
        System.out.println(tree.getSortedList());
    }
}
