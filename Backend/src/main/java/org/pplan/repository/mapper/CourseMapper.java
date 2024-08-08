package org.pplan.repository.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.pplan.service.dto.CourseDTO;

@Mapper
public interface CourseMapper {
    void insertCourse(CourseDTO courseDTO);

    CourseDTO findById(@Param("id") Long id);
}
