package pa;

public abstract class Node implements Comparable<Node> {
    private final String name;

    Node(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int compareTo(Node other) {
        if(other == null)
            return 1;
        return other.getName().compareTo(this.getName());
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                '}';
    }
}
