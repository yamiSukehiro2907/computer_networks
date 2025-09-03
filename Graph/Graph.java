package Graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {
    private final int vertices;

    private final List<List<Integer>> list;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.list = new ArrayList<>();
        fillVertices();
    }

    private void fillVertices() {
        for (int i = 0; i < vertices; i++) {
            list.add(new ArrayList<>());
        }
    }

    public void addEdges(int[][] edges) {
        for (int[] edge : edges) {
            list.get(edge[0]).add(edge[1]);
            list.get(edge[1]).add(edge[0]);
        }
    }

    public boolean isConnected(int source, int destination) {
        if (source == destination) return true;
        System.out.print("Starting Bfs: ");
        System.out.println(bfs(source, destination));
        System.out.print("Starting Dfs: ");
        boolean[] visited = new boolean[vertices];
        return dfs(visited, source, destination);
    }

    private boolean dfs(boolean[] visited, int source, int destination) {
        visited[source] = true;
        for (int node : list.get(source)) {
            if (source == destination)
                return true;
            else if (!visited[node]) {
                return dfs(visited, node, destination);
            }
        }
        return false;
    }

    public boolean bfs(int source, int destination) {
        if (source == destination)
            return true;
        boolean[] visited = new boolean[vertices];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited[source] = true;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            for (int neighbor : list.get(node)) {
                if (neighbor == destination) {
                    return true;
                } else if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
        return false;
    }
}