
public class Point implements ObjectWithCoordinates {
	private final String name;
	private final int x;
	private final int y;

	/**
	 * Constructor
	 * @param name name of a point.
	 * @param x x value  of a point.
	 * @param y y value  of a point.
	 */
	public Point(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}

	/**
	 * Getter of x value.
	 * @return x value.
	 */
	@Override
	public int getX() {
		return x;
	}

	/**
	 * Getter of y value.
	 * @return y value.
	 */
	@Override
	public int getY() {
		return y;
	}

	/**
	 * Getter of data value.
	 * @return data value as an object.
	 */
	@Override
	public Object getData() {
		return this.name + " X=" + this.x + " Y=" + this.y;
	}

}
