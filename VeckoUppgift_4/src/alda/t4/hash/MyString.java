package alda.t4.hash;

public class MyString {

	private char[] data;
	
	public MyString(String title) {
		data = title.toCharArray();
	}

	public int length() {
		return data.length;
	}
	
	@Override
	public String toString() {
		return new String(data);
	}
	
}
