package pa;

public class City {
    private long id;
    private String name;
    private Country country;
    private boolean isCapital;
    private float latitude, longitude;

    public City() {
    }

    public City(String name, Country country, boolean isCapital, float latitude, float longitude) {
        this.name = name;
        this.country = country;
        this.isCapital = isCapital;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public City(long id, String name, Country country, boolean isCapital, float latitude, float longitude) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.isCapital = isCapital;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Country getCountry() {
        return country;
    }

    public boolean isCapital() {
        return isCapital;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country=" + country.getName() +
                ", isCapital=" + isCapital +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
