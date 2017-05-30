import java.util.ArrayList;
import java.util.Arrays;

public class PathFinderTest {

    public static void main(String[] args) {
        int[][] paths_unparsed = Arrays.stream(args)
                .skip(1)
                .map(path -> Arrays.stream(path.split(","))
                        .mapToInt(Integer::parseInt)
                        .toArray())
                .toArray(int[][]::new);

        int highest_node = 0;
        for (int[] row : paths_unparsed)
            highest_node = Math.max(highest_node, Math.max(row[0], row[1]));

        int[][] paths = new int[highest_node+1][highest_node+1];
        for (int[] row : paths)
            Arrays.fill(row, -1);

        for (int[] aPaths_unparsed : paths_unparsed) {
            int p1 = aPaths_unparsed[0];
            int p2 = aPaths_unparsed[1];
            int cost = aPaths_unparsed[2];
            paths[p1][p2] = cost;
            paths[p2][p1] = cost;
        }

        int[] start_and_end = Arrays.stream(args[0].split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        ArrayList<Integer> path = new PathFinder(paths).dikstra(start_and_end[0], start_and_end[1]);

        System.out.println(path != null ?
                Arrays.deepToString(path.toArray()) :
                "no path");
    }
}
