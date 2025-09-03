package Graph;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(5);
        int[][] edges = {
                {0, 1},
                {1, 2},
                {2, 3},
                {3, 4}
        };
        graph.addEdges(edges);
        System.out.println(graph.isConnected(0, 4));
    }
}
