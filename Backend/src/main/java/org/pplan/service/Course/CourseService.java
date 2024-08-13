package org.pplan.service.Course;

import org.pplan.repository.mapper.CourseMapper;
import org.pplan.service.dto.Course.CourseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    public void saveCourse(CourseDTO course) {
        System.out.println("변환테스트2: " + course);
        courseMapper.insertCourse(course);
    }

    //course id로 가져오기
    public List<CourseDTO> getCourseById(int id) {
        return courseMapper.getById(id);
    }

    // 특정 ID로 여러 CourseDTO 항목을 반환하는 메소드
    public List<CourseDTO> getCoursesByEmail(String email) {
        return courseMapper.findById(email);
    }

    public List<CourseDTO> getAllCourses(String email) {
        return courseMapper.findAllCourses(email);
    }

    public int getReviewId(long courseId){
        return courseMapper.getReviewId(courseId);
    }

    public void deleteCourse(Long courseId) {
        courseMapper.deleteCourse(courseId);
    }
}
