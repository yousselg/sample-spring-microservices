package com.yousselg.microservices.player.service;

import java.util.ArrayList;
import java.util.List;

import com.yousselg.microservices.player.exceptions.PlayerNotFoundException;
import com.yousselg.microservices.player.model.Player;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PlayerServiceImpl implements IPlayerService {

	private final List<Player> players;

	public PlayerServiceImpl() {
		this.players = new ArrayList<>();
		this.players.add(new Player(1, "Youssef", new ArrayList<>()));
		this.players.add(new Player(2, "Abdelmoula", new ArrayList<>()));
	}

	@Override
	public List<Player> findAll() {
		log.info("Player.findAll()");
		return this.players;
	}

	@Override
	public Player findById(final Integer id) throws PlayerNotFoundException {
		log.info("Player.findById()");
		return this.players.stream()
				.filter(it -> it.getId().equals(id))
				.findFirst()
				.orElseThrow(() -> new PlayerNotFoundException("id : " + id));
	}

	@Override
	public Player addGameId(final Integer playerId, final Integer gameId) throws PlayerNotFoundException {
		log.info("Player.addGameId()");
		final Player player = this.players.stream()
				.filter(it -> it.getId().equals(playerId))
				.findFirst()
				.orElseThrow(() -> new PlayerNotFoundException("id : " + playerId));
		player.getGameIds().add(gameId);
		return player;
	}

	@Override
	public Player createNewPlayer(final Player player) {
		log.info("Player.createNewPlayer()");
		if (player.getId() != null) {
			return null;
		}
		final int size = this.players.size();
		player.setId(size + 1);
		this.players.add(player);
		return player;
	}

	@Override
	public boolean deletePlayer(final Integer id) {
		log.info("Player.deletePlayer()");
		try {
			final Player byId = this.findById(id);
			this.players.remove(byId);
		}
		catch (final PlayerNotFoundException e) {
			return false;
		}
		return true;
	}

}
