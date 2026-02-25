package alda.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

public class MyUndirectedGraph<T> implements UndirectedGraph<T> {

    private Map<T, Map<T, Integer>> nodes = new HashMap<T, Map<T, Integer>>();

    @Override
    public int getNumberOfNodes() {
        return nodes.size();
    }

    @Override
    public int getNumberOfEdges() {
        int numOfEdges = 0;
        for(Map<T, Integer> neighbours : nodes.values()) {
            numOfEdges += neighbours.size();
        }
        return numOfEdges/2;
    }

    @Override
    public boolean add(T newNode) {
        if(nodes.putIfAbsent(newNode, new HashMap<>()) != null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean connect(T node1, T node2, int cost) {
        if(!nodes.containsKey(node1) || !nodes.containsKey(node2) || cost <= 0) {
            return false;
        }
        nodes.get(node1).put(node2, cost);
        nodes.get(node2).put(node1, cost);

        return true;
    }

    @Override
    public boolean isConnected(T node1, T node2) {
        if(!nodes.containsKey(node1)) return false;
        return nodes.get(node1).containsKey(node2);
    }

    @Override
    public int getCost(T node1, T node2) {
        if(!isConnected(node1, node2)) return -1;
        return nodes.get(node1).get(node2);
    }

    @Override
    public List<T> depthFirstSearch(T start, T end) {
        if(!nodes.containsKey(start) || !nodes.containsKey(end)) {
            return Collections.emptyList();
        }
        return dfsRecursive(start, end, new HashSet<>());
    }

    private List<T> dfsRecursive(T current, T end, Set<T> visited) {
        visited.add(current);

        if(current.equals(end)) {
            List<T> path = new LinkedList<>();
            path.add(current);
            return path;
        }

        for(T neighbour : nodes.get(current).keySet()) {
            if(!visited.contains(neighbour)) {
                List<T> path = dfsRecursive(neighbour, end, visited);

                if(!path.isEmpty()) {
                    path.add(0, current);
                    return path;
                }
            }
        }

        return Collections.emptyList();
    }

    @Override
    public List<T> breadthFirstSearch(T start, T end) {
        if(!nodes.containsKey(start) || !nodes.containsKey(end)) {
            return Collections.emptyList();
        }

        Queue<T> q = new LinkedList<>();
        Map<T, T> parents = new HashMap<>();

        q.add(start);
        parents.put(start, null);

        while(!q.isEmpty()) {
            T currentNode = q.poll();

            if(currentNode.equals(end)) {
                return reconstructPath(parents, end);
            }

            for(T neighbour : nodes.get(currentNode).keySet()) {
                if(!parents.containsKey(neighbour)) {
                    parents.put(neighbour, currentNode);
                    q.add(neighbour);
                }
            }
        }

        return Collections.emptyList();
    }

    private List<T> reconstructPath(Map<T, T> parentMap, T end) {
        LinkedList<T> path = new LinkedList<>();
        T curr = end;
        while(curr != null) {
            path.addFirst(curr);
            curr = parentMap.get(curr);
        }
        return path;
    }

    @Override
    public UndirectedGraph<T> minimumSpanningTree() {
        MyUndirectedGraph<T> mst = new MyUndirectedGraph<>();

        for(T node : nodes.keySet()) {
            mst.add(node);
        }

        class TempEdge implements Comparable<TempEdge> {
            T u, v;
            int weight;
            TempEdge(T u, T v, int weight) {
                this.u = u;
                this.v = v;
                this.weight = weight;
            }

            @Override
            public int compareTo(TempEdge other) {
                return Integer.compare(this.weight, other.weight);
            }
        }

        List<TempEdge> allEdges = new ArrayList<>();
        Set<Set<T>> seen = new HashSet<>();

        for(T u : nodes.keySet()) {
            for(Map.Entry<T, Integer> neighbour : nodes.get(u).entrySet()) {
                T v = neighbour.getKey();

                Set<T> edgePair = new HashSet<>(Arrays.asList(u, v));
                if(!seen.contains(edgePair)) {
                    allEdges.add(new TempEdge(u, v, neighbour.getValue()));
                    seen.add(edgePair);
                }
            }
        }

        Collections.sort(allEdges);

        for(TempEdge edge : allEdges) {
            if(mst.depthFirstSearch(edge.u, edge.v).isEmpty()) {
                mst.connect(edge.u, edge.v, edge.weight);
            }

            if(mst.getNumberOfEdges() == getNumberOfNodes() - 1) {
                break;
            }
        }

        return mst;
    }
}
