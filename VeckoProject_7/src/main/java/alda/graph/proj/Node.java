package alda.graph.proj;

import java.util.Objects;

public class Node implements Comparable<Node> {
    private String stopName;
    private double x, y;

    public double gScore = Double.POSITIVE_INFINITY;
    public double fScore = Double.POSITIVE_INFINITY;
    public Node parent = null;

    Node(String name, double x, double y) {
        this.stopName = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return stopName;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public int compareTo(Node o) {
        return Double.compare(this.fScore, o.fScore);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return Objects.equals(stopName, node.stopName);
    }

    public int hashCode() {
        return Objects.hash(stopName);
    }
}
