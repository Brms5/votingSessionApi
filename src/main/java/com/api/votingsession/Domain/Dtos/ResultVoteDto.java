package com.api.votingsession.Domain.Dtos;

public class ResultVoteDto {
    private String title;
    private Integer voteYes = 0;
    private Integer voteNo = 0;

    public Integer getVoteYes() {
        return voteYes;
    }

    public void setVoteYes(Integer voteYes) {
        this.voteYes = voteYes;
    }

    public Integer getVoteNo() {
        return voteNo;
    }

    public void setVoteNo(Integer voteNo) {
        this.voteNo = voteNo;
    }
}
