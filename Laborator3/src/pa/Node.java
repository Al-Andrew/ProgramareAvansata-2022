package pa;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class Node implements Comparable<Node> {
    private final String name;
    private final Map<Node, Integer> neighbours;

    Node(String name) {
        this.name = name;
        this.neighbours = new HashMap<>();
    }

    public void addNeighbour(Node neighbour, int time) {
        if(neighbour == null) return;
        neighbours.put(neighbour, time);
        neighbour.neighbours.put(this, time); // The connection is two ways
    }

    public Map<Node, Integer> getNeighbours() {
        return this.neighbours;
    }

    public String getName() {
        return name;
    }

    public int compareTo(Node other) {
        if(other == null)
            return 1;
        return other.getName().compareTo(this.getName());
    }

    // Some very basic api to reuse some code when printing the derived classes;
    public StringBuilder getUnEndedStringBuilder(String title) {
        StringBuilder builder = new StringBuilder();
        builder.append(title).append("{ ").append(System.lineSeparator());
        builder.append("    name: ").append(name).append(",").append(System.lineSeparator());
        builder.append("    connections: {").append(System.lineSeparator());
        for(Map.Entry<Node, Integer> connection : neighbours.entrySet()) {
            builder.append("    ( ");
            builder.append(connection.getKey().getName()).append(", ").append(connection.getValue());
            builder.append(" ), ").append(System.lineSeparator());
        }
        builder.append("    }").append(System.lineSeparator());

        return builder;
    }

    @Override
    public String toString() {
        StringBuilder builder = getUnEndedStringBuilder("Node");
        builder.append("}");

        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return getName().equals(node.getName()) && Objects.equals(getNeighbours(), node.getNeighbours());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
