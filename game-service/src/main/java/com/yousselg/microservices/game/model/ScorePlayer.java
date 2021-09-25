package com.yousselg.microservices.game.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScorePlayer {
    private Player player;
    private GamePoints points = GamePoints._LOVE;
}
