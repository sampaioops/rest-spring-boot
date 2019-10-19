package com.sampaio.cursoestudo.servico;

import com.sampaio.cursoestudo.modelo.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstratoEmailServico implements EmailServico {

    @Value("${default.sender}")
    private String emailPadrao;

    @Override
    public void envioaOrdemConfirmacaoEmail(Pedido pedido) {
        SimpleMailMessage msg = preparaSimplesMailMessage(pedido);
        sendEmail(msg);
    }

    protected SimpleMailMessage preparaSimplesMailMessage(Pedido pedido) {
        SimpleMailMessage msg = new SimpleMailMessage();

        //Destinatario
        msg.setTo(pedido.getCliente().getEmail());
        //Remetente
        msg.setFrom(emailPadrao);
        msg.setSubject("Pedido confirmado! Código: " + pedido.getId());
        msg.setSentDate(new Date(System.currentTimeMillis()));
        msg.setText(pedido.toString());

        return msg;
    }
}
