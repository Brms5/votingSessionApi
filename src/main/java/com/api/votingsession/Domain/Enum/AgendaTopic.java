package com.api.votingsession.Domain.Enum;

import java.util.Random;

public enum AgendaTopic {
    ECONOMIA,
    CORRUPCAO,
    SEGURANCA,
    EDUCACAO,
    SAUDE;

    public static AgendaTopic generateRandomTopic() {
        AgendaTopic[] values = AgendaTopic.values();
        int length = values.length;
        int randIndex = new Random().nextInt(length);
        return values[randIndex];
    }
}
