package alda.t4.hash;

/*
 * Denna klass ska förberedas för att kunna användas som nyckel i en hashtabell. 
 * Du får göra nödvändiga ändringar även i klasserna MyString och ISBN10.
 * 
 * Hashkoden ska räknas ut på ett effektivt sätt och följa de regler och 
 * rekommendationer som finns för hur en hashkod ska konstrueras. Notera i en 
 * kommentar i koden hur du har tänkt när du konstruerat din hashkod.
 */
public class Book {
	private MyString title;
	private MyString author;
	private ISBN10 isbn;
	private MyString content;
	private int price;

	public Book(String title, String author, String isbn, String content, int price) {
		this.title = new MyString(title);
		this.author = new MyString(author);
		this.isbn = new ISBN10(isbn);
		this.content = new MyString(content);
		this.price = price;
	}

	public MyString getTitle() {
		return title;
	}

	public MyString getAuthor() {
		return author;
	}

	public ISBN10 getIsbn() {
		return isbn;
	}

	public MyString getContent() {
		return content;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	/**
	 * Jag valde att göra hashmetoden på detta sett för att ha bra spridning genom att använda
	 * primtalet 31, jag valde sedan att använda ett värder som kommer ofta var unikt och inte
	 * kommer ändras, isbn det är en simple implementation men gör ändå jobbet på att sprida ut
	 * värderna och vara baserat på något specifickt inom klassen.
	 */
	public int hashCode() {
		int hashVal = 0;

		for(int i = 0; i < isbn.size(); i++) {
			hashVal = 31 * hashVal + isbn.charAt(i);
		}

		return hashVal;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		Book other = (Book) o;

		if(this.isbn.size() != other.isbn.size()) {
			return false;
		}

		for(int i = 0; i < isbn.size(); i++) {
			if(this.isbn.charAt(i) != other.isbn.charAt(i)) {
				return false;
			}
		}

		return true;
	}

	@Override
	public String toString() {
		return String.format("\"%s\" by %s Price: %d ISBN: %s lenght: %s", title, author, price, isbn,
				content.length());
	}

}
