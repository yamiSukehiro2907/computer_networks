package Graph.Dijikstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

public class Graph {
    private final List<List<Node>> adjList;
    private final int nodes;

    public Graph(int n, List<List<Integer>> list) {
        this.nodes = n;
        this.adjList = new ArrayList<>();
        for (int i = 0; i < nodes; i++) {
            adjList.add(new ArrayList<>());
        }
        for (List<Integer> temp : list) {
            int node1 = temp.get(0);
            int node2 = temp.get(1);
            int weight = temp.get(2);
            adjList.get(node1).add(new Node(node2, weight));
            adjList.get(node2).add(new Node(node1, weight));
        }
    }

    public int[] findMinDistance(int src) {
        int[] distance = new int[nodes];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[src] = 0;
        PriorityQueue<NodePair> queue = new PriorityQueue<>(new NodePairSort());
        queue.add(new NodePair(src, 0));
        while (!queue.isEmpty()) {
            NodePair nodePair = queue.poll();
            List<Node> neighbors = adjList.get(nodePair.node);
            for (Node node : neighbors) {
                if (distance[node.node] > nodePair.distance + node.weight) {
                    distance[node.node] = nodePair.distance + node.weight;
                    NodePair nodePairNew = new NodePair(node.node, nodePair.distance + node.weight);
                    queue.add(nodePairNew);
                }
            }
        }
        return distance;
    }
}
