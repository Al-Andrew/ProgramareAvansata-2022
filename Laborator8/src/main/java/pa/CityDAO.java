package pa;

import java.util.List;

public interface CityDAO {
    List<City> getAll();
    City findById(long id);
    City findByName(String name);
    boolean insertCity(City city);
    boolean updateCity(long id, City city);
    boolean deleteCity(City city);
}
