package com.sampaio.cursoestudo.servico.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

public class SmtpEmailServico extends AbstratoEmailServico {

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailServico.class);

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        LOG.info("ENVIANDO...");
        mailSender.send(simpleMailMessage);
        LOG.info("E-MAIL ENVIADO!");
    }

    @Override
    public void sendEmailHtml(MimeMessage mimeMessage) {
        LOG.info("ENVIANDO...");
        javaMailSender.send(mimeMessage);
        LOG.info("E-MAIL ENVIADO!");
    }
}
