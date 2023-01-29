package com.api.votingsession.Utility.CustomException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Slf4j
@AllArgsConstructor
public enum MessageBusiness {

    AGENDA_NOT_FOUND(HttpStatus.NOT_FOUND, "Agenda not found!", "The entered agenda was not found."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found!", "The entered user was not found."),
    VOTING_SESSION_CLOSED(HttpStatus.BAD_REQUEST, "Voting Session is closed!", "The selected Agenda: %s has already been voted."),
    TOPIC_INVALID(HttpStatus.BAD_REQUEST, "The entered topic is not valid.", "Topic is not accepted by the system."),
    ALREADY_VOTED(HttpStatus.BAD_REQUEST, "User already voted!", "%s"),
    USER_CANNOT_VOTE(HttpStatus.BAD_REQUEST, "The user cannot vote on the agenda he created.", "The user can vote only in agendas that other users created.");

    private final HttpStatus status;
    private final String message;
    private final String description;

    public String formatDescription(String... strings) {
        return String.format(this.description, strings);
    }

    public BusinessException createException(String... strings) {
        log.info("Class {},  message={}", BusinessException.class.getName(), this.message);
        return BusinessException.builder().httpStatusCode(this.getStatus()).message(this.getMessage())
                .description(this.formatDescription(strings)).build();
    }

    public BusinessException createException(HttpStatus status, Object... args) {
        log.info("Class {}. Message={}", BusinessException.class.getName(), this.message);
        return BusinessException.builder().httpStatusCode(status).message(this.getMessage())
                .description(this.formatDescription(args)).build();
    }

    public String formatDescription(Object... params) {
        return String.format(this.description, params);
    }

}
