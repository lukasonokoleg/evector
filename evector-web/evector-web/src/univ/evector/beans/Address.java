package univ.evector.beans;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Address implements Serializable {

    private Long adr_id;

    private Country country;

    private Municipality municipality;

    private Residence residence;

    private Street street;

    private String post_code;

    private String building_no;

    private String house_no;

    private String room_no;

    public Address() {

    }

    public Long getAdr_id() {
        return adr_id;
    }

    public void setAdr_id(Long adr_id) {
        this.adr_id = adr_id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Municipality getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    public Residence getResidence() {
        return residence;
    }

    public void setResidence(Residence residence) {
        this.residence = residence;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public String getPost_code() {
        return post_code;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }

    public String getBuilding_no() {
        return building_no;
    }

    public void setBuilding_no(String building_no) {
        this.building_no = building_no;
    }

    public String getHouse_no() {
        return house_no;
    }

    public void setHouse_no(String house_no) {
        this.house_no = house_no;
    }

    public String getRoom_no() {
        return room_no;
    }

    public void setRoom_no(String room_no) {
        this.room_no = room_no;
    }

    @Override
    public String toString() {
        return "Address [adr_id=" + adr_id + ", country=" + country + ", municipality=" + municipality + ", residence=" + residence + ", street=" + street + ", post_code="
                + post_code + ", building_no=" + building_no + ", house_no=" + house_no + ", room_no=" + room_no + "]";
    }

}