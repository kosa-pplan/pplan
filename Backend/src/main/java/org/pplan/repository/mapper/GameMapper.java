package org.pplan.repository.mapper;

import org.apache.ibatis.annotations.*;
import org.pplan.service.dto.GameDTO;
import java.util.List;

@Mapper
public interface GameMapper {

    GameDTO getGameById(Long id);

    List<GameDTO> getGameByCategory(String category);

    List<GameDTO> getAll();

    void insertLocation(GameDTO gameDTO);

    void updateLocation(GameDTO gameDTO);

    void deleteLocation(Long id);
}
