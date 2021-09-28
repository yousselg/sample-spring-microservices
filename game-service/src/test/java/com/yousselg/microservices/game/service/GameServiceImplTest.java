package com.yousselg.microservices.game.service;

import java.util.ArrayList;

import com.yousselg.microservices.game.exceptions.GameNotFoundException;
import com.yousselg.microservices.game.intercomm.PlayerClient;
import com.yousselg.microservices.game.model.Game;
import com.yousselg.microservices.game.model.GamePoints;
import com.yousselg.microservices.game.model.GameStatus;
import com.yousselg.microservices.game.model.Player;
import com.yousselg.microservices.game.model.ScorePlayer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GameServiceImplTest {

	@InjectMocks
	private GameServiceImpl service;

	@Mock
	private PlayerClient playerClient;

	private static final Integer PLAYER_1_ID = 1;
	private static final Integer PLAYER_2_ID = 2;
	private static final Player player1 = new Player(PLAYER_1_ID, "Youssef", new ArrayList<>());
	private static final Player player2 = new Player(PLAYER_2_ID, "Abdelmoula", new ArrayList<>());

	private static final Game game = new Game();

	static {
		game.setId(0);
		game.setStatus(GameStatus.ONGOING);
		game.setFirstPlayer(new ScorePlayer());
		game.setSecondPlayer(new ScorePlayer());
		game.getFirstPlayer().setPlayer(player1);
		game.getSecondPlayer().setPlayer(player2);
	}

	@Test
	public void createNewGame() {

		when(this.playerClient.findById(PLAYER_1_ID)).thenReturn(player1);
		when(this.playerClient.findById(PLAYER_2_ID)).thenReturn(player2);
		when(this.playerClient.addGameId(anyInt(), anyInt())).thenReturn(mock(Player.class));
		final Game actual = this.service.createNewGame("game0", 1, 2);
		assertNotNull(actual);
		assertEquals("game0", actual.getName());
		assertSame(player1, actual.getFirstPlayer().getPlayer());
		assertSame(player2, actual.getSecondPlayer().getPlayer());
	}

	@Test(expected = GameNotFoundException.class)
	public void playerScores() throws GameNotFoundException {
		//when(this.service.getGames()).thenReturn(Arrays.asList());
		this.service.playerScores(0, 1);

	}

	@Test
	public void playerChangesGameStateIsOngoing() {
		final Game actual = this.service.playerChangesGameState(1, game);
		assertEquals(GameStatus.ONGOING, actual.getStatus());
		assertSame(game, actual);
	}

	@Test
	public void manageScore() {
		final ScorePlayer scorePlayer = new ScorePlayer();
		final ScorePlayer otherPlayer = new ScorePlayer();
		scorePlayer.setPlayer(player1);
		scorePlayer.setPoints(GamePoints.ADVANTAGE);
		otherPlayer.setPlayer(player2);
		otherPlayer.setPoints(GamePoints.ADVANTAGE);
		this.service.manageScore(scorePlayer, otherPlayer);
		assertEquals(GamePoints._40, scorePlayer.getPoints());
		assertEquals(GamePoints._40, otherPlayer.getPoints());
	}

	@Test
	public void isDeuce() {
		final ScorePlayer scorePlayer = new ScorePlayer();
		final ScorePlayer otherPlayer = new ScorePlayer();
		scorePlayer.setPlayer(player1);
		scorePlayer.setPoints(GamePoints._40);
		otherPlayer.setPlayer(player2);
		otherPlayer.setPoints(GamePoints._40);
		final Game game = new Game();
		game.setFirstPlayer(scorePlayer);
		game.setSecondPlayer(otherPlayer);
		assertTrue(this.service.isDeuce(game));
	}

	@Test
	public void findAll() {
		assertEquals(0, this.service.findAll().size());
	}

	@Test(expected = GameNotFoundException.class)
	public void findById() throws GameNotFoundException {
		this.service.findById(-1);
	}

	@Test
	public void deleteGame() {
		assertFalse(this.service.deleteGame(-1));
	}
}
