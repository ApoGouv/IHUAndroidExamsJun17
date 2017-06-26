package gr.edu.ihu.gouvalas.apo.ihuandroidexamsjun17;

/**
 * Created by ApoGouv on 26/6/2017.
 */

public class Company {

    private String id;
    private String legalName;
    private String altName;
    private String address;

    public Company(String id, String legalName, String altName, String address) {
        this.id = id;
        this.legalName = legalName;
        this.altName = altName;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getLegalName() {
        return legalName;
    }

    public String getAltName() {
        return altName;
    }

    public String getAddress() {
        return address;
    }
}