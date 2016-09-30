import datastructures.BinarySearchTree;

import java.util.LinkedList;

public class BinarySearchTreeTest {
    public static void main(String[] args) {
        for (String treeDataString : args) {
            System.out.println(treeDataString);

            LinkedList<Integer> treeData = new LinkedList<>();
            for (String n : treeDataString.split(","))
                treeData.add(Integer.parseInt(n));

            BinarySearchTree<Integer> tree = new BinarySearchTree<>(treeData);

            System.out.println(tree);
            System.out.println(tree.getSortedList());
            tree.delete(55);
            System.out.println(tree);
            System.out.println(tree.getSortedList());

        }
    }
}
