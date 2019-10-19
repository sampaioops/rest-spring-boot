package com.sampaio.cursoestudo.servico;

import com.sampaio.cursoestudo.modelo.Pedido;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public interface EmailServico {

    void envioaOrdemConfirmacaoEmail(Pedido pedido);

    void sendEmail(SimpleMailMessage simpleMailMessage);
}
