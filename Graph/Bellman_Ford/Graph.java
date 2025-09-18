package Graph.Bellman_Ford;

import java.util.Arrays;

public class Graph {
    private final int[][] edges;
    private final int nodes;

    public Graph(int nodes, int[][] edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public int[] findMinDistance(int start) {
        int[] dist = new int[nodes];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;
        for (int i = 0; i < nodes; i++) {
            for (int[] edge : edges) {
                int node1 = edge[0];
                int node2 = edge[1];
                int weight = edge[2];
                if (dist[node1] + weight < dist[node2]) {
                    dist[node2] = dist[node1] + weight;
                }
            }
        }
        return dist;
    }
}
