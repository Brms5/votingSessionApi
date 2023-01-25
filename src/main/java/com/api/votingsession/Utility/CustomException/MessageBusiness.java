package com.api.votingsession.Utility.CustomException;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Getter
@Slf4j
@AllArgsConstructor
public enum MessageBusiness {

    USER_NOT_ALL_AVAILABLE(HttpStatus.BAD_REQUEST, "User Search couldn't be performed", "%s"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "MISSING_VALUE", "%s"),
    AGENDA_NOT_FOUND(HttpStatus.NOT_FOUND, "Agenda not found", "%s"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User {} not found", "%s"),
    USER_NOT_NEW(HttpStatus.BAD_REQUEST, "USER_NOT_NEW", "User %s has already logged in"),
    FIELD_REQUIRED(HttpStatus.BAD_REQUEST, "Request field missing", "%s"),
    OPERATION_FORBIDEN(HttpStatus.FORBIDDEN, "Operation not allowed", "Forbidden Access"),
    USERS_NOT_FOUND(HttpStatus.NOT_FOUND, "No users were found for the given request", "%s"),
    TIMEZONE_NOT_FOUND(HttpStatus.NOT_FOUND, "Timezone not found", "%s"),
    LANGUAGE_NOT_FOUND(HttpStatus.NOT_FOUND, "Language not found", "%s"),
    GROUPS_NULL_OR_EMPTY(HttpStatus.BAD_REQUEST, "Wrong payload", "The groups list cannot be empty or null."),
    GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "Group not found", "Could not found any groups for customer: %s."),
    GROUP_INVALID(HttpStatus.NOT_FOUND, "Group Invalid", "One or more of the following groups: %s, are not associated with customer: %s"),
    FAIL_TO_REMOVE_USER_GROUPS(HttpStatus.INTERNAL_SERVER_ERROR, "Error removing groups", "Error while trying to remove groups from user: %s."),
    FAIL_TO_SAVE_USER_GROUPS(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving group", "Error while trying to relate group: {identifierGroup: %s, identifierParent: %s} with user: %s."),
    PASS_THROUGH_ERROR(null, "Unexpected response has been captured from Sasweb Authentication API.", "%s"),
    VOTING_SESSION_CLOSED(HttpStatus.BAD_REQUEST, "Voting Session is closed!", "The selected Agenda: %s has already been voted."),
    TOPIC_INVALID(HttpStatus.BAD_REQUEST, "The entered topic is not valid.", "%s"),
    ALREADY_VOTED(HttpStatus.BAD_REQUEST, "User already voted!", "%s"),
    USER_CANNOT_VOTE(HttpStatus.BAD_REQUEST, "The user cannot vote on the agenda he created.", "%s");

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
