package org.pplan.service.dto.Course;

public class PlaceDTO {
    private String locationName;
    private String address;
    private String category;
    private String business;

    // Constructors
    public PlaceDTO() {}

    public PlaceDTO(String locationName, String address, String category, String business) {
        this.locationName = locationName;
        this.address = address;
        this.category = category;
        this.business = business;
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

    public String getCategory(){return category;}
    public void setCategory(String category){this.category = category;}
    public String getBusiness(){return business;}
    public void setBusiness(String business){this.business = business;}
    @Override
    public String toString() {
        return "PlaceDTO{" +
                "locationName='" + locationName + '\'' +
                ", address='" + address + '\'' +
                ", category='" + category + '\'' +
                ", business='" + business + '\'' +
                '}';
    }
}
