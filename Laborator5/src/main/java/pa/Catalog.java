package pa;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Catalog {
    private String title;
    private List<CatalogItem> items;

    Catalog() {
        this.title = null;
        this.items = null;
    }

    Catalog(String title) {
        this.title = title;
        this.items = new ArrayList<>();
    }

    @JsonCreator
    Catalog(@JsonProperty("title") String title, @JsonProperty("items") List<CatalogItem> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public List<CatalogItem> getItems() {
        return items;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setItems(List<CatalogItem> items) {
        this.items = items;
    }

    public void add(CatalogItem item) {
        if(item == null) return; //TODO: make sure the items id are unique
        items.add(item);
    }

    public CatalogItem findById(String id) {
        return items.stream()
                .filter( (CatalogItem it) -> it.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "title='" + title + '\'' +
                ", items=" + items +
                '}';
    }
}
