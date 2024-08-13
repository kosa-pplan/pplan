package org.pplan.controller.course;

import lombok.RequiredArgsConstructor;
import org.pplan.service.Course.CourseService;
import org.pplan.service.dto.Course.CourseDTO;
import org.pplan.service.dto.Course.PlaceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor

public class CourseRestController {

    @Autowired
    private CourseService courseService;

    /*
     * @Author KyeongMin
     * 코스에 따른 리뷰아이디 가져오기
     * */
    @GetMapping("/api/reviewId/{courseId}")
    public ResponseEntity<Integer> getReviewId(@PathVariable("courseId") String courseId) {

        int reviewId = courseService.getReviewId(Integer.parseInt(courseId));
        return ResponseEntity.ok(reviewId);
    }

    @DeleteMapping("/api/delete/{courseId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long courseId) {
        System.out.println("ID: "+courseId);
        try {
            courseService.deleteCourse(courseId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/api/course/id")
    public List<CourseDTO> getCourse(@RequestParam int id) {
        return courseService.getCourseById(id); // 서비스에서 CourseDTO를 반환
    }

    @GetMapping("/api/course/userid")
    @CrossOrigin
    public List<CourseDTO> getCoursesById(String email) {
        // 데이터베이스에서 ID로 내가 저장한 ourse 정보를 가져오는 로직

        return courseService.getCoursesByEmail(email);
    }

    @GetMapping("/api/course/all")
    @CrossOrigin
    public List<CourseDTO> getAllCourses(String email) {
        // 데이터베이스에서 ID로 좋아요 누른 Course 정보를 가져오는 로직
        return courseService.getAllCourses(email);
    }


    @PostMapping("/api/course/save")
    @CrossOrigin
    public String receiveString(@RequestBody List<Map<String, String>> data) {

        // Total number of PlaceDTO objects needed
        int totalPlaces = 5;

        // Ensure the data list has the correct number of elements
        while (data.size() < totalPlaces) {
            Map<String, String> emptyMap = new HashMap<>();
            emptyMap.put("address", "");
            emptyMap.put("name", "");
            emptyMap.put("category", "없음");
            emptyMap.put("business", "없음");
            data.add(emptyMap);
        }


        // Convert List<PlaceDTO> to CourseDTO
        CourseDTO courseDTO = new CourseDTO();
        // Set places from data to CourseDTO
        if (data.size() > 0) {
            PlaceDTO placeDTO = new PlaceDTO();
            placeDTO.setAddress(data.get(0).get("address"));
            placeDTO.setLocationName(data.get(0).get("name"));
            placeDTO.setCategory(data.get(0).get("category"));
            placeDTO.setBusiness(data.get(0).get("business"));
            courseDTO.setPlaceDTO1(placeDTO);
        }
        if (data.size() > 1) {
            PlaceDTO placeDTO = new PlaceDTO();
            placeDTO.setAddress(data.get(1).get("address"));
            placeDTO.setLocationName(data.get(1).get("name"));
            placeDTO.setCategory(data.get(1).get("category"));
            placeDTO.setBusiness(data.get(1).get("business"));
            courseDTO.setPlaceDTO2(placeDTO);
        }
        if (data.size() > 2) {
            PlaceDTO placeDTO = new PlaceDTO();
            placeDTO.setAddress(data.get(2).get("address"));
            placeDTO.setLocationName(data.get(2).get("name"));
            placeDTO.setCategory(data.get(2).get("category"));
            placeDTO.setBusiness(data.get(2).get("business"));
            courseDTO.setPlaceDTO3(placeDTO);
        }
        if (data.size() > 3) {
            PlaceDTO placeDTO = new PlaceDTO();
            placeDTO.setAddress(data.get(3).get("address"));
            placeDTO.setLocationName(data.get(3).get("name"));
            placeDTO.setCategory(data.get(3).get("category"));
            placeDTO.setBusiness(data.get(3).get("business"));
            courseDTO.setPlaceDTO4(placeDTO);
        }
        if (data.size() > 4) {
            PlaceDTO placeDTO = new PlaceDTO();
            placeDTO.setAddress(data.get(4).get("address"));
            placeDTO.setLocationName(data.get(4).get("name"));
            placeDTO.setCategory(data.get(4).get("category"));
            placeDTO.setBusiness(data.get(4).get("business"));
            courseDTO.setPlaceDTO5(placeDTO);
        }

        courseDTO.setReviewCheck("NO"); // or some logic to set reviewCheck


        // Set static user information
        courseDTO.setUserEmail(data.get(0).get("email"));
        courseDTO.setTitle(data.get(0).get("title"));


        courseService.saveCourse(courseDTO);

        return "Data received";
    }
}
