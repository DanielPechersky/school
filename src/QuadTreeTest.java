import datastructures.QuadTree;

public class QuadTreeTest {
    public static void main(String[] args) {
        QuadTree<Boolean> tree = new QuadTree<>(new Boolean[][]{
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, true, true, true, false},
                {false, false, false, false, true, true, false, false},
                {true, true, true, true, true, true, false, false},
                {true, true, true, true, true, true, false, false},
                {true, true, true, true, false, false, false, false},
                {true, true, true, true, false, false, false, true}});

        System.out.println(tree.toString());
    }
}
