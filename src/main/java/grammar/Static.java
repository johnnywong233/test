package grammar;


public class Static {
	/*
	 * �����֪����261��Java��������.03.24
	 * ��̬��ʼ������ڵ�һ�δ�������ʱִ�У��ڶ��ξͲ�ִ��
	 * ����ִ��˳��
	 */
	public static void main(String args[]){
		

        
		T a = new T();
		T a1 = new T();
	}
}

class T{
	{
		System.out.println("--�Ǿ�̬��ʼ����--");
	}
	static {
		System.out.println("--��̬��ʼ����--");
	}
	public T(){
		System.out.println("--�޲εĹ��췽��--");
	}
}
