package org.pplan.service.dto;

import lombok.Data;

@Data
public class GameDTO {
    private Long id;
    private String category;
    private String locationName;
    private double latitude; //x
    private double longitude; //y
    private String address;
    private String foodMenu;
}
