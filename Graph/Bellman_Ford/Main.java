package Graph.Bellman_Ford;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[][] edges = {
                {0, 1, 3},
                {1, 2, -2},
                {0, 2, 2},
        };
        Graph graph = new Graph(3, edges);
        System.out.println(Arrays.toString(graph.findMinDistance(0)));
    }
}
