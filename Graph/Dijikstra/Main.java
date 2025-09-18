package Graph.Dijikstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            List<List<Integer>> edges = new ArrayList<>();
            System.out.println("No of vertices: ");
            int n = sc.nextInt();
            System.out.println("Start adding edges in tuple( node1 , node2 , weight): (Enter -1 to stop adding edges)");
            while (true) {
                int node1 = sc.nextInt();
                if (node1 == -1) break;
                int node2 = sc.nextInt();
                int weight = sc.nextInt();
                System.out.println("Added the edge: Node1-> " + node1 + " , Node2-> " + node2 + " , Weight-> " + weight);
                List<Integer> list = new ArrayList<>();
                list.add(node1);
                list.add(node2);
                list.add(weight);
                edges.add(list);
            }
            Graph graph = new Graph(n, edges);
            System.out.println("Enter the source: ");
            int src = sc.nextInt();
            System.out.println("Minimum distance between source: " + src + " is: ");
            System.out.print(Arrays.toString(graph.findMinDistance(src)));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
