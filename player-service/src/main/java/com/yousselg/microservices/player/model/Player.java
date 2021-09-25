package com.yousselg.microservices.player.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    private Integer id;
    private String name;
    private List<Integer> gameIds;

}
