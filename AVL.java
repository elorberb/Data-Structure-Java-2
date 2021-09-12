import java.util.LinkedList;

import javax.management.RuntimeErrorException;

public class AVL<T> {

	private AVLNode<T> root;

	/**
	 * Constructor of a new empty AVL tree.
	 */
	public AVL() {
		this.root = null;
	}

	/**
	 * Update node's height (by the taller child plus 1).
	 * @param n a node to update it's height.
	 */
	private void updateHeight(AVLNode<T> n) {
		int leftChildHeight = this.getHeight(n.getLeftChild());
		int rightChildHeight = this.getHeight(n.getRightChild());
		n.setHeight(1 + Math.max(leftChildHeight, rightChildHeight));
	}

	/**
	 * Getter of height of a given node in the AVL tree.
	 * @param n a node to get it's height.
	 * @return height value.
	 */
	private int getHeight(AVLNode<T> n) {
		return n == null ? -1 : n.getHeight();
	}

	/**
	 * Get the node balance value (by definition of AVL trees).
	 * @param n a node to get it's balance value.
	 * @return balance value.
	 */
	private int getBalance(AVLNode<T> n) {
		return n == null ? 0 : getHeight(n.getRightChild()) - getHeight(n.getLeftChild());
	}

	/**
	 * Return wether the node is the root of it's tree.
	 * @param n a node to check if it is a root.
	 * @return True if the node is a root and False if not.
	 */
	private boolean isRoot(AVLNode<T> n) {
		return n.getKey() == this.root.getKey();
	}

	/**
	 * Getter to the root of the tree.
	 * @return root.
	 */
	public AVLNode<T> getRoot() {
		return root;
	}

	/**
	 * Setter of the root by a given node.
	 * @param n a node to replace with the current root of the node.
	 * 	 *           current root child can be Null.
	 */
	public void setRoot(AVLNode<T> n) {
		this.root = n;
		n.setFather(null);
	}

	/**
	 * A right rotation function of an AVL tree node
	 * @param k2 the unbalanced node that the rotation needs to be done by it.
	 * @return k1 the node that need to be replaced by k2 in the tree.
	 */
	private AVLNode<T> rotateRight(AVLNode<T> k2) {
		AVLNode<T> k1 = k2.getLeftChild();
		AVLNode<T> b = k1.getRightChild();
		// if k2 is root switch with k1
		if (isRoot(k2)) {
			setRoot(k1);
		}

		// perform rotation
		k1.setRightChild(k2);
		k2.setLeftChild(b);

		// update heights
		updateHeight(k2);
		updateHeight(k1);
		return k1;
	}

	/**
	 * A left rotation function of an AVL tree node
	 * @param k2 the unbalanced node that the rotation needs to be done by it.
	 * @return k1 the node that need to be replaced by k2 in the tree.
	 */
	private AVLNode<T> rotateLeft(AVLNode<T> k2) {
		AVLNode<T> k1 = k2.getRightChild();
		AVLNode<T> b = k1.getLeftChild();

		// if k2 is root switch with k1
		if (isRoot(k2)) {
			setRoot(k1);
		}

		// perform rotation
		k1.setLeftChild(k2);
		k2.setRightChild(b);

		// update heights
		updateHeight(k2);
		updateHeight(k1);
		return k1;
	}

	/**
	 * Insert a new node to the tree by given a key and data of the node.
	 * @param key a key of the node.
	 * @param data date of the node.
	 */
	public void insert(int key, T data) {
		AVLNode<T> n = new AVLNode<T>(data, key);
		AVLNode<T> root = getRoot();
		if(root == null) {
			setRoot(n);
			return;
		}
		insert_node(root, key, data);
	}

	/**
	 * Recursive function to insert a new node to it's correct place in the AVL tree.
	 * @param n a node to start the insertion from.
	 * @param key a key of the node intended to be inserted.
	 * @param data date of the node intended to be inserted.
	 * @return a node that was rebalanced from.
	 */
	private AVLNode<T> insert_node(AVLNode<T> n, int key, T data) {
		if (n == null) {
			return new AVLNode<T>(data, key);
		} else if (n.getKey() > key) {
			n.setLeftChild(insert_node(n.getLeftChild(), key, data));
		} else if (n.getKey() < key) {
			n.setRightChild(insert_node(n.getRightChild(), key, data));
		} else {
			throw new RuntimeErrorException(null, "Duplicate keys not allowed");
		}
		return rebalance(n);

	}

	/**
	 * Balance function of the tree. Done as a part of the Insertion function.
	 * @param n the new inserted node to the tree.
	 * @return a node that was rebalanced from.
	 */
	private AVLNode<T> rebalance(AVLNode<T> n) {
		updateHeight(n);
		int balance = getBalance(n);
		if (balance > 1) {
			AVLNode<T> left = n.getLeftChild();
			if (left == null) {
				n = rotateLeft(n);
			} else if (getHeight(n.getRightChild().getRightChild()) > getHeight(n.getLeftChild().getRightChild())) {
				n = rotateLeft(n);
			} else {
				n.setRightChild(rotateRight(n.getRightChild()));
				n = rotateLeft(n);
			}
		} else if (balance < -1) {
			AVLNode<T> right = n.getRightChild();
			if (right == null) {
				n = rotateRight(n);
			} else if (getHeight(n.getLeftChild().getLeftChild()) > getHeight(n.getLeftChild().getRightChild())) {
				n = rotateRight(n);
			} else {
				n.setLeftChild(rotateLeft(n.getLeftChild()));
				n = rotateRight(n);
			}
		}
		return n;
	}

	/**
	 * Search a key in the AVL tree and return it's node's data.
	 * @param key a key to search.
	 * @return the data of the node that matches the searched key.
	 */
	public T search(int key) {
		AVLNode<T> curr = getRoot();
		while (curr != null) {
			if (curr.getKey() == key) {
				break;
			}
			curr = curr.getKey() < key ? curr.getRightChild() : curr.getLeftChild();
			if(curr == null) {
				return null;
			}
		}
		return curr.getData();
	}

	/**
	 * Create a linked list of all the X values in a given AVL tree that their keys
	 * are inside a given range (include edges).
	 * @param a the lower value of the range.
	 * @param b the higher value of the range.
	 * @param n a node to start search if it's key is in the range.
	 * @param l a linked list to contain the nodes.
	 * @return the linked list.
	 */
	public LinkedList<AVLNode<ObjectWithCoordinates>> insidePointsX(int a, int b, AVLNode<ObjectWithCoordinates> n,
			LinkedList<AVLNode<ObjectWithCoordinates>> l) {
		if (n == null){ return null;}
		if (a < n.getData().getX()) {
			insidePointsX(a, b, n.getLeftChild(), l);
		}
		if (a <= n.getData().getX() && b >= n.getData().getX()) {
			l.add(n);
		}
		if (b > n.getData().getX()) {
			insidePointsX(a, b, n.getRightChild(), l);
		}
		return l;
	}

	/**
	 * Create a linked list of all the Y values in a given AVL tree that their keys
	 * are inside a given range (include edges).
	 * @param a the lower value of the range.
	 * @param b the higher value of the range.
	 * @param n a node to start search if it's key is in the range.
	 * @param l a linked list to contain the nodes.
	 * @return the linked list.
	 */
	public LinkedList<AVLNode<ObjectWithCoordinates>> insidePointsY(int a, int b, AVLNode<ObjectWithCoordinates> n,
			LinkedList<AVLNode<ObjectWithCoordinates>> l) {
		if (n == null){ return null;}
		if (a < n.getData().getY()) {
			insidePointsY(a, b, n.getLeftChild(), l);
		}
		if (a <= n.getData().getY() && b >= n.getData().getY()) {
			l.add(n);
		}
		if (b > n.getData().getY()) {
			insidePointsY(a, b, n.getRightChild(), l);
		}
		return l;
	}
}
