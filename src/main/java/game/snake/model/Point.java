package game.snake.model;

public class Point {
	private int x;
	private int y;
	private int value;
	
	
	public Point() {
		super();
	}
	
	
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}


	public Point(int x, int y, int value) {
		super();
		this.x = x;
		this.y = y;
		this.value = value;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}


	public int hashCode() {
		return x*1000+y;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public Point clone(){
		return new Point(x, y, value);
	}

	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", value=" + value + "]";
	}
	
}
