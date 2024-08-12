package org.pplan.repository.mapper;

import org.apache.ibatis.annotations.*;
import org.pplan.service.dto.GameDTO;
import java.util.List;

@Mapper
public interface GameMapper {

    GameDTO getGameById(Long id);

    List<GameDTO> getGamesByCategoryAndColor(@Param("category") String category, @Param("regions") List<String> regions);

    List<GameDTO> getGameByCategory(String category);

    List<GameDTO> getAll();

    void insertLocation(GameDTO gameDTO);

    void updateLocation(GameDTO gameDTO);

    void deleteLocation(Long id);
}
