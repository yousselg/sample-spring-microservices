package com.yousselg.microservices.player.api;

import java.util.List;

import com.yousselg.microservices.player.exceptions.PlayerNotFoundException;
import com.yousselg.microservices.player.model.Player;
import com.yousselg.microservices.player.service.IPlayerService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController("")
@Slf4j
public class Api {

	@Autowired
	private IPlayerService playerService;

	@RequestMapping(method = RequestMethod.GET, value = "")
	public List<Player> findAll() {
		return this.playerService.findAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public Player findById(@PathVariable final Integer id) throws PlayerNotFoundException {
		return this.playerService.findById(id);
	}

	/**
	 * @param playerId
	 * @param gameId
	 * @return Player
	 * @throws PlayerNotFoundException *
	 *                                 RequestMethod.GET: to be easy to use with the browser
	 *                                 normally it should be POST with an object body
	 */
	@RequestMapping(method = RequestMethod.GET, value = "{playerId}/games/{gameId}")
	public Player addGameId(@PathVariable final Integer playerId, @PathVariable final Integer gameId) throws PlayerNotFoundException {
		return this.playerService.addGameId(playerId, gameId);
	}

	@RequestMapping(method = RequestMethod.POST, value = "")
	public Player createNewPlayer(@RequestBody final Player player) {
		return this.playerService.createNewPlayer(player);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public boolean deletePlayer(@PathVariable final Integer id) {
		return this.playerService.deletePlayer(id);
	}

}
