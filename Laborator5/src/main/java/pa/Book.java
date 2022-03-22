package pa;

public class Book extends CatalogItem {
    private String author;

    public Book(String id, String title, String location, String author) {
        super(id, title, location);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }
}
