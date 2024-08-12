package org.pplan.service.dto.Course;

import lombok.Data;

@Data
public class CourseDTO {
    private String reviewCheck;
    private PlaceDTO placeDTO1;
    private PlaceDTO placeDTO2;
    private PlaceDTO placeDTO3;
    private PlaceDTO placeDTO4;
    private PlaceDTO placeDTO5;
    private String userEmail;
    private Long userId;
    private String title;

    // Constructors
    public CourseDTO() {}

    public CourseDTO(String reviewCheck, PlaceDTO placeDTO1, PlaceDTO placeDTO2, PlaceDTO placeDTO3, PlaceDTO placeDTO4, PlaceDTO placeDTO5, String userEmail, Long userId, String title) {
        this.reviewCheck = reviewCheck;
        this.placeDTO1 = placeDTO1;
        this.placeDTO2 = placeDTO2;
        this.placeDTO3 = placeDTO3;
        this.placeDTO4 = placeDTO4;
        this.placeDTO5 = placeDTO5;
        this.userEmail = userEmail;
        this.userId = userId;
        this.title = title;

    }
    @Override
    public String toString() {
        return "CourseDTO{" +
                "reviewCheck='" + reviewCheck + '\'' +
                ", placeDTO1=" + placeDTO1 + '\'' +
                ", placeDTO2=" + placeDTO2 + '\'' +
                ", placeDTO3=" + placeDTO3 + '\'' +
                ", placeDTO4=" + placeDTO4 + '\'' +
                ", placeDTO5=" + placeDTO5 + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userId=" + userId + '\'' +
                ", title=" +
                '}';
    }
}
