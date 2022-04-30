package pa;

public class Continent {
    private long id;
    private String name;

    Continent() {
    }

    public Continent(String name) {
        this.name = name;
    }

    Continent(long id, String name) {
        this.id = id;
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Continent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
