package org.pplan.service.dto;

public class CourseDTO {
    private String reviewCheck;
    private PlaceDTO placeDTO1;
    private PlaceDTO placeDTO2;
    private PlaceDTO placeDTO3;
    private PlaceDTO placeDTO4;
    private PlaceDTO placeDTO5;
    private String userEmail;
    private Integer userId;

    // Constructors
    public CourseDTO() {}

    public CourseDTO(String reviewCheck, PlaceDTO placeDTO1, PlaceDTO placeDTO2, PlaceDTO placeDTO3, PlaceDTO placeDTO4, PlaceDTO placeDTO5, String userEmail, Integer userId) {
        this.reviewCheck = reviewCheck;
        this.placeDTO1 = placeDTO1;
        this.placeDTO2 = placeDTO2;
        this.placeDTO3 = placeDTO3;
        this.placeDTO4 = placeDTO4;
        this.placeDTO5 = placeDTO5;
        this.userEmail = userEmail;
        this.userId = userId;
    }

    // Getters and Setters

    public String getReviewCheck() {
        return reviewCheck;
    }

    public void setReviewCheck(String reviewCheck) {
        this.reviewCheck = reviewCheck;
    }

    public PlaceDTO getPlace1() {
        return placeDTO1;
    }

    public void setPlace1(PlaceDTO placeDTO1) {
        this.placeDTO1 = placeDTO1;
    }

    public PlaceDTO getPlace2() {
        return placeDTO2;
    }

    public void setPlace2(PlaceDTO placeDTO2) {
        this.placeDTO2 = placeDTO2;
    }

    public PlaceDTO getPlace3() {
        return placeDTO3;
    }

    public void setPlace3(PlaceDTO placeDTO3) {
        this.placeDTO3 = placeDTO3;
    }

    public PlaceDTO getPlace4() {
        return placeDTO4;
    }

    public void setPlace4(PlaceDTO placeDTO4) {
        this.placeDTO4 = placeDTO4;
    }

    public PlaceDTO getPlace5() {
        return placeDTO5;
    }

    public void setPlace5(PlaceDTO placeDTO5) {
        this.placeDTO5 = placeDTO5;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    @Override
    public String toString() {
        return "CourseDTO{" +
                ", reviewCheck='" + reviewCheck + '\'' +
                ", placeDTO1=" + placeDTO1 +
                ", placeDTO2=" + placeDTO2 +
                ", placeDTO3=" + placeDTO3 +
                ", placeDTO4=" + placeDTO4 +
                ", placeDTO5=" + placeDTO5 +
                ", userEmail='" + userEmail + '\'' +
                ", userId=" + userId +
                '}';
    }
}
