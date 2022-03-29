package pa;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Website extends CatalogItem {
    Date lastAccessTime;

    @JsonCreator
    public Website(@JsonProperty("id") String id,@JsonProperty("title") String title,@JsonProperty("location") String location,
                   @JsonProperty("lastAccessTime") Date lastAccessTime) {
        super(id, title, location);
        this.lastAccessTime = lastAccessTime;
    }

    @Override
    public String toString() {
        return "Website{" +
                "item=" + super.toString() + ", " +
                "lastAccessTime=" + lastAccessTime +
                '}';
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }
}
