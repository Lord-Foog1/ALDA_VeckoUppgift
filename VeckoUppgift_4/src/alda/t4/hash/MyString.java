package alda.t4.hash;

public class MyString {

	private char[] data;
	
	public MyString(String title) {
		data = title.toCharArray();
	}

	public int length() {
		return data.length;
	}

	public char charAt(int index) throws IndexOutOfBoundsException {
		if(index >= data.length) {
			throw new IndexOutOfBoundsException("Index out of bounds!");
		}
		return data[index];
	}
	
	@Override
	public String toString() {
		return new String(data);
	}

}
