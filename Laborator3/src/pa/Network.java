package pa;

import java.util.*;

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

    public Node getNodeByName(String name) {
        for(Node n : nodes){
            if(n.getName().equals(name))
                return n;
        }
        return null;
    }

    public void addLinkByName(String from, String to, int delay){
        // Adding neighbours directly on the nodes is cumbersome, because you need
        // a reference to nodes, but you want to bury the nodes in the network class.
        // So I made thi API
        Node startNode = getNodeByName(from);
        if(startNode == null)
            return;
        Node endNode = getNodeByName(to);
        startNode.addNeighbour(endNode, delay);
    }

    public List<Node> getAllIdentifiable(){
        List<Node> identifiables = new ArrayList<>();

        for(Node n : nodes) {
            if( Identifiable.class.isAssignableFrom(n.getClass())){
                identifiables.add(n);
            }
        }

        return identifiables;
    }

    public void displayAllIdentifiable() {
        List<Node> identifiables = getAllIdentifiable();

        identifiables.sort((Node A, Node B) -> {
            Identifiable a = (Identifiable) A; // We know for sure this works;
            Identifiable b = (Identifiable) B; // We know for sure this works;
            return a.getAddress().compareTo(b.getAddress());
        });
        for (Node ident : identifiables) {
            System.out.println(ident);
        }
    }

    public static class RoutingPair {
        public Identifiable node;
        public Identifiable previous;
        public int time;

        public RoutingPair(Identifiable node, Identifiable previous, int time) {
            this.node = node;
            this.previous = previous;
            this.time = time;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Node: ").append(node.getAddress()).append(System.lineSeparator());
            if(this.previous != null) {
                builder.append("Previous; ").append(previous.getAddress()).append(System.lineSeparator());
            } else {
                builder.append("Previous; ").append("null").append(System.lineSeparator());
            }
            builder.append("Cost: ").append(time).append(System.lineSeparator());
            return builder.toString();
        }
    }


    public List<RoutingPair> getShortestRoutingTimes(Node start) {

        Map<Node, Boolean> checked = new HashMap<>();
        Map<Node, Node> previous = new HashMap<>();
        Map<Node, Integer> minCost = new HashMap<>();


        for(Node n : getAllIdentifiable()) {
            checked.put(n, false);
            previous.put(n, null);
            minCost.put(n, Integer.MAX_VALUE);
        }
        minCost.put(start, 0);
        Node current = start;
        do {
            for (Map.Entry<Node, Integer> n : current.getNeighbours().entrySet()) {
                Node node = n.getKey();
                if (!Identifiable.class.isAssignableFrom(node.getClass())) continue;
                int len = n.getValue();

                if (minCost.get(node) > len + minCost.get(current)) {
                    minCost.put(node, len + minCost.get(current));
                    previous.put(node, current);
                }
            }
            checked.put(current, true);
            int min = Integer.MAX_VALUE;
            current = null;
            for (Map.Entry<Node, Integer> n : minCost.entrySet()) {
                if (min >= n.getValue() && !checked.get(n.getKey())) {
                    current = n.getKey();
                    min = n.getValue();
                }
            }
        } while (current != null);
        List<RoutingPair> ret = new ArrayList<>();

        for(Map.Entry<Node, Node> n : previous.entrySet()) {
            ret.add(new RoutingPair((Identifiable) n.getKey(), (Identifiable) n.getValue(), minCost.get(n.getKey())));
        }

        return ret;
    }

    public static class Route {
        Identifiable from;
        List<RoutingPair> routingPairs;

        public Route(Identifiable from, List<RoutingPair> routingPairs) {
            this.from = from;
            this.routingPairs = routingPairs;
        }

        @Override
        public String toString() {
            return "Route{" +
                    "from=" + from +
                    ", routingPairs=" + routingPairs +
                    '}';
        }
    }

    public List<Route> getAllRoutes() {
        // Prea tarziu mi-am dat seama ca trebuie o lista de rute de la toate nodurile la toate nodurile
        // Floyd-Warshall ar fi fost mai potrivit situatiei...

        List<Route> ret = new ArrayList<>();
        List<Node> identifiables = getAllIdentifiable();
        for(Node n : identifiables) {
            ret.add(new Route((Identifiable) n, getShortestRoutingTimes(n)));
        }
        return ret;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("Network{").append(System.lineSeparator());
        for(Node n : nodes){
            builder.append(n.toString()).append(System.lineSeparator());
        }
        builder.append("}");
        return builder.toString();
    }
}
