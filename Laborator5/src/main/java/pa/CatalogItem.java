package pa;

public abstract class CatalogItem {

    private String id;
    private String title;
    private String location;

    public CatalogItem(String id, String title, String location) {
        this.id = id;
        this.title = title;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLocation() {
        return location;
    }
}
