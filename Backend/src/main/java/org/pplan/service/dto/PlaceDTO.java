package org.pplan.service.dto;

public class PlaceDTO {
    private String locationName;
    private String address;

    // Constructors
    public PlaceDTO() {}

    public PlaceDTO(String locationName, String address) {
        this.locationName = locationName;
        this.address = address;
    }

    // Getters and Setters
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @Override
    public String toString() {
        return "PlaceDTO{" +
                "locationName='" + locationName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
