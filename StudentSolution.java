import java.util.LinkedList;
import java.util.ListIterator;

public class StudentSolution  implements MyInterface{
	private AVL<ObjectWithCoordinates> avlX;
	private AVL<ObjectWithCoordinates> avlY;
	private HashTable hash;


	/**
	 * Constructor of StudentSolution.
	 * Create two empty AVL trees.
	 */
	public StudentSolution() {
		this.avlX = new AVL<ObjectWithCoordinates>();
		this.avlY = new AVL<ObjectWithCoordinates>();
	}

	/**
	 * Insert data from DB to the two AVL trees.
	 * @param objectName name of a point from the DB.
	 * @param objectX x value  of a point from the DB.
	 * @param objectY y value  of a point from the DB.
	 */
	public void insertDataFromDBFile(String objectName, int objectX, int objectY) {
		Point p = new Point(objectName, objectX, objectY);
		avlX.insert(objectX, p);
		avlY.insert(objectY, p);
	}
	
	//first solution
	public String[] firstSolution(int leftTopX, int leftTopY, int rightBottomX,
			int rightBottomY) {
		
		// create AVL lists of X values an Y values
		LinkedList<AVLNode<ObjectWithCoordinates>> listX = new LinkedList<AVLNode<ObjectWithCoordinates>>();
		listX = avlX.insidePointsX(leftTopX, rightBottomX, avlX.getRoot(), listX);
		LinkedList<AVLNode<ObjectWithCoordinates>> listY = new LinkedList<AVLNode<ObjectWithCoordinates>>();
		listY = avlY.insidePointsY(rightBottomY, leftTopY, avlY.getRoot(), listY);
		
		hash = new HashTable(listX.size());
		
		// if we have x values insert to hash table
		if (listX.size() != 0) {
			for (int i = 0; i < listX.size(); i++) {
				hash.insert(listX.get(i).getData());
			}
		}
		else {
			return null;
		}

		LinkedList<ObjectWithCoordinates> l = new LinkedList<ObjectWithCoordinates>();
		ListIterator<AVLNode<ObjectWithCoordinates>> it = listY.listIterator();
		while (it.hasNext()) {
			ObjectWithCoordinates curr = it.next().getData();
			int x = curr.getX();
			int y = curr.getY();
			ObjectWithCoordinates p = hash.search(x, y);
			if (p != null) {
				l.add(curr);
			}
		}

		if (l.size() == 0)
			return null;
		else {
			String[] ans = new String[l.size()];
			for (int i = 0; i < ans.length; i++) {
				ans[i] = l.get(i).getData().toString();
			}
			return ans;
		}
	}
	

	//second solution
	public String[] secondSolution(int leftTopX, int leftTopY,
			int rightBottomX, int rightBottomY) {

		// create AVL lists of X values an Y values
		LinkedList<AVLNode<ObjectWithCoordinates>> listX = new LinkedList<AVLNode<ObjectWithCoordinates>>();
		listX = avlX.insidePointsX(leftTopX, rightBottomX, avlX.getRoot(), listX);
		LinkedList<AVLNode<ObjectWithCoordinates>> listY = new LinkedList<AVLNode<ObjectWithCoordinates>>();
		listY = avlY.insidePointsY(rightBottomY, leftTopY, avlY.getRoot(), listY);
		
		String[] ans = null;
		int count = 0;
		AVL<ObjectWithCoordinates> avl = new AVL<ObjectWithCoordinates>();

		// if listY is shorter than listX, search by listY
		if(listY.size() <= listX.size()) {

			// convert list to AVL tree
			ListIterator<AVLNode<ObjectWithCoordinates>> it = listX.listIterator();
			while (it.hasNext()) {
				AVLNode<ObjectWithCoordinates> curr = it.next();
				int keyX = curr.getData().getX();
				ObjectWithCoordinates dataX = curr.getData();
				avl.insert(keyX, dataX);
			}
			// search
			it = listY.listIterator();
			while (it.hasNext()) {
				AVLNode<ObjectWithCoordinates> curr = it.next();
				int keyY = curr.getData().getX();
				ObjectWithCoordinates p = avl.search(keyY);
				if(p!= null) {
					count++;
				}
			}

			// search and insert to array
			ans = new String[count];
			count = 0; // index for inserting values to ans
			for (int i = 0; i < listY.size(); i++) {
				int keyY = listY.get(i).getData().getX();
				ObjectWithCoordinates p = avl.search(keyY);
				if(p!= null) {
					ans[count] = p.getData().toString();
					count++;
				}
			}
			
		}
		// if listY is longer than listX, search by listX
		else {
			//inserting y values to avl
			for (int i = 0; i < listY.size(); i++) {
				int keyY = listY.get(i).getData().getX();
				ObjectWithCoordinates dataY = listY.get(i).getData();
				avl.insert(keyY, dataY);
			}
			//searching for x values in avl 
			for (int i = 0; i < listX.size(); i++) {
				int keyX = listX.get(i).getData().getX();
				ObjectWithCoordinates p = avl.search(keyX);
				if(p!= null) {
					count++;
				}
			}
			ans = new String[count];
			count = 0;
			//inserting to ans the crossed values
			for (int i = 0; i < listX.size(); i++) {
				ObjectWithCoordinates p = avl.search(listX.get(i).getData().getX());
				if(p!= null) {
					ans[count] = p.getData().toString();
					count++;
				}
			}
		}
				
		return ans;
	}
}
