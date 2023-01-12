package com.api.votingsession.domain.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Builder
public class UserCreateDto {
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
