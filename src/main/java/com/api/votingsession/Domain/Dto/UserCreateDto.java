package com.api.votingsession.Domain.Dto;

import javax.validation.constraints.NotBlank;

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
