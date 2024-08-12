package org.pplan.repository.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.pplan.service.dto.Course.CourseDTO;

import java.util.List;

@Mapper
public interface CourseMapper {
    void insertCourse(CourseDTO courseDTO);

    //CourseDTO findById(@Param("id") int id);

    List<CourseDTO> findById(Long id);
}
