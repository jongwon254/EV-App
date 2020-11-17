package joey.lee.org.myapplication;

public class Charge_Point_Prices {

    private int power;
    private String plug;
    private double price;

    public Charge_Point_Prices(int power, String plug, double price) {
        this.power = power;
        this.plug = plug;
        this.price = price;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getPlug() {
        return plug;
    }

    public void setPlug(String plug) {
        this.plug = plug;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
