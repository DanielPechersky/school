import datastructures.QuadTree;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class QuadTreeTest {
    public static void main(String[] args) {
        QuadTree<Boolean> tree = fromFile(args[0]);

        System.out.println(tree.toString());

        Boolean[][] array = new Boolean[tree.getSize()][tree.getSize()];
        tree.fillArray(array);


        for (Boolean[] row : array)
            System.out.println(Arrays.deepToString(row));

        System.out.println(tree.toDepthString());
    }

    @SuppressWarnings("unchecked")
    private static QuadTree<Boolean> fromFile(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return new QuadTree<>(
                    lines.map(s -> Arrays.stream(s.split(","))
                            .map(Boolean::parseBoolean).toArray(Boolean[]::new))
                    .toArray(Boolean[][]::new));
        } catch (IOException e) {
            return null;
        }
    }
}
