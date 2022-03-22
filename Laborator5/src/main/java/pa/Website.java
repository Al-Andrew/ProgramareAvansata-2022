package pa;

import java.util.Date;

public class Website extends CatalogItem {
    Date lastAccessTime;

    public Website(String id, String title, String location, Date lastAccessTime) {
        super(id, title, location);
        this.lastAccessTime = lastAccessTime;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }
}
