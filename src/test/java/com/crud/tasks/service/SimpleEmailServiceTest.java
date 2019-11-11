package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MailCreatorService mailCreatorService;

    @Test
    public void shouldSendEmail() {
        //Given
        Mail mail = new Mail("test@test.com", "Test", "Test message");
        Mail mail2 = new Mail("test@test.com", "Test", "Test message");

        /*SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        Optional.ofNullable(mail.getToCc()).ifPresent(cc -> mailMessage.setCc(cc));
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        SimpleMailMessage mailMessage2 = new SimpleMailMessage();
        mailMessage2.setTo(mail2.getMailTo());
        Optional.ofNullable(mail2.getToCc()).ifPresent(cc -> mailMessage2.setCc(cc));
        mailMessage2.setSubject(mail2.getSubject());
        mailMessage2.setText(mail2.getMessage());*/

        MimeMessagePreparator mimeMessage1 = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
        };

        MimeMessagePreparator mimeMessage2 = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail2.getMailTo());
            messageHelper.setSubject(mail2.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail2.getMessage()), true);
        };
        //When
        simpleEmailService.send(mail);
        simpleEmailService.send(mail2);

        //Then
        verify(javaMailSender, times(1)).send(mimeMessage1);
        verify(javaMailSender, times(1)).send(mimeMessage2);
    }
}