package com.yousselg.microservices.game.intercomm;

import com.yousselg.microservices.game.model.Player;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("player-service")
public interface PlayerClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Player findById(@PathVariable("id") Integer id);

    @RequestMapping(method = RequestMethod.GET, value = "/{playerId}/games/{gameId}")
    Player addGameId(@PathVariable("playerId") Integer playerId, @PathVariable("gameId") Integer gameId);


}
