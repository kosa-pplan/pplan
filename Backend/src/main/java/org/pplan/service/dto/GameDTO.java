package org.pplan.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GameDTO {
    private Long id;
    private String category;
    private String location_name;
    private String address;
    private String business;

}
