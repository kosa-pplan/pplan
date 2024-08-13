package org.pplan.service.Game;

import org.pplan.repository.mapper.GameMapper;
import org.pplan.service.dto.GameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;//컬렉션을 쉽게 생성, 조작, 검색, 정렬, 동기화, 변환

@Service
public class GameService {

    @Autowired
    private GameMapper gameMapper;

    public List<GameDTO> getGamesByCategoryAndColor(String category, String color) {
        List<String> regions = getRegionsByColor(color);
        List<GameDTO> games = gameMapper.getGamesByCategoryAndColor(category, regions); //j

        // 리스트를 무작위로 섞기
        Collections.shuffle(games);

        // 상위 20개의 항목만 반환
        return games.size() > 20 ? games.subList(0, 20) : games;
    }

    private List<String> getRegionsByColor(String color) {
        switch (color.toLowerCase()) {
            case "green":
                return Arrays.asList("강서구", "양천구", "구로구", "영등포구", "동작구", "금천구", "관악구");
            case "purple":
                return Arrays.asList("서초구", "강남구", "송파구", "강동구");
            case "orange":
                return Arrays.asList("은평구", "서대문구", "마포구");
            case "pink":
                return Arrays.asList("종로구", "중구", "용산구");
            case "blue":
                return Arrays.asList("성북구", "동대문구", "성동구", "강북구", "도봉구", "노원구", "중랑구", "광진구");
            default:
                return Collections.emptyList();
        }
    }

    // ID로 게임 데이터 조회
    public GameDTO getGameById(Long id) {
        return gameMapper.getGameById(id);
    }

    // 카테고리로 게임 데이터 조회
    public List<GameDTO> getGameByCategory(String category) {
        return gameMapper.getGameByCategory(category);
    }

    // 게임 데이터 삽입
    public void createGame(GameDTO gameDTO) {
        gameMapper.insertLocation(gameDTO);
    }

    // 게임 데이터 업데이트
    public void updateGame(GameDTO gameDTO) {
        if (gameMapper.getGameById(gameDTO.getId()) != null) {
            gameMapper.updateLocation(gameDTO);
        } else {
            throw new RuntimeException("Game not found with id: " + gameDTO.getId());
        }
    }

    // 게임 데이터 삭제
    public void deleteGame(Long id) {
        if (gameMapper.getGameById(id) != null) {
            gameMapper.deleteLocation(id);
        } else {
            throw new RuntimeException("Game not found with id: " + id);
        }
    }
}
