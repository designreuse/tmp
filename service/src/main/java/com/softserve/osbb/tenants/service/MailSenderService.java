package com.softserve.osbb.tenants.service;

import com.softserve.osbb.tenants.model.Mail;

import javax.mail.MessagingException;

/**
 * Created by Anastasiia Fedorak on 8/13/16.
 */
public interface MailSenderService {
    void send(String to, String subject, String text) throws MessagingException;
    void send(Mail mail) throws MessagingException;

}
