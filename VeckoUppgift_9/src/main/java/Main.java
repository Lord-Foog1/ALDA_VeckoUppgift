public class Main {
    public static void main(String[] args) {
        RedBlackTree<Integer> rbt = new RedBlackTree<>();

        rbt.insert(Integer.valueOf(1));
        rbt.insert(Integer.valueOf(2));
        rbt.insert(Integer.valueOf(3));
        rbt.insert(Integer.valueOf(4));

        rbt.printTree();

        System.out.println();

        rbt.remove(Integer.valueOf(2));

        rbt.printTree();
    }
}
