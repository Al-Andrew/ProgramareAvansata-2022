package pa.entity;

import javax.persistence.*;

@Entity
@Table(name = "COUNTRIES")
@NamedQueries({
        @NamedQuery(name = "Country.getAll",
                query = "select c from Continent c"),

        @NamedQuery(name = "Country.findByID",
                query = "select c from Country c where c.id = ?1"),

        @NamedQuery(name = "Country.findByName",
                query = "select c from Country c where c.name like ?1")
})
public class Country {
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", length = 64)
    private String name;

    @Column(name = "CODE", length = 3)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTINENT_ID")
    private Continent continent;

    public Country() {
    }

    public Country(String name, String code, Continent continent) {
        this.name = name;
        this.code = code;
        this.continent = continent;
    }

    public Country(Long id, String name, String code, Continent continent) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.continent = continent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", continent=" + continent.getId() +
                '}';
    }

    public void become(Country other) {
        this.id = other.getId();
        this.name = other.getName();
        this.code = other.getCode();
        this.continent = other.getContinent();
    }
}