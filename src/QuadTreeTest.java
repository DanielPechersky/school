import datastructures.QuadTree;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class QuadTreeTest {
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

    public static void main(String[] args) {
        QuadTree<Boolean> tree = fromFile("src/quad.dat");

        System.out.println(tree.toString());

        Boolean[][] array = new Boolean[8][8];
        tree.fillArray(array);

        for (Boolean[] row : array) {
            for (Boolean bool : row)
                System.out.print(bool+" ");
            System.out.println();
        }
    }
}
