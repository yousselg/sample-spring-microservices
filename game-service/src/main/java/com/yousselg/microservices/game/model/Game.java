package com.yousselg.microservices.game.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    private Integer id;
    private String name;
    private GameStatus status = GameStatus.ONGOING;
    private ScorePlayer firstPlayer = new ScorePlayer();
    private ScorePlayer secondPlayer = new ScorePlayer();
    ;


}
