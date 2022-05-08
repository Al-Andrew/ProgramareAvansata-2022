package pa.graphics;

import pa.entity.City;

import java.util.ArrayList;
import java.util.List;

public class HighlightData {
    private List<City> cityList = new ArrayList<>();
    private City selected = null;


    public List<City> getCityList() {
        return cityList;
    }

    public void addToCityList(City city) {
        cityList.add(city);
    }

    public boolean isSelected(City candidate) {
        return candidate.equals(selected);
    }

    public void setSelected(City selected) {
        this.selected = selected;
    }

    public void clear() {
        this.cityList.clear();
        selected = null;
    }
}
