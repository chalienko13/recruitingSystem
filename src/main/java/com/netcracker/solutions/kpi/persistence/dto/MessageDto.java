package com.netcracker.solutions.kpi.persistence.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDto {

    private String message;

    private MessageDtoType type;

    public MessageDto() {
    }

    public MessageDto(String message) {
        this.message = message;
    }

    public MessageDto(String message, MessageDtoType type) {
        super();
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MessageDtoType getType() {
        return type;
    }

    public void setType(MessageDtoType type) {
        this.type = type;
    }

}
