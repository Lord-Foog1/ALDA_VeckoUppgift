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

	/*
	 * The @SuppressWarnings below *SHOULD* be removed.
	 */
	
	@SuppressWarnings("unused")
	private T data;
	@SuppressWarnings("unused")
	private BinarySearchTreeNode<T> left;
	@SuppressWarnings("unused")
	private BinarySearchTreeNode<T> right;

	public BinarySearchTreeNode(T data) {
		this.data = data;
	}

	public boolean add(T data) {
		if(data.compareTo(this.data) <= 0 && left == null) {
			left = new BinarySearchTreeNode<>(data);
			return true;
		}
		if(data.compareTo(this.data) >= 0 && right == null) {
			right = new BinarySearchTreeNode<>(data);
			return true;
		}
		if(data.compareTo(this.data) <= 0 && left != null) {
			return left.add(data);
		}
		if(data.compareTo(this.data) >= 0 && right != null) {
			return right.add(data);
		}
		return false;
	}

	/*
	 * The @SuppressWarnings below *SHOULD* be removed.
	 */
	@SuppressWarnings("unused")
	private T findMin() {
		return null;
	}

	public BinarySearchTreeNode<T> remove(T data) {
		return null;
	}

	public boolean contains(T data) {
		return false;
	}

	public int size() {
		return 0;
	}

	public int depth() {
		return -1;
	}

	public String toString() {
		return "";
	}
}
