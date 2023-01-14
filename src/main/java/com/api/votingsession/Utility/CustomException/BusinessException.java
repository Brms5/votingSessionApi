package com.api.votingsession.Utility.CustomException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

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
}