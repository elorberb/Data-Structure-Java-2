
import java.util.LinkedList;
import java.util.ListIterator;

public class HashTable {

	private LinkedList[] hash;

	/**
	 * Constructor
	 * @param m the length of the hash table array.
	 */
	public HashTable(int m) {
		hash = new LinkedList[m];
	}

	public void insert(ObjectWithCoordinates object) {
		int i = hashFunc(object);
		if (hash[i] == null) {
			hash[i] = new LinkedList();
		}
		hash[i].addFirst(object);
	}

	/**
	 * Hash Function.
	 * @param point a point to implement the Hash Function on.
	 * @return the index to insert the point in the hash table.
	 */
	private int hashFunc(ObjectWithCoordinates point) {
		int m = hash.length;
		return (point.getX() + point.getY()) % m;
	}

	/**
	 * Search a point in the hash table by int x and y values.
	 * @param x x value of the point.
	 * @param y y value of the point.
	 * @return if found, return Point object of the searched point, else return null.
	 */
	public ObjectWithCoordinates search(int x, int y) {
		ObjectWithCoordinates point = new Point(null, x, y);
		int i = hashFunc(point);
		// is the point exists in the hash table
		if (hash[i] == null) {
			return null;
		} else {
			// if the point exists in the hash table, search in it's linked list
			ListIterator<ObjectWithCoordinates> it = hash[i].listIterator();
			while (it.hasNext()) {
				ObjectWithCoordinates curr = it.next();
				if (curr.getX() == x && curr.getY() == y) {
					return curr;
				}
			}
		}
		return null;
	}
}
