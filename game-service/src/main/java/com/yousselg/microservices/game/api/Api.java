package com.yousselg.microservices.game.api;

import java.util.List;

import com.yousselg.microservices.game.exceptions.GameNotFoundException;
import com.yousselg.microservices.game.model.Game;
import com.yousselg.microservices.game.service.IGameService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@Slf4j
public class Api {

	@Autowired
	private IGameService gameService;

	/**
	 * @param gameName
	 * @param firstPlayerId
	 * @param secondPlayerId
	 * @return Game
	 * <p>
	 * * RequestMethod.GET: to be easy to use with the browser
	 * normally it should be POST with an object body
	 */
	@RequestMapping(method = RequestMethod.GET, value = "create")
	public Game createNewGame(@RequestParam("name") final String gameName,
			@RequestParam("firstPlayerId") final Integer firstPlayerId,
			@RequestParam("secondPlayerId") final Integer secondPlayerId) {
		return this.gameService.createNewGame(gameName, firstPlayerId, secondPlayerId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{gameId}/{playerId}")
	public Game playerScores(@PathVariable("gameId") final Integer gameId, @PathVariable("playerId") final Integer playerId) throws GameNotFoundException {
		log.info("Game.findAll()");
		return this.gameService.playerScores(gameId, playerId);

	}

	@RequestMapping(method = RequestMethod.GET, value = "")
	public List<Game> findAll() {
		return this.gameService.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public Game findById(@PathVariable("id") final Integer id) throws GameNotFoundException {
		return this.gameService.findById(id);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public boolean deleteGame(@PathVariable final Integer id) {
		return this.gameService.deleteGame(id);
	}

}
