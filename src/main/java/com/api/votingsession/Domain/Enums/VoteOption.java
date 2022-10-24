package com.api.votingsession.Domain.Enums;

public enum VoteOption {

    SIM(1),
    NAO(2);

    private int code;

    private VoteOption(int code) { this.code = code; }

    public int getCode() { return code; }

    public static VoteOption valueOf(int code) {
        for(VoteOption value : VoteOption.values()) {
            if(value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid VoteOption code!");
    }

}
