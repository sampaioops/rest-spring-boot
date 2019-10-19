package com.sampaio.cursoestudo.servico;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailServico extends AbstratoEmailServico {

    private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailServico.class);

    @Autowired
    private MailSender mailSender;

    @Override
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        LOG.info("ENVIANDO...");
        mailSender.send(simpleMailMessage);
        LOG.info("E-MAIL ENVIADO!");
    }
}
