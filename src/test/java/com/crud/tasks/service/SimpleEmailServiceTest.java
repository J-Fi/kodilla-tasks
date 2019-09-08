package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmail() {
        //Given
        Mail mail = new Mail("test@test.com", "test2@test2.com", "Test", "Test message");
        Mail mail2 = new Mail("test@test.com", null, "Test", "Test message");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        Optional.ofNullable(mail.getToCc()).ifPresent(cc -> mailMessage.setCc(cc));
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        SimpleMailMessage mailMessage2 = new SimpleMailMessage();
        mailMessage2.setTo(mail2.getMailTo());
        Optional.ofNullable(mail2.getToCc()).ifPresent(cc -> mailMessage2.setCc(cc));
        mailMessage2.setSubject(mail2.getSubject());
        mailMessage2.setText(mail2.getMessage());

        //When
        simpleEmailService.send(mail);
        simpleEmailService.send(mail2);

        //Then
        verify(javaMailSender, times(1)).send(mailMessage);
        verify(javaMailSender, times(1)).send(mailMessage2);
    }
}