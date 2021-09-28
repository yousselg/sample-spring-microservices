package com.yousselg.microservices.game.service;

import java.util.List;

import com.yousselg.microservices.game.exceptions.GameNotFoundException;
import com.yousselg.microservices.game.model.Game;

public interface IGameService {

	Game createNewGame(String gameName,
			Integer firstPlayerId,
			Integer secondPlayerId);

	Game playerScores(Integer gameId, Integer playerId) throws GameNotFoundException;

	List<Game> findAll();

	Game findById(Integer id) throws GameNotFoundException;

	boolean deleteGame(Integer id);
}
