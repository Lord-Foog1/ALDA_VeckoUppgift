// Oliver Nordlander olno1943

// Feel free to use packages in your own environment, but remember to remove when handing it in

//RedBlackTree class
//
//CONSTRUCTION: with no parameters
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//void remove( x )       --> Remove x (unimplemented)
//boolean contains( x )  --> Return true if x is found
//Comparable findMin( )  --> Return smallest item
//Comparable findMax( )  --> Return largest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//void printTree( )      --> Print all items
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/**
 * Implements a red-black tree. Note that all "matching" is based on the
 * compareTo method.
 * <p>
 * Modified with a working, but extremely inefficient implementation of remove,
 * and following Checkstyles styleguide.
 *
 * @author Mark Allen Weiss
 */
public class RedBlackTree<AnyType extends Comparable<? super AnyType>> {
	private static final int BLACK = 1; // BLACK must be 1
	private static final int RED = 0;

	private RedBlackNode<AnyType> header;
	private RedBlackNode<AnyType> nullNode;

	// Used in insert routine and its helpers
	private RedBlackNode<AnyType> current;
	private RedBlackNode<AnyType> parent;
	private RedBlackNode<AnyType> grand;
	private RedBlackNode<AnyType> great;

	/**
	 * Construct the tree.
	 */
	public RedBlackTree() {
		nullNode = new RedBlackNode<>(null);
		nullNode.left = nullNode.right = nullNode;
		header = new RedBlackNode<>(null);
		header.left = header.right = nullNode;
	}

	/**
	 * Compare item and t.element, using compareTo, with caveat that if t is header,
	 * then item is always larger. This routine is called if is possible that t is
	 * header. If it is not possible for t to be header, use compareTo directly.
	 */
	private int compare(AnyType item, RedBlackNode<AnyType> t) {
		if (t == header)
			return 1;
		else
			return item.compareTo(t.element);
	}

	/**
	 * Insert into the tree.
	 *
	 * @param item the item to insert.
	 */
	public void insert(AnyType item) {
		current = parent = grand = header;
		nullNode.element = item;

		while (compare(item, current) != 0) {
			great = grand;
			grand = parent;
			parent = current;
			current = compare(item, current) < 0 ? current.left : current.right;

			// Check if two red children; fix if so
			if (current.left.color == RED && current.right.color == RED)
				handleReorient(item);
		}

		// Insertion fails if already present
		if (current != nullNode)
			return;
		current = new RedBlackNode<>(item, nullNode, nullNode);

		// Attach to parent
		if (compare(item, parent) < 0)
			parent.left = current;
		else
			parent.right = current;
		handleReorient(item);
	}

	/**
	 * Remove from the tree.
	 * <p>
	 * The current version works, but is extremely inefficient since it copies the
	 * entire content for each remove operation. Your job is to replace it with an
	 * efficient version that works on the tree directly.
	 *
	 * @param x the item to remove.
	 */
	public void remove(AnyType x) {
		if (isEmpty() || !contains(x)) {
			return;
		}

		RedBlackNode<AnyType> v = findNode(x);
		if (v == nullNode) return;

		RedBlackNode<AnyType> u = bstReplace(v);

		boolean uvBlack = u.color == BLACK && v.color == BLACK;

		RedBlackNode<AnyType> p = findParent(v);

		if (u == nullNode) {
			if (v == header.right) {
				header.right = nullNode;
			} else {
				if (uvBlack) {
					fixDoubleBlack(v);
				} else {
					RedBlackNode<AnyType> s = getSibling(v);
					if (s != nullNode) s.color = RED;
				}

				if (isLeftChild(v)) p.left = nullNode;
				else p.right = nullNode;
			}
			return;
		}

		if (v.left == nullNode || v.right == nullNode) {
			if (v == header.right) {
				v.element = u.element;
				v.left = v.right = nullNode;
			} else {
				if (isLeftChild(v)) p.left = u;
				else p.right = u;

				if (uvBlack) {
					fixDoubleBlack(u);
				} else {
					u.color = BLACK;
				}
			}
			return;
		}

		AnyType successorVal = u.element;
		remove(successorVal);
		v.element = successorVal;
	}

	private void remove(RedBlackTree<AnyType> newTree, RedBlackNode<AnyType> node, AnyType x) {
		if (node != nullNode) {
			if (!node.element.equals(x)) {
				newTree.insert(node.element);
			}
			remove(newTree, node.left, x);
			remove(newTree, node.right, x);
		}
	}

	private void fixDoubleBlack(RedBlackNode<AnyType> x) {
		if (x == header.right) return;

		RedBlackNode<AnyType> p = findParent(x);
		RedBlackNode<AnyType> s = getSibling(x);

		if (s == nullNode) { fixDoubleBlack(p); }
		else {
			if (s.color == RED) {
				p.color = RED;
				s.color = BLACK;
				if (isLeftChild(s)) { rotateRight(p); }
				else { rotateLeft(p); }
				fixDoubleBlack(x);
			} else {
				if (s.left.color == RED || s.right.color == RED) {
					if (s.left != nullNode && s.left.color == RED) {
						if (isLeftChild(s)) {
							s.left.color = s.color;
							s.color = p.color;
							rotateRight(p);
						} else {
							s.left.color = p.color;
							rotateRight(s);
							rotateLeft(p);
						}
					} else {
						if (isLeftChild(s)) {
							s.right.color = p.color;
							rotateLeft(s);
							rotateRight(p);
						} else {
							s.right.color = s.color;
							s.color = p.color;
							rotateLeft(p);
						}
					}
					p.color = BLACK;
				} else {
					s.color = RED;
					if (p.color == BLACK) fixDoubleBlack(p);
					else p.color = BLACK;
				}
			}
		}
	}

	private RedBlackNode<AnyType> bstReplace(RedBlackNode<AnyType> x) {
		if (x.left != nullNode && x.right != nullNode) {
			RedBlackNode<AnyType> temp = x.right;
			while (temp.left != nullNode) temp = temp.left;
			return temp;
		}
		return (x.left != nullNode) ? x.left : x.right;
	}

	private RedBlackNode<AnyType> findNode(AnyType x) {
		RedBlackNode<AnyType> t = header.right;
		while (t != nullNode) {
			int cmp = compare(x, t);
			if (cmp < 0) t = t.left;
			else if (cmp > 0) t = t.right;
			else return t;
		}
		return nullNode;
	}

	private RedBlackNode<AnyType> findParent(RedBlackNode<AnyType> child) {
		if (child == header.right) return header;
		RedBlackNode<AnyType> t = header.right;
		RedBlackNode<AnyType> p = header;
		while (t != nullNode && t != child) {
			p = t;
			t = (compare(child.element, t) < 0) ? t.left : t.right;
		}
		return p;
	}

	private RedBlackNode<AnyType> getSibling(RedBlackNode<AnyType> n) {
		RedBlackNode<AnyType> p = findParent(n);
		if (p == header) return nullNode;
		return (n == p.left) ? p.right : p.left;
	}

	private boolean isLeftChild(RedBlackNode<AnyType> n) {
		return n == findParent(n).left;
	}

	private void rotateLeft(RedBlackNode<AnyType> n) {
		RedBlackNode<AnyType> p = findParent(n);
		RedBlackNode<AnyType> newNode = rotateWithRightChild(n);
		if (p.left == n) p.left = newNode;
		else p.right = newNode;
	}

	private void rotateRight(RedBlackNode<AnyType> n) {
		RedBlackNode<AnyType> p = findParent(n);
		RedBlackNode<AnyType> newNode = rotateWithLeftChild(n);
		if (p.left == n) p.left = newNode;
		else p.right = newNode;
	}

	/**
	 * Find the smallest item the tree.
	 *
	 * @return the smallest item or throw UnderflowExcepton if empty.
	 */
	public AnyType findMin() {
		if (isEmpty())
			throw new UnderflowException();

		RedBlackNode<AnyType> itr = header.right;

		while (itr.left != nullNode)
			itr = itr.left;

		return itr.element;
	}

	/**
	 * Find the largest item in the tree.
	 *
	 * @return the largest item or throw UnderflowExcepton if empty.
	 */
	public AnyType findMax() {
		if (isEmpty())
			throw new UnderflowException();

		RedBlackNode<AnyType> itr = header.right;

		while (itr.right != nullNode)
			itr = itr.right;

		return itr.element;
	}

	/**
	 * Find an item in the tree.
	 *
	 * @param x the item to search for.
	 * @return true if x is found; otherwise false.
	 */
	public boolean contains(AnyType x) {
		nullNode.element = x;
		current = header.right;

		for (;;) {
			if (x.compareTo(current.element) < 0)
				current = current.left;
			else if (x.compareTo(current.element) > 0)
				current = current.right;
			else
				return current != nullNode;
		}
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty() {
		header.right = nullNode;
	}

	/**
	 * Print the tree contents in sorted order.
	 */
	public void printTree() {
		if (isEmpty())
			System.out.println("Empty tree");
		else
			printTree(header.right);
	}

	/**
	 * Internal method to print a subtree in sorted order.
	 *
	 * @param t the node that roots the subtree.
	 */
	private void printTree(RedBlackNode<AnyType> t) {
		if (t != nullNode) {
			printTree(t.left);
			System.out.println(t.element);
			printTree(t.right);
		}
	}

	/**
	 * Test if the tree is logically empty.
	 *
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return header.right == nullNode;
	}

	/**
	 * Internal routine that is called during an insertion if a node has two red
	 * children. Performs flip and rotations.
	 *
	 * @param item the item being inserted.
	 */
	private void handleReorient(AnyType item) {
		// Do the color flip
		current.color = RED;
		current.left.color = BLACK;
		current.right.color = BLACK;

		if (parent.color == RED) // Have to rotate
		{
			grand.color = RED;
			if ((compare(item, grand) < 0) != (compare(item, parent) < 0))
				parent = rotate(item, grand); // Start dbl rotate
			current = rotate(item, great);
			current.color = BLACK;
		}
		header.right.color = BLACK; // Make root black
	}

	/**
	 * Internal routine that performs a single or double rotation. Because the
	 * result is attached to the parent, there are four cases. Called by
	 * handleReorient.
	 *
	 * @param item   the item in handleReorient.
	 * @param parent the parent of the root of the rotated subtree.
	 * @return the root of the rotated subtree.
	 */
	private RedBlackNode<AnyType> rotate(AnyType item, RedBlackNode<AnyType> parent) {
		if (compare(item, parent) < 0)
			return parent.left = compare(item, parent.left) < 0 ? rotateWithLeftChild(parent.left) : // LL
					rotateWithRightChild(parent.left); // LR
		else
			return parent.right = compare(item, parent.right) < 0 ? rotateWithLeftChild(parent.right) : // RL
					rotateWithRightChild(parent.right); // RR
	}

	/**
	 * Rotate binary tree node with left child.
	 */
	private RedBlackNode<AnyType> rotateWithLeftChild(RedBlackNode<AnyType> node) {
		RedBlackNode<AnyType> leftChild = node.left;
		node.left = leftChild.right;
		leftChild.right = node;
		return leftChild;
	}

	/**
	 * Rotate binary tree node with right child.
	 */
	private RedBlackNode<AnyType> rotateWithRightChild(RedBlackNode<AnyType> node) {
		RedBlackNode<AnyType> rightChild = node.right;
		node.right = rightChild.left;
		rightChild.left = node;
		return rightChild;
	}

	private static class RedBlackNode<AnyType> {
		private AnyType element; // The data in the node
		private RedBlackNode<AnyType> left; // Left child
		private RedBlackNode<AnyType> right; // Right child
		private int color; // Color

		// Constructors
		RedBlackNode(AnyType theElement) {
			this(theElement, null, null);
		}

		RedBlackNode(AnyType theElement, RedBlackNode<AnyType> lt, RedBlackNode<AnyType> rt) {
			element = theElement;
			left = lt;
			right = rt;
			color = RedBlackTree.BLACK;
		}

		@Override
		public String toString() {
			return element == null ? "NULL" : element.toString();
		}

	}

}