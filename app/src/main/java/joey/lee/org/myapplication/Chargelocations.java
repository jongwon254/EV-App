package joey.lee.org.myapplication;

import java.util.List;

public class Chargelocations {

    private int ge_id;
    private String name;
    private String network;
    private String url;
    private boolean fault_report;
    private boolean verified;

    private List<Chargepoints> chargepoints;
    private Address address;
    private Coordinates coordinates;

    public Chargelocations(int ge_id, String name, String network, String url, boolean fault_report, boolean verified, List<Chargepoints> chargepoints, Address address, Coordinates coordinates) {
        this.ge_id = ge_id;
        this.name = name;
        this.network = network;
        this.url = url;
        this.fault_report = fault_report;
        this.verified = verified;
        this.chargepoints = chargepoints;
        this.address = address;
        this.coordinates = coordinates;
    }

    public int getGe_id() {
        return ge_id;
    }

    public void setGe_id(int ge_id) {
        this.ge_id = ge_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isFault_report() {
        return fault_report;
    }

    public void setFault_report(boolean fault_report) {
        this.fault_report = fault_report;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public List<Chargepoints> getChargepoints() {
        return chargepoints;
    }

    public void setChargepoints(List<Chargepoints> chargepoints) {
        this.chargepoints = chargepoints;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
