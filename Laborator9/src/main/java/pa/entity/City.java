package pa.entity;

import javax.persistence.*;

@Entity
@Table(name = "CITIES")
@NamedQueries({
        @NamedQuery(name = "City.getAll",
                query = "select c from City c"),

        @NamedQuery(name = "City.findByID",
                query = "select c from City c where c.id = ?1"),

        @NamedQuery(name = "City.findByName",
                query = "select c from City c where c.name like ?1")
})
public class City {
    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private long id;

    @Column(name = "NAME", length = 64)
    private String name;

    
    @Column(name = "IS_CAPITAL")
    private Long isCapital = 0l;

    @Column(name = "LATITUDE")
    private Float latitude;

    @Column(name = "LONGITUDE")
    private Float longitude;

    @ManyToOne
    @JoinColumn(name = "COUNTRY_ID")
    private Country country;

    @Column(name = "POPULATION")
    private Long population;

    public City() {
    }

    public City(String name, Country country, Long isCapital, float latitude, float longitude, Long population) {
        this.name = name;
        this.country = country;
        this.isCapital = isCapital;
        this.latitude = latitude;
        this.longitude = longitude;
        this.population = population;
    }

    public City(String name, Country country, Long isCapital, float latitude, float longitude) {
        this.name = name;
        this.country = country;
        this.isCapital = isCapital;
        this.latitude = latitude;
        this.longitude = longitude;
        this.population = null;
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

    public Long getIsCapital() {
        return isCapital;
    }

    public void setIsCapital(Long isCapital) {
        this.isCapital = isCapital;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Long getPopulation() {
        return this.population;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", isCapital=" + isCapital +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", country=" + country.getName() +
                '}';
    }

    public void become(City other) {
        this.id = other.getId();
        this.name = other.getName();
        this.isCapital = other.getIsCapital();
        this.longitude = other.getLongitude();
        this.latitude = other.getLatitude();
        this.country = other.getCountry();
    }
}