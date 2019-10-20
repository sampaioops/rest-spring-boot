package com.sampaio.cursoestudo.servico.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

import javax.mail.internet.MimeMessage;


public class MockEmailServico extends AbstratoEmailServico {

    private static final Logger LOG = LoggerFactory.getLogger(MockEmailServico.class);

    @Override
    public void sendEmail(SimpleMailMessage simpleMailMessage) {
        LOG.info("SIMULANDO ENVIO DE EMAIL");
        LOG.info(simpleMailMessage.toString());
        LOG.info("E-MAIL ENVIADO!");
    }

    @Override
    public void sendEmailHtml(MimeMessage mimeMessage) {
        LOG.info("SIMULANDO ENVIO DE EMAIL");
        LOG.info(mimeMessage.toString());
        LOG.info("E-MAIL ENVIADO!");
    }
}
