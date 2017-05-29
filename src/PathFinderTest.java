import java.util.ArrayList;
import java.util.Arrays;

public class PathFinderTest {

    public static void main(String[] args) {
        int[][] paths_unparsed = Arrays.stream(args)
                .skip(2)
                .map(path -> Arrays.stream(path.split(",")).mapToInt(Integer::parseInt).toArray())
                .toArray(int[][]::new);

        int highest_node = 0;
        for (int[] row : paths_unparsed)
            highest_node = Math.max(highest_node, Math.max(row[0], row[1]));

        int[][] paths = new int[highest_node+1][highest_node+1];
        for (int[] row : paths)
            Arrays.fill(row, -1);

        for (int i=0; i<paths_unparsed.length; i++) {
            int p1 = paths_unparsed[i][0];
            int p2 = paths_unparsed[i][1];
            int cost = paths_unparsed[i][2];
            paths[p1][p2] = cost;
            paths[p2][p1] = cost;
        }

        ArrayList<Integer> path = new PathFinder(paths).dikstra(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println(Arrays.deepToString(path.toArray()));
    }
}
