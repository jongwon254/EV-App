package joey.lee.org.myapplication;

public class Chargepoints {

    private String type;
    private double power;
    private int count;

    public Chargepoints(String type, int power, int count) {
        this.type = type;
        this.power = power;
        this.count = count;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
