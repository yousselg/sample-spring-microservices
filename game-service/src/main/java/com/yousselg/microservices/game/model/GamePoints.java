package com.yousselg.microservices.game.model;

public enum GamePoints {
    _LOVE, _15, _30, _40, ADVANTAGE, WIN;

    public GamePoints changePoint() {
        if (this == WIN) {
            return WIN;
        }
        return values()[this.ordinal() + 1];

    }
}
