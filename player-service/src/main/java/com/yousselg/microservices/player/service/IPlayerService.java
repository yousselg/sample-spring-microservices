package com.yousselg.microservices.player.service;

import java.util.List;

import com.yousselg.microservices.player.exceptions.PlayerNotFoundException;
import com.yousselg.microservices.player.model.Player;

public interface IPlayerService {
	List<Player> findAll();

	Player findById(Integer id) throws PlayerNotFoundException;

	Player addGameId(Integer playerId, Integer gameId) throws PlayerNotFoundException;

	Player createNewPlayer(Player player);

	boolean deletePlayer(Integer id);
}
