import datastructures.QuadTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class QuadTreeTest {
    public static void main(String[] args) {
        QuadTree<Boolean> tree = fromFile(args[0]);

        System.out.println(tree.toString());

        Boolean[][] array = new Boolean[tree.getSize()][tree.getSize()];
        tree.fillArray(array);

        for (Boolean[] row : array) {
            for (Boolean bool : row)
                System.out.print(bool+" ");
            System.out.println();
        }
    }

    @SuppressWarnings("unchecked")
    private static QuadTree<Boolean> fromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return new QuadTree<>(br.lines()
                    .map(s -> Arrays.stream(s.split(","))
                            .map(Boolean::parseBoolean).toArray(Boolean[]::new))
                    .toArray(Boolean[][]::new));
        } catch (IOException e) {
            return null;
        }
    }
}
