package alda.t3.bst;

public class Main {
    public static void main(String[] args) {
        BinarySearchTreeNode<String> tree = new BinarySearchTreeNode("100");
        tree.add("20");
        tree.add("60");
        tree.add("50");
        tree.add("10");
        tree.add("40");
        tree.add("200");
        tree.add("150");
        tree.add("175");
        tree.add("75");


        System.out.println(tree);

    }
}
