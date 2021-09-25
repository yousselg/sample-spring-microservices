package com.yousselg.microservices.game.api;

import com.yousselg.microservices.game.exceptions.GameNotFoundException;
import com.yousselg.microservices.game.intercomm.PlayerClient;
import com.yousselg.microservices.game.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController()
@Slf4j
public class Api {

    @Autowired
    private PlayerClient playerClient;

    private final List<Game> games;

    public Api() {
        this.games = new ArrayList<>();
    }

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
        log.info("Game.createNewGame()");
        final Game game = new Game();
        game.setId(this.games.size());
        game.setName(gameName);
        this.playerClient.addGameId(firstPlayerId, game.getId());
        this.playerClient.addGameId(secondPlayerId, game.getId());
        final Player firstPlayer = this.playerClient.findById(firstPlayerId);
        final Player secondPlayer = this.playerClient.findById(secondPlayerId);
        game.getFirstPlayer().setPlayer(firstPlayer);
        game.getSecondPlayer().setPlayer(secondPlayer);
        this.games.add(game);
        return game;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{gameId}/{playerId}")
    public Game playerScores(@PathVariable("gameId") final Integer gameId, @PathVariable("playerId") final Integer playerId) throws GameNotFoundException {
        log.info("Game.findAll()");
        final Game game = this.games.stream().filter(g -> g.getId().equals(gameId))
                .findFirst().orElseThrow(() -> new GameNotFoundException("id : " + gameId));
        return this.playerChangesGameState(playerId, game);

    }

    private Game playerChangesGameState(final Integer playerId, final Game game) {

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
            } else {
                if (scorePlayer.getPoints() == GamePoints.WIN) {
                    game.setStatus(GameStatus.FINISHED);
                } else {
                    game.setStatus(GameStatus.ONGOING);
                }
            }
        }
        return game;
    }

    private void manageScore(final ScorePlayer scorePlayer, final ScorePlayer otherPlayer) {
        if (scorePlayer.getPoints() == GamePoints.ADVANTAGE
                && otherPlayer.getPoints() == GamePoints.ADVANTAGE) {
            scorePlayer.setPoints(GamePoints._40);
            otherPlayer.setPoints(GamePoints._40);
        } else if (scorePlayer.getPoints() == GamePoints.ADVANTAGE && otherPlayer.getPoints().ordinal() < GamePoints._40.ordinal()) {
            scorePlayer.setPoints(GamePoints.WIN);
        }
    }

    private boolean isDeuce(final Game game) {
        return game.getFirstPlayer().getPoints() == GamePoints._40
                && game.getSecondPlayer().getPoints() == GamePoints._40;
    }


    @RequestMapping(method = RequestMethod.GET, value = "")
    public List<Game> findAll() {
        log.info("Game.findAll()");
        return this.games;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Game findById(@PathVariable("id") final Integer id) throws GameNotFoundException {
        log.info(String.format("Game.findById(%s)", id));
        final Game game = this.games.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst().orElseThrow(() -> new GameNotFoundException("id : " + id));
        return game;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public boolean deleteGame(@PathVariable final Integer id) {
        log.info("Game.deleteGame()");
        try {
            final Game byId = this.findById(id);
            this.games.remove(byId);
        } catch (final GameNotFoundException e) {
            return false;
        }
        return true;
    }

}
