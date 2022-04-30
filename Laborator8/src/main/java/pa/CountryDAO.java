package pa;

import java.util.List;

public interface CountryDAO {
    List<Country> getAll();
    Country findById(long id);
    Country findByName(String name);
    boolean insertCountry(Country country);
    boolean updateCountry(long id, Country country);
    boolean deleteCountry(Country country);
}
