package alda.graph.proj;

import java.util.*;

public class SLLite {
    Map<Node, Map<Node, Edge>> nodes = new HashMap<>();

    public void add(Node node) {
        nodes.putIfAbsent(node, new HashMap<>());
    }

    public void connect(Node node1, Node node2, double travelTime, double departureTime, double arrivalTime, String routeName) {
        if(!nodes.containsKey(node1) || !nodes.containsKey(node2) || travelTime <= 0 || departureTime <= 0) {
            return;
        }

        nodes.get(node1).put(node2, new Edge(routeName, departureTime, travelTime, arrivalTime));
        nodes.get(node2).put(node1, new Edge(routeName, departureTime, travelTime, arrivalTime));
    }

    public boolean isConnected(Node node1, Node node2) {
        if(!nodes.containsKey(node1) || !nodes.containsKey(node2)) return false;

        return nodes.get(node1).containsKey(node2);
    }

    public int getNumberOfNodes() {
        return nodes.size();
    }

    public int getNumberOfEdges() {
        int numEdge = 0;
        for(Map<Node, Edge> neighbors : nodes.values()) {
            numEdge += neighbors.size();
        }
        return numEdge/2;
    }

    public List<Node> findPath(Node start, Node end) {
        for(Node n : nodes.keySet()) {
            n.gScore = Double.POSITIVE_INFINITY;
            n.fScore = Double.POSITIVE_INFINITY;
            n.parent = null;
        }

        PriorityQueue<Node> openList = new PriorityQueue<>();
        Set<Node> closedList = new HashSet<>();

        start.gScore = 0;
        start.fScore = calculateHeuristics(start, end);
        openList.add(start);

        while(!openList.isEmpty()) {
            Node current = openList.poll();

            if(current.equals(end)) {
                return reconstructPath(end);
            }

            closedList.add(current);

            Map<Node, Edge> neighbors = nodes.get(current);
            if(neighbors == null) continue;

            for(Node neighbor : neighbors.keySet()) {
                if(closedList.contains(neighbor)) continue;

                Edge edge = neighbors.get(neighbor);

                double tentativeG = current.gScore + edge.getTravelTime();

                if(tentativeG < neighbor.gScore) {
                    neighbor.parent = current;
                    neighbor.gScore = tentativeG;
                    neighbor.fScore = neighbor.gScore + calculateHeuristics(neighbor, end);

                    if(!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }

        return Collections.emptyList();
    }

    private List<Node> reconstructPath(Node endNode) {
        List<Node> path = new ArrayList<>();
        Node temp = endNode;
        while(temp != null) {
            path.add(0, temp);
            temp = temp.parent;
        }
        return path;
    }

    private double calculateHeuristics(Node a, Node b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }
}
