package Graph.Dijikstra;

import java.util.Comparator;

public class NodePairSort implements Comparator<NodePair> {

    @Override
    public int compare(NodePair n1, NodePair n2) {
        return Integer.compare(n1.distance, n2.distance);
    }
}
