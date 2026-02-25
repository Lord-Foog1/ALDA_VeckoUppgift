package alda.graph;

public class Main {
    public static void main(String[] args) {
        MyUndirectedGraph<Integer> graph = new MyUndirectedGraph<Integer>();

        for(int i = 0; i < 100; i++) {
            graph.add(i);
        }

        for(int i = 0; i <= 50; i++){
            graph.connect(i, i+1, i^2 + 1);
        }

        System.out.println(graph.getNumberOfNodes());
        System.out.println(graph.getNumberOfEdges());

        for(int i = 1; i <= 10; i+=2) {
            System.out.println(graph.isConnected(i, i*2));
        }

        System.out.println(graph.breadthFirstSearch(11, 20));

        System.out.println(graph.depthFirstSearch(11, 20));

    }
}
