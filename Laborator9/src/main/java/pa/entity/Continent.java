package pa.entity;

import javax.persistence.*;

@Entity
@Table(name = "CONTINENTS")
@NamedQueries({
        @NamedQuery(name = "Continent.getAll",
                query = "select c from Continent c"),

        @NamedQuery(name = "Continent.findByID",
                query = "select c from Continent c where c.id = ?1"),

        @NamedQuery(name = "Continent.findByName",
                query = "select c from Continent c where c.name like ?1")
})
public class Continent {
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NAME", length = 64)
    private String name;

    public Continent() {

    }

    public Continent(String name) {
        this.name = name;
    }

    public Continent(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Continent{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public void become(Continent other) {
        this.id = other.getId();
        this.name = other.getName();
    }
}