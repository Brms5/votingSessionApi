package com.api.votingsession.Utility.CustomException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
public class BusinessException extends RuntimeException {

    @JsonIgnore
    private final HttpStatus httpStatusCode;

    private final String message;

    private final String description;

    public BusinessExceptionBody getOnlyBody(){
        BusinessExceptionBody businessException =  new BusinessExceptionBody();
        businessException.setCode(httpStatusCode.toString());
        businessException.setMessage(this.message);
        businessException.setDescription(this.description);
        return businessException;
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this.getOnlyBody());
        } catch (IOException e) {
            log.error("Fail to convert ResultAcessToken to json");
        }
        return "";
    }
}