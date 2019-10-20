package com.sampaio.cursoestudo.servico.email;

import com.sampaio.cursoestudo.modelo.Pedido;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public interface EmailServico {

    void envioaOrdemConfirmacaoEmail(Pedido pedido);

    void sendEmail(SimpleMailMessage simpleMailMessage);

    void envioaOrdemConfirmacaoEmailHtml(Pedido pedido);

    void sendEmailHtml(MimeMessage mimeMessage);
}
