/*
 * You are encouraged to use packages for the assignments, but it's not a requirement.
 * However, when submitting any code in ilearn through VPL you *MUST* remove the package.
 */
package alda.t3.bst;

/**
 * 
 * Detta är den enda av de tre klasserna ni ska göra några ändringar i. (Om ni
 * inte vill lägga till fler testfall.) Det är också den enda av klasserna ni
 * ska lämna in. Glöm inte att namn och användarnamn ska stå i en kommentar
 * högst upp, och att en eventuell paketdeklarationen måste plockas bort vid
 * inlämningen för att koden ska gå igenom de automatiska testerna.
 * 
 * De ändringar som är tillåtna är begränsade av följande: <ul> <li>Ni får INTE
 * byta namn på klassen. <li>Ni får INTE lägga till några fler instansvariabler.
 * <li>Ni får INTE lägga till några statiska variabler. <li>Ni får INTE använda
 * några loopar någonstans. Detta gäller också alterntiv till loopar, så som
 * strömmar. <li>Ni FÅR lägga till fler metoder, dessa ska då vara privata.
 * <li>Ni får INTE låta NÅGON metod ta en parameter av typen
 * BinarySearchTreeNode. Enbart den generiska typen (T eller vad ni väljer att
 * kalla den), String, StringBuilder, StringBuffer, samt primitiva typer är
 * tillåtna. </ul>
 * 
 * @author henrikbe
 * 
 * @param <T>
 */

public class BinarySearchTreeNode<T extends Comparable<T>> {

	private T data;

	private BinarySearchTreeNode<T> left;

	private BinarySearchTreeNode<T> right;


	public BinarySearchTreeNode(T data) {
		this.data = data;
	}


	public boolean add(T data) {
		if(data.compareTo(this.data) < 0 && left == null) {
			left = new BinarySearchTreeNode<>(data);
			return true;
		}
		if(data.compareTo(this.data) > 0 && right == null) {
			right = new BinarySearchTreeNode<>(data);
			return true;
		}
		if(data.compareTo(this.data) < 0 && left != null) {
			return left.add(data);
		}
		if(data.compareTo(this.data) > 0 && right != null) {
			return right.add(data);
		}
		return false;
	}

	private T findMin() {
		if(left == null) {
			return this.data;
		}
		else {
			return left.findMin();
		}
	}

	public BinarySearchTreeNode<T> remove(T data) {
		if(left != null && left.data.compareTo(data) == 0) {
			if(left.right == null && left.left == null) {
				left = null;
			}
			else if(left.right != null && left.left != null) {
				left.right.left = left.left;
				left = left.right;
			}
			return null;
		}
		if(right != null && right.data.compareTo(data) == 0) {
			if(right.right == null && right.left == null) {
				right = null;
			}
			else if(right.left != null && right.right != null) {
				right.right.left = right.left;
				right = right.right;
			}
			return null;
		}

		if(left != null && data.compareTo(this.data) < 0) {
			left.remove(data);
		}
		if(right != null && data.compareTo(this.data) > 0) {
			right.remove(data);
		}
		return null;
	}

	public boolean contains(T data) {
		if(this.data == data) {
			return true;
		}
		if(data.compareTo(this.data) < 0 && left != null) {
			return left.contains(data);
		}
		if(data.compareTo(this.data) > 0 && right != null) {
			return right.contains(data);
		}
		return false;
	}

	public int size() {
		int num = 0;

		if(left != null) {
			num += left.size();
		}
		if(right != null) {
			num += right.size();
		}

		num++;

		return num;
	}

	public int depth() {
		int depth = -1;
		int current = -1;

		if(right == null && left == null) {
			return current;
		}

		if(left != null) {
			current += left.depth();
		}
		if(right != null) {
			current += right.depth();
		}

		if(current > depth) {
			depth = current;
		}

		return depth;
	}

	public String toString() {
		String sum = "";

		sum += data + ", ";

		if(left != null) {
			sum += left.toString();
		}
		if(right != null) {
			sum += right.toString();
		}

		return sum;
	}
}
