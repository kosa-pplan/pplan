package org.pplan.controller.course;

import org.pplan.service.dto.CourseDTO;
import org.pplan.service.dto.PlaceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.pplan.service.CourseService;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CourseRestController {

    @Autowired
    private CourseService courseService;


    @PostMapping("/api/test")
    @CrossOrigin
    public String receiveString(@RequestBody List<Map<String, String>> data) {
        System.out.println("Received data: " + data);
        System.out.println("Received data2: " + data.get(0));

        // Total number of PlaceDTO objects needed
        int totalPlaces = 5;

        // Ensure the data list has the correct number of elements
        while (data.size() < totalPlaces) {
            Map<String, String> emptyMap = new HashMap<>();
            emptyMap.put("address", "");
            emptyMap.put("name", "");
            data.add(emptyMap);
        }

        
        // Convert List<PlaceDTO> to CourseDTO
        CourseDTO courseDTO = new CourseDTO();

        // Set places from data to CourseDTO
        if (data.size() > 0) {
            PlaceDTO placeDTO = new PlaceDTO();
            placeDTO.setAddress(data.get(0).get("address"));
            placeDTO.setLocationName(data.get(0).get("name"));
            courseDTO.setPlace1(placeDTO);
        }
        if (data.size() > 1) {
            PlaceDTO placeDTO = new PlaceDTO();
            placeDTO.setAddress(data.get(1).get("address"));
            placeDTO.setLocationName(data.get(1).get("name"));
            courseDTO.setPlace2(placeDTO);
        }
        if (data.size() > 2) {
            PlaceDTO placeDTO = new PlaceDTO();
            placeDTO.setAddress(data.get(2).get("address"));
            placeDTO.setLocationName(data.get(2).get("name"));
            courseDTO.setPlace3(placeDTO);
        }
        if (data.size() > 3) {
            PlaceDTO placeDTO = new PlaceDTO();
            placeDTO.setAddress(data.get(3).get("address"));
            placeDTO.setLocationName(data.get(3).get("name"));
            courseDTO.setPlace4(placeDTO);
        }
        if (data.size() > 4) {
            PlaceDTO placeDTO = new PlaceDTO();
            placeDTO.setAddress(data.get(4).get("address"));
            placeDTO.setLocationName(data.get(4).get("name"));
            courseDTO.setPlace5(placeDTO);
        }

        courseDTO.setReviewCheck("N"); // or some logic to set reviewCheck


        // Set static user information
        courseDTO.setUserEmail("test@gmail.com");
        courseDTO.setUserId(123); // Example user ID


        System.out.println("변환테스트: " + courseDTO);
        System.out.println("변환테스트: " + courseDTO.getPlace1());

        courseService.saveCourse(courseDTO);
        return "Data received";
    }
}
