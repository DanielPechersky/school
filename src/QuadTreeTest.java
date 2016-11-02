import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class QuadTreeTest {
    public static void main(String[] args) {
        // identical to QuadTree, except can also be displayed with swing
        GraphicQuadTree<Boolean> tree = new GraphicQuadTree<>(fromFile(args[0]));

        Boolean[][] array = new Boolean[tree.getSize()][tree.getSize()];
        tree.fillArray(array);

        for (Boolean[] row : array)
            System.out.println(Arrays.deepToString(row));

        System.out.println(tree.toDepthString());

        JFrame frame = new JFrame("tree");

        JPanel panel = tree.getPanel();

        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private static Boolean[][] fromFile(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines.map(s -> Arrays.stream(s.split(","))
                        .map(Boolean::parseBoolean).toArray(Boolean[]::new))
                    .toArray(Boolean[][]::new);
        } catch (IOException e) {
            return null;
        }
    }
}
