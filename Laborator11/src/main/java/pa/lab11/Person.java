package pa.lab11;

import java.util.UUID;

public class Person {
    String name;
    UUID id;

    public Person(UUID uuid, String name) {
        this.name = name;
        this.id = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
