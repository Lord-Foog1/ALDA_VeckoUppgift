package alda.t4.hash;

public class Main {
    public static void main(String[] args) {
        Book book = new Book("Book", "Bert Bertsson", "1234567890", "Book Book", 100);

        System.out.println(book.hashCode());
    }
}