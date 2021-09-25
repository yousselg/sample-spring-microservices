package com.yousselg.microservices.game.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    private Integer id;
    private String name;
    private List<Integer> gameIds;

}
