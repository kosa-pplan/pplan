package org.pplan.service.Course;

import org.pplan.repository.mapper.CourseMapper;
import org.pplan.service.dto.Course.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    public void saveCourse(CourseDTO course) {
        System.out.println("변환테스트2: " + course);
        System.out.println("변환테스트3: " + course.getPlace3().getAddress());
        courseMapper.insertCourse(course);
    }

    public CourseDTO getCourseById(Long id) {
        System.out.println(id);
        System.out.println("불러오기 테스트"+courseMapper.findById(id));
        return courseMapper.findById(id);
    }
}
