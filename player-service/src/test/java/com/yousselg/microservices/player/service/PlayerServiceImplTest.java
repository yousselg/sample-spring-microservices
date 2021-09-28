package com.yousselg.microservices.player.service;

import java.util.Arrays;

import com.yousselg.microservices.player.exceptions.PlayerNotFoundException;
import com.yousselg.microservices.player.model.Player;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class PlayerServiceImplTest {

	private final PlayerServiceImpl service = new PlayerServiceImpl();

	@Test
	public void findAll() {
		assertEquals(2, this.service.findAll().size());
	}

	@Test
	public void findById() throws PlayerNotFoundException {
		final Player player = this.service.findById(1);
		assertNotNull(player);
		assertEquals("Youssef", player.getName());
	}

	@Test
	public void addGameIdWithCorrectPlayerId() throws PlayerNotFoundException {
		final Player player = this.service.addGameId(1, 0);
		assertEquals(Arrays.asList(0), player.getGameIds());
	}

	@Test(expected = PlayerNotFoundException.class)
	public void addGameIdWithIncorrectPlayerId() throws PlayerNotFoundException {
		final Player player = this.service.addGameId(-1, 0);
	}

	@Test
	public void createNewPlayer() {
		final Player player = new Player();
		assertSame(player, this.service.createNewPlayer(player));
	}

	@Test
	public void deletePlayer() {
		assertTrue(this.service.deletePlayer(1));
	}

}
