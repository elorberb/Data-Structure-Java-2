
public class AVLNode<T> {

	private int key;
	private T data;
	private int height;
	private AVLNode<T> father;
	private AVLNode<T> leftChild;
	private AVLNode<T> rightChild;

	/**
	 * Constructor of a new AVL tree node.
	 * @param data data stored in the Node.
	 * @param key a key value to order by the AVL tree nodes.
	 */
	public AVLNode(T data, int key) {
		this.data = data;
		this.key = key;
		this.height = 0;
	}

	/**
	 * Getter of the left child of the node.
	 * @return the wanted child.
	 */
	public AVLNode<T> getLeftChild() {

		return leftChild;
	}

	/**
	 * Setter of the left child of the node.
	 * @param lc the child to replace with the current child of the node.
	 *           current left child can be Null.
	 */
	public void setLeftChild(AVLNode<T> lc) {
		this.leftChild = lc;
		if(lc != null) {
			lc.setFather(this);
		}
	}

	/**
	 * Getter of the right child of the node.
	 * @return the wanted child.
	 */
	public AVLNode<T> getRightChild() {
	
		return rightChild;
	}

	/**
	 * Setter of the right child of the node.
	 * @param rc the child to replace with the current child of the node.
	 *           current right child can be Null.
	 */
	public void setRightChild(AVLNode<T> rc) {
		this.rightChild = rc;
		if(rc != null) {
			rc.setFather(this);
		}
	}

	/**
	 * Setter of the parent(father) of the node.
	 * @param f the father to replace with the current father of the node.
	 *           current father can be Null.
	 */
	public void setFather(AVLNode<T> f) {
		this.father = f;
	}

	/**
	 * Getter of the key value of the node.
	 * @return key.
	 */
	public int getKey() {
		return key;
	}

	/**
	 * Getter of the data value of the node.
	 * @return data.
	 */
	public T getData() {
		return data;
	}

	/**
	 * Getter of the height value of the node.
	 * @return height.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Setter of the height value of the node.
	 * @param h a new height value to replace with the current value.
	 */
	public void setHeight(int h) {
		this.height = h;
	}

	@Override
	/**
	 * Override toString function
	 */
	public String toString() {

		String s = "";

		if (getLeftChild() != null) {
			s += "(";
			s += getLeftChild().toString();
			s += ")";
		}
		s += getKey();

		if (getRightChild() != null) {
			s += "(";
			s += getRightChild().toString();
			s += ")";
		}

		return s;
	}
}
