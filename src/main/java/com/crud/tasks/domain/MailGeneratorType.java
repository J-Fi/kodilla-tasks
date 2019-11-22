package com.crud.tasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MailGeneratorType {
    EMAIL_FROM_EMAIL_SCHEDULER("/mail/number-of-cards-mail"),
    EMAIL_FROM_TRELLO_CARD_CREATE("/mail/created-trello-card-mail");

    private String mailTemplateFileName;
}
