package project.game.snake.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {
    private int x;
    private int y;
    private int value;

    public Point(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return x * 1000 + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Point other = (Point) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public Point clone() {
        return new Point(x, y, value);
    }

    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + ", value=" + value + "]";
    }

}
