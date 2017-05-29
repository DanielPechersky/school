import java.util.*;

public class PathFinder {
    private int[][] paths;

    public PathFinder(int[][] paths) {
        this.paths = Arrays.copyOf(paths, paths.length);
    }

    private class Path implements Comparable<Path> {
        private final LinkedList<Integer> nodes;
        private final int cost;

        private Path(Collection<Integer> nodes, int cost) {
            this.nodes = new LinkedList<>(nodes);
            this.cost = cost;
        }

        protected Path(int start, int end, int cost) {
            nodes = new LinkedList<>();
            nodes.add(start);
            nodes.add(end);
            this.cost = cost;
        }

        protected Path add(int node, int cost) {
            LinkedList<Integer> new_nodes = new LinkedList<>(nodes);
            new_nodes.add(node);
            return new Path(new_nodes, this.cost + cost);
        }

        protected Path clone() {
            return new Path(nodes, cost);
        }

        protected int end() {
            return nodes.getLast();
        }

        protected ArrayList<Integer> toList() {
            return new ArrayList<>(nodes);
        }

        @Override
        public int compareTo(Path other) {
            return (cost < other.cost) ? -1 :
                    (cost == other.cost) ? 0 : 1;
        }
    }

    public ArrayList<Integer> dikstra(int start, int end) {
        PriorityQueue<Path> queue = new PriorityQueue<>();
        HashSet<Integer> visited = new HashSet<>();

        visited.add(start);
        for (int i=0; i<paths.length; i++)
            if (paths[start][i] != -1) {
                queue.add(new Path(start, i, paths[start][i]));
            }

        Path p = queue.poll();
        while (!queue.isEmpty()) {
            if (p.end() == end)
                return p.toList();

            visited.add(p.end());
            for (int i=0; i<paths.length; i++)
                if (paths[p.end()][i] != -1 && !visited.contains(i)) {
                    queue.add(p.add(i, paths[p.end()][i]));
                }
            p = queue.poll();
        }
        return null;
    }

}
