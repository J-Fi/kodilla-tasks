package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mail {
    public enum MailGeneratorType {
        EMAIL_FROM_EMAIL_SCHEDULER,
        EMAIL_FROM_TRELLO_CARD_CREATE
    }
    private MailGeneratorType mailGeneratorType;
    private String mailTo;
    private String subject;
    private String message;
}
