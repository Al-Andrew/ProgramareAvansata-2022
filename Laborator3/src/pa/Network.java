package pa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Network {
    private List<Node> nodes = new ArrayList<>();

    public void addNode(Node node) {
        for(Node n : nodes) { // Ensure all node names are unique
            if(n.getName().equals(node.getName()))
                return;
        }
        nodes.add(node);
    }

    public void addAllNodes(Node[] nodes) {
        this.nodes.addAll(Arrays.stream(nodes).toList());
    }

    @Override
    public String toString() {
        return "Network{" +
                "nodes=" + nodes +
                '}';
    }
}
