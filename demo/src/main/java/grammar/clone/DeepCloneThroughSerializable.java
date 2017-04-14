package grammar.clone;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Author: Johnny Date: 2017/4/6 Time: 20:14
 */
public class DeepCloneThroughSerializable {
	public static void main(String[] args) throws IOException {
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;

		try {
			// 创建原始的可序列化对象
			ColoredCircle c1 = new ColoredCircle(100, 100);
			System.out.println("Original = " + c1);

			ColoredCircle c2;

			// 通过序列化实现深拷贝
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			// 序列化以及传递这个对象
			oos.writeObject(c1);
			oos.flush();
			ByteArrayInputStream bin = new ByteArrayInputStream(
					bos.toByteArray());
			ois = new ObjectInputStream(bin);
			// 返回新的对象
			c2 = (ColoredCircle) ois.readObject();

			// 校验内容是否相同
			System.out.println("Copied   = " + c2);
			// 改变原始对象的内容
			c1.setX(200);
			c1.setY(200);
			// 查看每一个现在的内容
			System.out.println("Original = " + c1);
			System.out.println("Copied   = " + c2);
		} catch (Exception e) {
			System.out.println("Exception in main = " + e);
		} finally {
			oos.close();
			ois.close();
		}
	}
}

class ColoredCircle implements Serializable {
	private static final long serialVersionUID = 609791557237372124L;
	
	private int x;
	private int y;

	public ColoredCircle(int x, int y) {
		this.x = x;
		this.y = y;
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

	@Override
	public String toString() {
		return "x=" + x + ", y=" + y;
	}
}