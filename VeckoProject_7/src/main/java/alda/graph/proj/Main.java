package alda.graph.proj;

public class Main {
    public static void main(String[] args) {
        SLLite sl = new SLLite();

        Node n1 = new Node("n1", 0, 0);
        Node n2 = new Node("n2", 1, 1);
        Node n3 = new Node("n3", 2, 2);
        Node n4 = new Node("n4", 3, 3);

        sl.add(n1);
        sl.add(n2);
        sl.add(n3);
        sl.add(n4);

        System.out.println(sl.getNumberOfNodes());
        System.out.println(sl.getNumberOfEdges());

        sl.connect(n1, n2, 10, 20, 15,"874");

        sl.connect(n2, n3, 15, 30, 22.5,"123");

        System.out.println(sl.getNumberOfNodes());
        System.out.println(sl.getNumberOfEdges());

        System.out.println(sl.isConnected(n1, n2));
        System.out.println(sl.isConnected(n1, n3));
    }
}
