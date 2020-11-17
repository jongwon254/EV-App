package joey.lee.org.myapplication;

import java.util.List;

public class Attributes {

    private String provider;
    private List<Charge_Point_Prices> charge_point_prices;

    public Attributes(String provider, List<Charge_Point_Prices> charge_point_prices) {
        this.provider = provider;
        this.charge_point_prices = charge_point_prices;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<Charge_Point_Prices> getCharge_point_prices() {
        return charge_point_prices;
    }

    public void setCharge_point_prices(List<Charge_Point_Prices> charge_point_prices) {
        this.charge_point_prices = charge_point_prices;
    }
}
