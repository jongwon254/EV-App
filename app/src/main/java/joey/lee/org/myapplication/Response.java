package joey.lee.org.myapplication;

import java.util.List;

public class Response {

    private String status;
    private double startkey;
    private List<Chargelocations> chargelocations;

    public Response(String status, List<Chargelocations> chargelocations, double startkey) {
        this.status = status;
        this.chargelocations = chargelocations;
        this.startkey = startkey;
    }

    public double getStartkey() {
        return startkey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Chargelocations> getChargelocations() {
        return chargelocations;
    }

    public void setChargelocations(List<Chargelocations> chargelocations) {
        this.chargelocations = chargelocations;
    }
}


