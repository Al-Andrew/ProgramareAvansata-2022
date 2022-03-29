package pa;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Book extends CatalogItem {
    private String author;

   @JsonCreator
    public Book(@JsonProperty("id") String id,@JsonProperty("title") String title,@JsonProperty("location") String location,
                @JsonProperty("author") String author) {
        super(id, title, location);
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "item=" + super.toString() + ", " +
                "author='" + author + '\'' +
                '}';
    }

    public String getAuthor() {
        return author;
    }
}
