package com.api.votingsession.Domain.Dto;

public class ResultVoteDto {
    private String title;
    private Integer voteYes = 0;
    private Integer voteNo = 0;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
