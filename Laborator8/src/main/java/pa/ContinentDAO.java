package pa;

import java.util.List;

public interface ContinentDAO {

    List<Continent> getAll();
    Continent findById(long id);
    Continent findByName(String name);
    boolean insertContinent(Continent continent);
    boolean updateContinent(long id, Continent newContinent);
    boolean deleteContinent(Continent continent);
}
