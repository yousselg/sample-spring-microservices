package com.yousselg.microservices.game.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.yousselg.microservices.game.exceptions.GameNotFoundException;
import com.yousselg.microservices.game.intercomm.PlayerClient;
import com.yousselg.microservices.game.model.Game;
import com.yousselg.microservices.game.model.GamePoints;
import com.yousselg.microservices.game.model.GameStatus;
import com.yousselg.microservices.game.model.Player;
import com.yousselg.microservices.game.model.ScorePlayer;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GameServiceImpl implements IGameService {

	@Autowired
	private PlayerClient playerClient;

	private final List<Game> games;

	public GameServiceImpl() {
		this.games = new ArrayList<>();
	}

	public List<Game> getGames() {
		return this.games;
	}

	@Override
	public Game createNewGame(final String gameName,
			final Integer firstPlayerId,
			final Integer secondPlayerId) {
		log.info("Game.createNewGame()");
		final Game game = new Game();
		game.setId(this.getGames().size());
		game.setName(gameName);
		this.playerClient.addGameId(firstPlayerId, game.getId());
		this.playerClient.addGameId(secondPlayerId, game.getId());
		final Player firstPlayer = this.playerClient.findById(firstPlayerId);
		final Player secondPlayer = this.playerClient.findById(secondPlayerId);
		game.getFirstPlayer().setPlayer(firstPlayer);
		game.getSecondPlayer().setPlayer(secondPlayer);
		this.getGames().add(game);
		return game;
	}

	@Override
	public Game playerScores(final Integer gameId, final Integer playerId) throws GameNotFoundException {
		log.info("Game.findAll()");
		final Game game = this.getGames().stream().filter(g -> g.getId().equals(gameId))
				.findFirst().orElseThrow(() -> new GameNotFoundException("id : " + gameId));
		return this.playerChangesGameState(playerId, game);

	}

	Game playerChangesGameState(final Integer playerId, final Game game) {

		if (!game.getStatus().equals(GameStatus.FINISHED)) {
			final ScorePlayer scorePlayer = Stream.of(game.getFirstPlayer(), game.getSecondPlayer())
					.filter(sp -> sp.getPlayer().getId() == playerId).findFirst()
					.orElseThrow(() -> new RuntimeException("Player not found with id : " + playerId));
			final ScorePlayer otherPlayer = Stream.of(game.getFirstPlayer(), game.getSecondPlayer())
					.filter(sp -> sp.getPlayer().getId() != playerId).findFirst()
					.orElseThrow(() -> new RuntimeException("Player not found with id : " + playerId));

			final GamePoints newPoints = scorePlayer.getPoints().changePoint();
			scorePlayer.setPoints(newPoints);
			this.manageScore(scorePlayer, otherPlayer);
			if (this.isDeuce(game)) {
				game.setStatus(GameStatus.DEUCE);
			}
			else {
				if (scorePlayer.getPoints() == GamePoints.WIN) {
					game.setStatus(GameStatus.FINISHED);
				}
				else {
					game.setStatus(GameStatus.ONGOING);
				}
			}
		}
		return game;
	}

	void manageScore(final ScorePlayer scorePlayer, final ScorePlayer otherPlayer) {
		if (scorePlayer.getPoints() == GamePoints.ADVANTAGE
				&& otherPlayer.getPoints() == GamePoints.ADVANTAGE) {
			scorePlayer.setPoints(GamePoints._40);
			otherPlayer.setPoints(GamePoints._40);
		}
		else if (scorePlayer.getPoints() == GamePoints.ADVANTAGE && otherPlayer.getPoints().ordinal() < GamePoints._40
				.ordinal()) {
			scorePlayer.setPoints(GamePoints.WIN);
		}
	}

	boolean isDeuce(final Game game) {
		return game.getFirstPlayer().getPoints() == GamePoints._40
				&& game.getSecondPlayer().getPoints() == GamePoints._40;
	}

	@Override
	public List<Game> findAll() {
		log.info("Game.findAll()");
		return this.getGames();
	}

	@Override
	public Game findById(final Integer id) throws GameNotFoundException {
		log.info(String.format("Game.findById(%s)", id));
		final Game game = this.getGames().stream()
				.filter(it -> it.getId().equals(id))
				.findFirst().orElseThrow(() -> new GameNotFoundException("id : " + id));
		return game;
	}

	@Override
	public boolean deleteGame(final Integer id) {
		log.info("Game.deleteGame()");
		try {
			final Game byId = this.findById(id);
			this.getGames().remove(byId);
		}
		catch (final GameNotFoundException e) {
			return false;
		}
		return true;
	}
}
