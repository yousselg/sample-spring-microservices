package com.yousselg.microservices.player.api;

import com.yousselg.microservices.player.exceptions.PlayerNotFoundException;
import com.yousselg.microservices.player.model.Player;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController("")
@Slf4j
public class Api {

    private final List<Player> players;

    public Api() {
        this.players = new ArrayList<>();
        this.players.add(new Player(1, "Youssef", new ArrayList<>()));
        this.players.add(new Player(2, "Abdelmoula", new ArrayList<>()));
    }

    @RequestMapping(method = RequestMethod.GET, value = "")
    public List<Player> findAll() {
        log.info("Player.findAll()");
        return this.players;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Player findById(@PathVariable final Integer id) throws PlayerNotFoundException {
        log.info("Player.findById()");
        return this.players.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new PlayerNotFoundException("id : " + id));
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
        log.info("Player.addGameId()");
        final Player player = this.players.stream()
                .filter(it -> it.getId().equals(playerId))
                .findFirst()
                .orElseThrow(() -> new PlayerNotFoundException("id : " + playerId));
        player.getGameIds().add(gameId);
        return player;
    }

    @RequestMapping(method = RequestMethod.POST, value = "")
    public Player createNewPlayer(@RequestBody final Player player) {
        log.info("Player.createNewPlayer()");
        if (player.getId() != null) {
            return null;
        }
        final int size = this.players.size();
        player.setId(size + 1);
        this.players.add(player);
        return player;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public boolean deletePlayer(@PathVariable final Integer id) {
        log.info("Player.deletePlayer()");
        try {
            final Player byId = this.findById(id);
            this.players.remove(byId);
        } catch (final PlayerNotFoundException e) {
            return false;
        }
        return true;
    }

}
