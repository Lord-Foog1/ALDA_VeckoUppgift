package alda.t3.bst;

public class Main {
    public static void main(String[] args) {
        BinarySearchTreeNode<String> tree = new BinarySearchTreeNode("1");
        tree.add("2");
        tree.add("3");
        tree.add("1");
        tree.add("1");
    }
}
