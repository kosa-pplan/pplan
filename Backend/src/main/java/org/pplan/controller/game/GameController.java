package org.pplan.controller.game ;

import org.pplan.service.Game.GameService;
import org.pplan.service.dto.GameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/{category}/{color}")
    @CrossOrigin
    public List<GameDTO> getGamesByCategoryAndColor(@PathVariable String category, @PathVariable String color) {
        return gameService.getGamesByCategoryAndColor(category, color);
    }


    @GetMapping("/{id}")
    public GameDTO getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    @GetMapping("/category/{category}")
    public List<GameDTO> getGameByCategory(@PathVariable String category) {
        return gameService.getGameByCategory(category);
    }

    @GetMapping
    public List<GameDTO> getAllGames() {
        return gameService.getAllGames();
    }

    @PostMapping
    public void createGame(@RequestBody GameDTO gameDTO) {
        gameService.createGame(gameDTO);
    }

    @PutMapping("/{id}")
    public void updateGame(@PathVariable Long id, @RequestBody GameDTO gameDTO) {
        gameDTO.setId(id);
        gameService.updateGame(gameDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
    }
}
