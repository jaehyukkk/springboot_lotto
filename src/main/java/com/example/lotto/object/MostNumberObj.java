package com.example.lotto.object;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MostNumberObj {

    private int number;
    private int count;
    private int rank;

    public MostNumberObj(int number, int count, int rank) {
        this.number = number;
        this.count = count;
        this.rank = rank;
    }
}
