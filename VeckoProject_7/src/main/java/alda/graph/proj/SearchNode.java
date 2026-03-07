package alda.graph.proj;

import java.time.LocalTime;

public class SearchNode implements Comparable<SearchNode> {

    Node node;
    LocalTime currentTime;
    double costFromStart; // g(n): i minuter
    double estimatedTotalCost; // F(n) = g(n) + h(n)
    SearchNode parent;
    Departure arrivalDeparture;

    SearchNode(Node node, LocalTime time, double g, double f, SearchNode parent, Departure dep) {
        this.node = node;
        this.currentTime = time;
        this.costFromStart = g;
        this.estimatedTotalCost = f;
        this.parent = parent;
        this.arrivalDeparture = dep;
    }

    @Override
    public int compareTo(SearchNode other) {
        return Double.compare(this.estimatedTotalCost, other.estimatedTotalCost);
    }
}
