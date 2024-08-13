package org.pplan.repository.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.pplan.service.dto.Course.CourseDTO;

import java.util.List;

@Mapper
public interface CourseMapper {
    void insertCourse(CourseDTO courseDTO);

    //CourseDTO findById(@Param("id") int id);
    List<CourseDTO> getById(int id);

    List<CourseDTO> findById(String email);

    List<CourseDTO> findAllCourses(String email);

    int getReviewId(long courseId);

    void deleteCourse(Long courseId);
}
