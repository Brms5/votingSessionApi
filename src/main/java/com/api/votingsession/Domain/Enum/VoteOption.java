package com.api.votingsession.Domain.Enum;

import java.util.Random;

public enum VoteOption {
    SIM,
    NAO;

    public static VoteOption generateRandomVoteOption() {
        VoteOption[] values = VoteOption.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }
}
