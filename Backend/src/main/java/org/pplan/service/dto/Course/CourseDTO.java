package org.pplan.service.dto.Course;

import lombok.Data;

@Data
public class CourseDTO {
    private int courseId;
    private String reviewCheck;
    private String title;
    private PlaceDTO placeDTO1;
    private PlaceDTO placeDTO2;
    private PlaceDTO placeDTO3;
    private PlaceDTO placeDTO4;
    private PlaceDTO placeDTO5;
    private String userEmail;


    // Constructors
    public CourseDTO() {}

    public CourseDTO(String reviewCheck, PlaceDTO placeDTO1, PlaceDTO placeDTO2, PlaceDTO placeDTO3, PlaceDTO placeDTO4, PlaceDTO placeDTO5, String userEmail, int courseId, String title) {
        this.courseId = courseId;
        this.reviewCheck = reviewCheck;
        this.title = title;
        this.placeDTO1 = placeDTO1;
        this.placeDTO2 = placeDTO2;
        this.placeDTO3 = placeDTO3;
        this.placeDTO4 = placeDTO4;
        this.placeDTO5 = placeDTO5;
        this.userEmail = userEmail;
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
                ", courseId=" + courseId + '\'' +
                ", title=" + title +
                '}';
    }
}
