package com.api.votingsession.Domain.Dto;

public class VoteDto {

    private int voteYes = 0;
    private int voteNo = 0;

    public int getVoteYes() {
        return voteYes;
    }

    public void setVoteYes(int voteYes) {
        this.voteYes = voteYes;
    }

    public int getVoteNo() {
        return voteNo;
    }

    public void setVoteNo(int voteNo) {
        this.voteNo = voteNo;
    }

}
