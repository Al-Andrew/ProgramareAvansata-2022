package pa;

public class Country {
    Continent continent;
    private long id;
    private String name;
    private String code;

    public Country() {
    }

    public Country(String name, String code, Continent continent) {
        this.name = name;
        this.code = code;
        this.continent = continent;
    }

    public Country(long id, String name, String code, Continent continent) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.continent = continent;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public Continent getContinent() {
        return continent;
    }

    @Override
    public String toString() {
        return "Country{" +
                "continent=" + continent.getName() +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}
